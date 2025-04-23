package controller.implementations;

import apirest.ApiClient;
import apirest.DirectoryApi;
import apirest.FileApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.FileDepotService;
import dto.SoapDownloadResponse;
import dto.SoapResponse;
import dto.files.*;
import dto.files.ListAllFiles;
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

                        if (directory == null) {
                            resultados.add(upload.name + ": No se pudo obtener el path del directorio destino");
                            continue;
                        }

                        List<FileSystemClient> clients = GrpcNodeManager.getAllClients();
                        UploadResult primerExito = null;

                        for (FileSystemClient client : clients) {
                            try {
                                UploadResult result = client.uploadBase64File(upload.name, upload.base64, directory);
                                if (result.success && primerExito == null && result.nodeId != null && !result.nodeId.isEmpty()) {
                                    primerExito = result;
                                }
                            } catch (Exception e) {
                                System.err.println("Error al subir archivo en nodo: " + e.getMessage());
                            }
                        }

                        if (primerExito == null) {
                            resultados.add(upload.name + ": Fallo en todos los nodos disponibles");
                            continue;
                        }

                        upload.name = primerExito.name;
                        String fileType = primerExito.type;
                        String nodeId = primerExito.nodeId;

                        boolean registered = FileApi.registerFile(upload, nodeId, fileType);
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
                    for (FileSystemClient client : GrpcNodeManager.getAllClients()) {
                        try {
                            result = client.moveFile(move.fileID, move.newDirectoryID);
                            if (result.toLowerCase().contains("correctamente")) {
                                success = true;
                            }
                        } catch (Exception e) {
                            System.err.println("Error al mover archivo en nodo: " + e.getMessage());
                        }
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
