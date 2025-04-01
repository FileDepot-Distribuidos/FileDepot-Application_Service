package controller.implementations;

import com.google.gson.Gson;
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
                    CreateDirectory create = gson.fromJson(data, CreateDirectory.class);
                    String result = client.createDirectory(create.path);
                    boolean success = result.toLowerCase().contains("correctamente");
                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
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
                    String result = client.renameFile(rename.directoryID, rename.newName);
                    boolean success = result.toLowerCase().contains("correctamente");
                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "moveDirectory": {
                    MoveDirectory move = gson.fromJson(data, MoveDirectory.class);
                    String result = client.moveFile(move.directoryID, move.newParentDirectory);
                    boolean success = result.toLowerCase().contains("correctamente");
                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "deleteDirectory": {
                    DeleteDirectory delete = gson.fromJson(data, DeleteDirectory.class);
                    String result = client.deleteFile(delete.directoryID);
                    boolean success = result.toLowerCase().contains("correctamente");
                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "listDirectories": {
                    var files = client.listFiles("/");
                    SoapResponse response = new SoapResponse(true, gson.toJson(files));
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
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
