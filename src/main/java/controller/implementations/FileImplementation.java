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
        FileSystemClient client = GrpcNodeManager.getAvailableNodeClient();

        try {
            switch (action) {
                case "upload": {
                    UploadFileBatch batch = gson.fromJson(data, UploadFileBatch.class);

                    List<UploadFile> files = batch.files;
                    List<String> resultados = new ArrayList<>();

                    for (UploadFile upload : files) {
                        System.out.println("Procesando archivo: " + upload.name);

                        String directory = DirectoryApi.getDirectoryPathById(upload.directoryId);

                        if (directory == null) {
                            resultados.add(upload.name + ": No se pudo obtener el path del directorio destino");
                            continue;
                        }

                        try {
                            UploadResult result = client.uploadBase64File(upload.name, upload.base64, directory);

                            if (!result.success || result.nodeId == null || result.nodeId.isEmpty()) {
                                resultados.add(upload.name + ": Fallo al subir el archivo (" + result.message + ")");
                                continue;
                            }

                            upload.name = result.name;
                            String fileType = result.type;
                            String nodeId = result.nodeId;

                            boolean saved = FileApi.registerFile(upload, nodeId, fileType);

                            if (!saved) {
                                resultados.add(upload.name + ": Subido pero no registrado en la base de datos");
                            } else {
                                resultados.add(upload.name + ": Subido y registrado correctamente");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            resultados.add(upload.name + ": Error interno al subir (" + e.getMessage() + ")");
                        }
                    }

                    boolean todosBien = resultados.stream().allMatch(s -> s.contains("Subido y registrado correctamente"));
                    String resumen = String.join("\n", resultados);

                    return gson.toJson(new SoapResponse(todosBien, resumen));
                }



                case "delete": {
                    DeleteFile delete = gson.fromJson(data, DeleteFile.class);
                    String fileId = delete.fileID;

                    try {
                        // Obtener información del archivo desde la API
                        String fileInfoJson = ApiClient.get("/file/byId/" + fileId);

                        JsonObject fileInfo = JsonParser.parseString(fileInfoJson).getAsJsonObject();


                        if (!fileInfo.has("name") || !fileInfo.has("owner_id")) {
                            return gson.toJson(new SoapResponse(false, "Archivo no encontrado en la base de datos"));
                        }

                        String fileName = fileInfo.get("name").getAsString();
                        String ownerId = fileInfo.get("owner_id").getAsString();

                        // Construir path en el nodo
                        String filePath = ownerId + "/" + fileName;

                        // Eliminar archivo en nodo
                        String result = client.deleteFile(filePath);
                        boolean successNode = result.toLowerCase().contains("correctamente");

                        // Eliminar archivo en la base de datos
                        boolean successDb = false;
                        if (successNode) {
                            successDb = FileApi.deleteFile(fileId);
                        }

                        // Resultado final
                        boolean finalSuccess = successNode && successDb;
                        String message = finalSuccess
                                ? "Archivo eliminado correctamente del nodo y la base de datos"
                                : successNode
                                ? "Archivo eliminado del nodo, pero no de la base de datos"
                                : "No se pudo eliminar el archivo del nodo";

                        System.out.println("Resultado: " + message);
                        SoapResponse response = new SoapResponse(finalSuccess, message);
                        return gson.toJson(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al eliminar archivo: " + e.getMessage()));
                    }
                }



                case "rename": {
                    RenameFile rename = gson.fromJson(data, RenameFile.class);

                    // Construye los paths completos usando userId
                    String userPathPrefix = rename.userId + "/";
                    String fullOldName = userPathPrefix + rename.oldFileName;
                    String fullNewName = userPathPrefix + rename.newFileName;

                    // 1. Renombrar en nodo
                    String result = client.renameFile(fullOldName, fullNewName);
                    boolean successNode = result.toLowerCase().contains("renombrado");

                    // 2. Renombrar en la base de datos
                    String oldNameOnly = rename.oldFileName.contains("/")
                            ? rename.oldFileName.substring(rename.oldFileName.lastIndexOf("/") + 1)
                            : rename.oldFileName;

                    String newNameOnly = rename.newFileName.contains("/")
                            ? rename.newFileName.substring(rename.newFileName.lastIndexOf("/") + 1)
                            : rename.newFileName;

                    boolean successDb = false;
                    if (successNode) {
                        successDb = FileApi.renameFile(oldNameOnly, newNameOnly);
                    }

                    boolean finalSuccess = successNode && successDb;

                    String message = finalSuccess
                            ? "Archivo renombrado en nodo y base de datos correctamente"
                            : successNode
                            ? "Archivo renombrado en nodo, pero no en base de datos"
                            : "Error al renombrar archivo en el nodo";

                    SoapResponse response = new SoapResponse(finalSuccess, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }


                case "move": {
                    MoveFile move = gson.fromJson(data, MoveFile.class);
                    String result = client.moveFile(move.fileID, move.newDirectoryID);
                    boolean success = result.toLowerCase().contains("correctamente");

                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "read": {
                  // Parseamos la respuesta que contiene el filePath
                  ReadFile read = gson.fromJson(data, ReadFile.class);

                  // Obtenemos la ruta desde la BD
                  String filePathJson = FileApi.downloadFile(read.fileID);

                  // Extraemos solo el filePath del JSON
                  JsonObject filePathObj = gson.fromJson(filePathJson, JsonObject.class);
                  String filePath = filePathObj.get("filePath").getAsString();

                  //  Llamamos al nodo y recibimos el archivo
                  DownloadResult result = client.readFile(filePath);

                  // Si no obtuvimos un resultado válido
                  if (result == null) {
                    return gson.toJson(new SoapResponse(false, "No llegan datos"));
                  }

                  // 3. Creamos la respuesta con los datos recibidos
                  SoapDownloadResponse resp = new SoapDownloadResponse(
                    true,
                    "Se pasan datos de archivo",
                    result.getFilename(),
                    result.getFileType(),
                    result.getContentBase64()
                  );

                  // Convertimos la respuesta a JSON
                  String json = gson.toJson(resp);
                  System.out.println("Respuesta enviada al backend cliente: " + json);

                  return json;
                }

               case "download": {
                // Parseamos la respuesta que contiene el filePath
                ReadFile download = gson.fromJson(data, ReadFile.class);

                // Obtenemos la ruta desde la BD
                String filePathJson = FileApi.downloadFile(download.fileID);

                // Extraemos solo el filePath del JSON
                JsonObject filePathObj = gson.fromJson(filePathJson, JsonObject.class);
                String filePath = filePathObj.get("filePath").getAsString();

                //  Llamamos al nodo y recibimos el archivo
                DownloadResult result = client.downloadFile(filePath);

                // Si no obtuvimos un resultado válido
                if (result == null) {
                  return gson.toJson(new SoapResponse(false, "No se pudo descargar el archivo desde el nodo"));
                }

                // 3. Creamos la respuesta con los datos recibidos
                SoapDownloadResponse resp = new SoapDownloadResponse(
                  true,
                  "Archivo descargado correctamente",
                  result.getFilename(),
                  result.getFileType(),
                  result.getContentBase64()
                );

                // Convertimos la respuesta a JSON
                String json = gson.toJson(resp);
                System.out.println("Respuesta enviada al backend cliente: " + json);

                return json;
              }

                case "getAllFiles": {
                    var request = gson.fromJson(data, ListAllFiles.class);

                    String responseJson = FileApi.getAllFiles(request.userId);

                    System.out.println("Respuesta enviada al backend cliente: " + responseJson);

                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener la lista de archivos del usuario"));
                    }

                    SoapResponse response = new SoapResponse(true, "Se han recuperado los archivos", responseJson);
                    return gson.toJson(response);
                }

                case "getFiles": {
                    var request = gson.fromJson(data, ListFilesByDir.class);

                    String responseJson = FileApi.getFiles(request.userId, request.dir);

                    System.out.println("Respuesta enviada al backend cliente: " + responseJson);

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
