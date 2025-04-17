package controller.implementations;

import apirest.ApiClient;
import apirest.DirectoryApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.directory.*;
import dto.files.ListAll;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;
import jakarta.jws.WebService;

import java.util.List;

@WebService(endpointInterface = "controller.FileDepotService")
public class DirectoryImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processDirectoryRequest(String action, String data) {
        FileSystemClient client = GrpcNodeManager.getAvailableNodeClient();

        try {
            switch (action) {
                case "createDirectory": {
                    CreateDirectory dir = gson.fromJson(data, CreateDirectory.class);

                    client = GrpcNodeManager.getAvailableNodeClient();
                    String nodeResult = client.createDirectory(dir.path);

                    boolean successNode = nodeResult.toLowerCase().contains("correctamente");
                    boolean successDb = false;

                    if (successNode) {
                        try {
                            Integer parentId = null;
                            if (!dir.isRoot && dir.parentDirectory != null && !dir.parentDirectory.isEmpty()) {
                                int possibleParent = DirectoryApi.getDirectoryIdByPath(dir.parentDirectory);
                                if (possibleParent != -1) {
                                    parentId = possibleParent;
                                }
                            }

                            String[] parts = dir.path.split("/");
                            int ownerId = Integer.parseInt(parts[0]);

                            successDb = DirectoryApi.createDirectory(dir.path, ownerId, parentId);
                        } catch (Exception e) {
                            System.err.println("Error al registrar directorio en DB: " + e.getMessage());
                        }
                    }

                    boolean totalSuccess = successNode && successDb;

                    return gson.toJson(new SoapResponse(
                            totalSuccess,
                            totalSuccess ? "Directorio creado exitosamente" :
                                    successNode ? "Nodo creado, pero no se guardó en la base de datos" :
                                            "No se pudo crear el directorio"
                    ));
                }

                case "addSubdirectory": {
                    Subdirectory sub = gson.fromJson(data, Subdirectory.class);
                    String result = client.createSubdirectory(sub.parentDirectory, sub.subdirectory);
                    boolean success = result.toLowerCase().contains("correctamente");
                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "renameDirectory": {
                    RenameDirectory rename = gson.fromJson(data, RenameDirectory.class);
                    String directoryId = rename.directoryID;
                    String newPath = rename.newName;

                    try {
                        String dirJson = ApiClient.get("/directory/by-id/" + directoryId);

                        JsonObject dirInfo = JsonParser.parseString(dirJson).getAsJsonObject();

                        if (!dirInfo.has("path")) {
                            return gson.toJson(new SoapResponse(false, "No se encontró el directorio en la base de datos"));
                        }

                        String oldPath = dirInfo.get("path").getAsString();

                        // Renombrar en el nodo
                        String nodeResult = client.renameFile(oldPath, newPath);

                        boolean successNode = nodeResult.toLowerCase().contains("con éxito");
                        boolean successDb = DirectoryApi.renameDirectory(directoryId, newPath);
                        boolean finalSuccess = successNode && successDb;

                        String message = finalSuccess
                                ? "Directorio renombrado correctamente"
                                : successNode
                                ? "Nodo renombrado, pero error en base de datos"
                                : "Error al renombrar directorio en nodo";

                        System.out.println("Resultado: " + message);
                        return gson.toJson(new SoapResponse(finalSuccess, message));

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al renombrar directorio: " + e.getMessage()));
                    }
                }


                case "moveDirectory": {
                    MoveDirectory move = gson.fromJson(data, MoveDirectory.class);
                    String directoryId = move.directoryID;
                    String newParentPath = move.newParentDirectory;

                    try {
                        String dirJson = ApiClient.get("/directory/by-id/" + directoryId);
                        JsonObject dirInfo = JsonParser.parseString(dirJson).getAsJsonObject();

                        if (!dirInfo.has("path")) {
                            return gson.toJson(new SoapResponse(false, "No se encontró el directorio en la base de datos"));
                        }

                        String oldPath = dirInfo.get("path").getAsString();

                        String[] parts = oldPath.split("/");
                        String folderName = parts[parts.length - 1];

                        String newFullPath = newParentPath.endsWith("/") ? newParentPath + folderName : newParentPath + "/" + folderName;

                        String nodeResult = client.moveFile(oldPath, newFullPath);
                        boolean successNode = nodeResult.toLowerCase().contains("con éxito");

                        int newParentId = DirectoryApi.getDirectoryIdByPath(newParentPath);

                        boolean successDb = DirectoryApi.moveDirectory(directoryId, String.valueOf(newParentId), newFullPath);

                        boolean finalSuccess = successNode && successDb;
                        String message = finalSuccess
                                ? "Directorio movido correctamente"
                                : successNode
                                ? "Nodo movido, pero error en base de datos"
                                : "Error al mover directorio en nodo";

                        System.out.println("Resultado: " + message);
                        return gson.toJson(new SoapResponse(finalSuccess, message));

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al mover directorio: " + e.getMessage()));
                    }
                }


                case "deleteDirectory": {
                    DeleteDirectory delete = gson.fromJson(data, DeleteDirectory.class);
                    String directoryId = delete.directoryID;

                    try {
                        String dirJson = ApiClient.get("/directory/by-id/" + directoryId);
                        JsonObject dirInfo = JsonParser.parseString(dirJson).getAsJsonObject();

                        if (!dirInfo.has("path")) {
                            return gson.toJson(new SoapResponse(false, "No se encontró el directorio en la base de datos"));
                        }

                        String path = dirInfo.get("path").getAsString();

                        String nodeResult = client.deleteFile(path); // gRPC con path, no ID
                        boolean successNode = nodeResult.toLowerCase().contains("correctamente") || nodeResult.toLowerCase().contains("éxito");

                        boolean successDb = DirectoryApi.deleteDirectory(directoryId);

                        boolean totalSuccess = successNode && successDb;
                        return gson.toJson(new SoapResponse(totalSuccess,
                                totalSuccess ? "Directorio eliminado correctamente" :
                                        successNode ? "Nodo eliminado, pero fallo en DB" :
                                                "Fallo al eliminar directorio"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al eliminar directorio: " + e.getMessage()));
                    }
                }



                default: {
                    SoapResponse response = new SoapResponse(false, "Acción de directorio no soportada: " + action);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            SoapResponse response = new SoapResponse(false, "Error procesando directorio: " + e.getMessage());
            String json = gson.toJson(response);
            System.out.println("Respuesta enviada al backend cliente: " + json);
            return json;
        }
    }

    @Override public String processAuthRequest(String action, String data) { return ""; }
    @Override public String processFileRequest(String action, String data) { return ""; }
    @Override public String processShareRequest(String action, String data) { return ""; }
}
