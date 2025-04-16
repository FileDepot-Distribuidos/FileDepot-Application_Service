package controller.implementations;

import apirest.DirectoryApi;
import com.google.gson.Gson;
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
                            // Si es raíz, no tiene padre
                            Integer parentId = null;
                            if (!dir.isRoot && dir.parentDirectory != null && !dir.parentDirectory.isEmpty()) {
                                int possibleParent = DirectoryApi.getDirectoryIdByPath(dir.parentDirectory);
                                if (possibleParent != -1) {
                                    parentId = possibleParent;
                                }
                            }

                            // Extraer userId del path (ej: "15/proyectos" → "15")
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
