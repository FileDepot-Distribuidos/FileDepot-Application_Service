package controller.implementations;

import apirest.ApiClient;
import apirest.DirectoryApi;
import apirest.FileApi;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.directory.*;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;
import jakarta.jws.WebService;

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

                    // 🔍 Mostrar datos recibidos del backend
                    System.out.println("📥 Datos recibidos para crear directorio:");
                    System.out.println("  path: " + dir.path);
                    System.out.println("  isRoot: " + dir.isRoot);
                    System.out.println("  parentDirectory: " + dir.parentDirectory);

                    client = GrpcNodeManager.getAvailableNodeClient();
                    String nodeResult = client.createDirectory(dir.path);

                    boolean successNode = nodeResult.toLowerCase().contains("correctamente");
                    boolean successDb = false;

                    if (successNode) {
                        try {
                            Integer parentId = null;

                            // ✅ Si el campo parentDirectory llega como ID (número), úsalo directamente
                            if (dir.parentDirectory != null && dir.parentDirectory.matches("\\d+")) {
                                parentId = Integer.parseInt(dir.parentDirectory);
                                System.out.println("parentDirectory recibido como ID → " + parentId);
                            } else {
                                // Si no, inferir path
                                String inferredParent = dir.parentDirectory;

                                if (inferredParent == null || inferredParent.isEmpty()) {
                                    String cleanPath = dir.path.endsWith("/") ? dir.path.substring(0, dir.path.length() - 1) : dir.path;
                                    int lastSlash = cleanPath.lastIndexOf("/");
                                    if (lastSlash != -1) {
                                        inferredParent = cleanPath.substring(0, lastSlash);
                                    }
                                }

                                System.out.println("Path inferido del padre: " + inferredParent);

                                if (inferredParent != null && !inferredParent.isEmpty()) {
                                    int possibleParent = DirectoryApi.getDirectoryIdByPath(inferredParent);
                                    System.out.println("ID del directorio padre encontrado: " + possibleParent);
                                    if (possibleParent != -1) {
                                        parentId = possibleParent;
                                    }
                                }
                            }

                            String[] parts = dir.path.split("/");
                            int ownerId = Integer.parseInt(parts[0]);

                            // ✅ Imprimir valores finales que se envían al DirectoryApi
                            System.out.println("🚀 Enviando a DirectoryApi.createDirectory:");
                            System.out.println("  path: " + dir.path);
                            System.out.println("  ownerId: " + ownerId);
                            System.out.println("  parentId: " + parentId);
                            System.out.println("  isRoot: " + dir.isRoot);

                            successDb = DirectoryApi.createDirectory(dir.path, ownerId, parentId, dir.isRoot);
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
                    int directoryId = rename.directoryID;

                    try {
                        String dirJson = ApiClient.get("/directorio/id/" + directoryId);

                        JsonObject dirInfo = JsonParser.parseString(dirJson).getAsJsonObject();

                        if (!dirInfo.has("path")) {
                            return gson.toJson(new SoapResponse(false, "No se encontró el directorio en la base de datos"));
                        }

                        String oldPath = dirInfo.get("path").getAsString();

                        String parentPath = oldPath.substring(0, oldPath.lastIndexOf('/'));
                        String fullNewName = parentPath + "/" + rename.newName;

                        // Renombrar en el nodo
                        String nodeResult = client.renameFile(oldPath, fullNewName);

                        boolean successNode = nodeResult.toLowerCase().contains("con éxito");
                        boolean successDb = DirectoryApi.renameDirectory(directoryId, fullNewName);
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
                        String dirJson = ApiClient.get("/directorio/id/" + directoryId);
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
                    System.out.println(directoryId);

                    try {
                        System.out.println("🗑️ Solicitud para eliminar directorio con ID: " + directoryId);

                        String dirJson = ApiClient.get("/directorio/id/" + directoryId);
                        System.out.println(dirJson);
                        System.out.println("[GET] /directorio/id/" + directoryId + " → respuesta: " + dirJson);

                        JsonElement parsed = JsonParser.parseString(dirJson);
                        if (!parsed.isJsonObject()) {
                            System.err.println("❌ No se encontró directorio con ID=" + directoryId + " → respuesta no es JSON válido: " + dirJson);
                            return gson.toJson(new SoapResponse(false, "No se encontró el directorio en la base de datos"));
                        }

                        JsonObject dirInfo = parsed.getAsJsonObject();
                        if (!dirInfo.has("path")) {
                            System.err.println("❌ La respuesta del backend no contiene el campo 'path': " + dirInfo);
                            return gson.toJson(new SoapResponse(false, "La información del directorio no contiene el campo 'path'"));
                        }

                        String path = dirInfo.get("path").getAsString();
                        System.out.println("📂 Path del directorio a eliminar: " + path);

                        String nodeResult = client.deleteFile(path);
                        System.out.println("🛰️ Respuesta del nodo: " + nodeResult);

                        boolean successNode = nodeResult.toLowerCase().contains("correctamente") || nodeResult.toLowerCase().contains("éxito");
                        System.out.println("✅ ¿Nodo eliminó correctamente?: " + successNode);

                        boolean successDb = DirectoryApi.deleteDirectory(directoryId);
                        System.out.println("🗃️ ¿DB eliminada correctamente?: " + successDb);

                        boolean totalSuccess = successNode && successDb;
                        String finalMessage = totalSuccess
                                ? "Directorio eliminado correctamente"
                                : successNode
                                ? "Nodo eliminado, pero fallo en DB"
                                : "Fallo al eliminar directorio";

                        System.out.println("📤 Resultado final: " + finalMessage);
                        return gson.toJson(new SoapResponse(totalSuccess, finalMessage));

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al eliminar directorio: " + e.getMessage()));
                    }
                }

                case "getAllDirs": {
                    var request = gson.fromJson(data, ListAllDirectories.class);

                    String responseJson = DirectoryApi.getAllDirs(request.userId);

                    System.out.println("Respuesta enviada al backend cliente: " + responseJson);

                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener la lista de directorios del usuario"));
                    }

                    SoapResponse response = new SoapResponse(true, "Se han recuperado los directorios", responseJson);
                    return gson.toJson(response);
                }

                case "getDirs": {
                    var request = gson.fromJson(data, ListDirectoriesByDir.class);

                    String responseJson = DirectoryApi.getDirs(request.userId, request.dir);

                    System.out.println("Respuesta enviada al backend cliente: " + responseJson);

                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener la lista de directorios del usuario"));
                    }

                    SoapResponse response = new SoapResponse(true, "Se han recuperado los directorios", responseJson);
                    return gson.toJson(response);
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
