package controller.implementations;

import apirest.ApiClient;
import apirest.DirectoryApi;
import apirest.FileApi;
import apirest.NodeApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.FileDepotService;
import dto.SoapDownloadResponse;
import dto.SoapResponse;
import dto.files.*;
import dto.files.ListAllFiles;
import dto.node.NodeInfo;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "controller.FileDepotService")
public class FileImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processFileRequest(String action, String data) {
        try {
            switch (action) {
                case "upload": {
                    UploadFileBatch batch = gson.fromJson(data, UploadFileBatch.class);
                    List<UploadFile> files = batch.files;
                    List<String> resultados = new ArrayList<>();

                    for (UploadFile upload : files) {

                        String directory = DirectoryApi.getDirectoryPathById(upload.directoryId);
                        if (upload.directoryId == 0) {
                            int rootDirectory = DirectoryApi.getRootDirectoryId(upload.owner);
                            directory = DirectoryApi.getDirectoryPathById(rootDirectory);
                        }

                        List<NodeInfo> nodos = NodeApi.listAllNodes(); // Actualizado en cada iteración

                        List<NodeInfo> nodosDisponibles = new ArrayList<>();
                        for (NodeInfo nodo : nodos) {
                            try {
                                FileSystemClient client = GrpcNodeManager.getClientByHost(nodo.ipv4_address);
                                System.out.println("Probing " + nodo.ipv4_address);

                                if (client.nodeIsAlive()) {
                                    System.out.println("EL NODO " + client.getHost() + ":" + client.getPort() + " ESTA VIVO");
                                    nodosDisponibles.add(nodo);
                                } else {
                                    System.out.println("Nodo no responde: " + nodo.ipv4_address);
                                }

                            } catch (Exception e) {
                                System.out.println("Nodo inactivo o error al conectarse: " + nodo.ipv4_address);
                            }

                        }

                        if (nodosDisponibles.size() < 2) {
                            resultados.add(upload.name + ": No hay suficientes nodos disponibles para replicación");
                            continue;
                        }

                        nodosDisponibles.sort((a, b) -> Long.compare(b.available_size_bytes, a.available_size_bytes));

                        NodeInfo original = nodosDisponibles.get(0);
                        NodeInfo copia = nodosDisponibles.get(1);

                        UploadResult resultadoOriginal = null;
                        UploadResult resultadoCopia = null;

                        try {
                            FileSystemClient clientOriginal = GrpcNodeManager.getClientByHost(original.ipv4_address);
                            FileSystemClient clientCopia = GrpcNodeManager.getClientByHost(copia.ipv4_address);

                            resultadoOriginal = clientOriginal.uploadBase64File(upload.name, upload.base64, directory);
                            resultadoCopia = clientCopia.uploadBase64File(upload.name, upload.base64, directory);
                        } catch (Exception e) {
                            resultados.add(upload.name + ": Falló la subida a uno de los nodos: " + e.getMessage());
                            continue;
                        }

                        if (!resultadoOriginal.success || !resultadoCopia.success) {
                            resultados.add(upload.name + ": Subida fallida en uno o ambos nodos");
                            continue;
                        }

                        // Registrar en la base de datos
                        upload.name = resultadoOriginal.name;
                        String fileType = resultadoOriginal.type;

                        boolean registered = FileApi.registerFile(upload,
                                String.valueOf(original.idNODE),
                                String.valueOf(copia.idNODE),
                                fileType);

                        if (!registered) {
                            resultados.add(upload.name + ": Subido pero no registrado en DB");
                        } else {
                            resultados.add(upload.name + ": Subido y registrado correctamente");
                        }
                    }

                    boolean todosBien = resultados.stream().allMatch(s -> s.contains("registrado correctamente"));
                    return gson.toJson(new SoapResponse(todosBien, String.join("\n", resultados)));
                }



                case "delete": {
                    DeleteFile delete = gson.fromJson(data, DeleteFile.class);
                    String fileId = delete.fileID;

                    try {
                        String fileInfoJson = ApiClient.get("/file/byId/" + fileId);
                        JsonObject fileInfo = JsonParser.parseString(fileInfoJson).getAsJsonObject();
                        if (!fileInfo.has("name")) {
                            return gson.toJson(new SoapResponse(false, "Archivo no encontrado en DB"));
                        }

                        String fileName = fileInfo.get("name").getAsString();
                        String filePath = fileInfo.get("directory_path").getAsString() + "/" + fileName;

                        boolean eliminado = false;
                        for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                            try {
                                String result = client.deleteFile(filePath);
                                if (result.toLowerCase().contains("correctamente") || result.toLowerCase().contains("éxito")) {
                                    eliminado = true;
                                }
                            } catch (Exception e) {
                                System.err.println("Error al eliminar en nodo: " + e.getMessage());
                            }
                        }

                        boolean successDb = eliminado && FileApi.deleteFile(fileId);
                        return gson.toJson(new SoapResponse(successDb, successDb ? "Archivo eliminado correctamente" : "Falló la eliminación"));
                    } catch (Exception e) {
                        return gson.toJson(new SoapResponse(false, "Error interno: " + e.getMessage()));
                    }
                }


                case "rename": {
                    RenameFile rename = gson.fromJson(data, RenameFile.class);
                    String oldPath = rename.userId + "/" + rename.oldFileName;
                    String newPath = rename.userId + "/" + rename.newFileName;

                    boolean renombrado = false;
                    for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                        try {
                            String result = client.renameFile(oldPath, newPath);
                            if (result.toLowerCase().contains("renombrado")) {
                                renombrado = true;
                            }
                        } catch (Exception e) {
                            System.err.println("Error al renombrar en nodo: " + e.getMessage());
                        }
                    }

                    String oldName = rename.oldFileName.substring(rename.oldFileName.lastIndexOf("/") + 1);
                    String newName = rename.newFileName.substring(rename.newFileName.lastIndexOf("/") + 1);
                    boolean successDb = renombrado && FileApi.renameFile(oldName, newName);

                    return gson.toJson(new SoapResponse(successDb, successDb ? "Archivo renombrado correctamente" : "Fallo al renombrar archivo"));
                }



