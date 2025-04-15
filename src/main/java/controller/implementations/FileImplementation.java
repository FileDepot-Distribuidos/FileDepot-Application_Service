package controller.implementations;

import apirest.FileApi;
import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.files.*;
import dto.files.ListAll;
import filesystem.ListAllResponse;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;
import jakarta.jws.WebService;

@WebService(endpointInterface = "controller.FileDepotService")
public class FileImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processFileRequest(String action, String data) {
        FileSystemClient client = GrpcNodeManager.getAvailableNodeClient();

        try {
            switch (action) {
                case "upload": {
                    UploadFile upload = gson.fromJson(data, UploadFile.class);

                    System.out.println(gson.toJson(upload));

                    String directory = upload.owner + "/";

                    try {
                        UploadResult result = client.uploadBase64File(upload.name, upload.base64, directory);

                        if (!result.success || result.nodeId == null || result.nodeId.isEmpty()) {
                            return gson.toJson(new SoapResponse(false, "Error al subir el archivo: " + result.message));
                        }

                        upload.name = result.name;
                        String fileType = result.type;
                        String nodeId = result.nodeId;

                        System.out.println(gson.toJson(upload.size));

                        boolean saved = apirest.FileApi.registerFile(upload, nodeId, fileType);

                        if (!saved) {
                            return gson.toJson(new SoapResponse(false, "Archivo subido pero no registrado en la base de datos"));
                        }

                        return gson.toJson(new SoapResponse(true, "Archivo subido y registrado correctamente"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        return gson.toJson(new SoapResponse(false, "Error interno al subir archivo: " + e.getMessage()));
                    }
                }


                case "delete": {
                    DeleteFile delete = gson.fromJson(data, DeleteFile.class);

                    // Eliminar en nodo
                    String result = client.deleteFile(delete.fileID);
                    boolean successNode = result.toLowerCase().contains("correctamente");

                    String fileNameOnly = delete.fileID.contains("/")
                            ? delete.fileID.substring(delete.fileID.lastIndexOf("/") + 1)
                            : delete.fileID;

                    // 3. Eliminar en DB
                    boolean successDb = false;
                    if (successNode) {
                        successDb = FileApi.deleteFile(fileNameOnly);
                    }

                    boolean finalSuccess = successNode && successDb;
                    String message = finalSuccess
                            ? "Archivo eliminado correctamente del nodo y base de datos"
                            : successNode
                            ? "Archivo eliminado del nodo, pero no de la base de datos"
                            : "No se pudo eliminar el archivo";

                    SoapResponse response = new SoapResponse(finalSuccess, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "rename": {
                    RenameFile rename = gson.fromJson(data, RenameFile.class);

                    // 1. Renombrar en nodo
                    String result = client.renameFile(rename.oldFileName, rename.newFileName);
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
                    ReadFile read = gson.fromJson(data, ReadFile.class);
                    SoapResponse response = new SoapResponse(true, "Contenido de: " + read.fileID);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "download": {
                    ReadFile download = gson.fromJson(data, ReadFile.class);
                    SoapResponse response = new SoapResponse(true, "Archivo disponible para descarga: " + download.fileID);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

//                case "listAll": {
//                    ListAll request = gson.fromJson(data, ListAll.class);
//                    ListAllResponse responseGrpc = client.listAll(request.path);
//
//                    // Puedes empaquetar como JSON combinado (archivos y carpetas)
//                    var result = new java.util.HashMap<String, Object>();
//                    result.put("directories", responseGrpc.getDirectoriesList());
//                    result.put("files", responseGrpc.getFilesList());
//
//                    SoapResponse response = new SoapResponse(true, gson.toJson(result));
//                    String json = gson.toJson(response);
//                    System.out.println("Respuesta enviada al backend cliente: " + json);
//                    return json;
//                }

                case "getFiles": {
                    var request = gson.fromJson(data, dto.files.ListAll.class);

                    String responseJson = FileApi.getFiles(request.userId);

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