                case "move": {
                    MoveFile move = gson.fromJson(data, MoveFile.class);
                    boolean success = false;
                    String result = "";

                    String filePathJson = FileApi.getFilePathById(move.fileID);
                    String newFilePath = DirectoryApi.getDirectoryPathById(move.newDirectoryID);

                    System.out.println("File path JSON: " + filePathJson);
                    System.out.println("New file path: " + newFilePath);

                    for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                        try {
                            String[] parts = filePathJson.split("/");
                            String fileName = parts[parts.length - 1];
                            String newFullPath = newFilePath.endsWith("/") ? newFilePath + fileName : newFilePath + "/" + fileName;

                            result = client.moveFile(filePathJson, newFullPath);

                            if (result.toLowerCase().contains("con éxito")) {
                                success = true;
                            }
                        } catch (Exception e) {
                            System.err.println("Error al mover archivo en nodo: " + e.getMessage());
                        }
                    }
                    if (success) {
                        boolean successDb = FileApi.moveFile(move.fileID, move.newDirectoryID);
                        if (!successDb) {
                            return gson.toJson(new SoapResponse(false, "Archivo movido pero no registrado en DB"));
                        }
                    } else {
                        return gson.toJson(new SoapResponse(false, "Error al mover el archivo"));
                    }
                    SoapResponse response = new SoapResponse(success, result);
                    return gson.toJson(response);
                }

                case "read": {
                    ReadFile read = gson.fromJson(data, ReadFile.class);
                    String filePathJson = FileApi.downloadFile(read.fileID);
                    JsonObject filePathObj = gson.fromJson(filePathJson, JsonObject.class);
                    String filePath = filePathObj.get("filePath").getAsString();

                    DownloadResult result = null;
                    for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                        try {
                            result = client.readFile(filePath);
                            if (result != null) break;
                        } catch (Exception e) {
                            System.err.println("Error al leer archivo en nodo: " + e.getMessage());
                        }
                    }

                    if (result == null) {
                        return gson.toJson(new SoapResponse(false, "No llegan datos"));
                    }

                    SoapDownloadResponse resp = new SoapDownloadResponse(
                            true,
                            "Se pasan datos de archivo",
                            result.getFilename(),
                            result.getFileType(),
                            result.getContentBase64()
                    );
                    return gson.toJson(resp);
                }

                case "download": {
                    ReadFile download = gson.fromJson(data, ReadFile.class);
                    String filePathJson = FileApi.downloadFile(download.fileID);
                    JsonObject filePathObj = gson.fromJson(filePathJson, JsonObject.class);
                    String filePath = filePathObj.get("filePath").getAsString();

                    DownloadResult result = null;
                    for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                        try {
                            result = client.downloadFile(filePath);
                            if (result != null) break;
                        } catch (Exception e) {
                            System.err.println("Error al descargar archivo en nodo: " + e.getMessage());
                        }
                    }

                    if (result == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo descargar el archivo desde el nodo"));
                    }

                    SoapDownloadResponse resp = new SoapDownloadResponse(
                            true,
                            "Archivo descargado correctamente",
                            result.getFilename(),
                            result.getFileType(),
                            result.getContentBase64()
                    );
                    return gson.toJson(resp);
                }


                case "getAllFiles": {
                    var request = gson.fromJson(data, ListAllFiles.class);
                    String responseJson = FileApi.getAllFiles(request.userId);
                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener la lista de archivos del usuario"));
                    }
                    SoapResponse response = new SoapResponse(true, "Se han recuperado los archivos", responseJson);
                    return gson.toJson(response);
                }

                case "getFiles": {
                    var request = gson.fromJson(data, ListFilesByDir.class);
                    String responseJson = FileApi.getFiles(request.userId, request.dir);
                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener la lista de archivos del usuario"));
                    }
                    SoapResponse response = new SoapResponse(true, "Se han recuperado los archivos", responseJson);
                    return gson.toJson(response);
                }


                default:
                    return gson.toJson(new SoapResponse(false, "Acción '" + action + "' no soportada."));
            }
        } catch (Exception e) {
            return gson.toJson(new SoapResponse(false, "Error interno: " + e.getMessage()));
        }
    }

    @Override public String processAuthRequest(String action, String data) { return ""; }
    @Override public String processDirectoryRequest(String action, String data) { return ""; }
    @Override public String processShareRequest(String action, String data) { return ""; }
}
