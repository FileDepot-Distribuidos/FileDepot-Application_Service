package controller;

import com.google.gson.Gson;
import dto.*;
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
                case "createDirectory":
                    CreateDirectory create = gson.fromJson(data, CreateDirectory.class);
                    String path = create.isRoot ? create.path : create.parentDirectory + "/" + create.path;
                    String createResult = client.createDirectory(path);
                    return gson.toJson(new SoapResponse(true, createResult));

                case "addSubdirectory":
                    Subdirectory sub = gson.fromJson(data, Subdirectory.class);
                    String subResult = client.createSubdirectory(sub.parentDirectory, sub.subdirectory);
                    return gson.toJson(new SoapResponse(true, subResult));

                case "renameDirectory":
                    RenameDirectory rename = gson.fromJson(data, RenameDirectory.class);
                    String renameResult = client.renameFile(rename.directoryID, rename.newName);
                    return gson.toJson(new SoapResponse(true, renameResult));

                case "moveDirectory":
                    MoveDirectory move = gson.fromJson(data, MoveDirectory.class);
                    String moveResult = client.moveFile(move.directoryID, move.newParentDirectory);
                    return gson.toJson(new SoapResponse(true, moveResult));

                case "deleteDirectory":
                    DeleteDirectory delete = gson.fromJson(data, DeleteDirectory.class);
                    String deleteResult = client.deleteFile(delete.directoryID);
                    return gson.toJson(new SoapResponse(true, deleteResult));

                case "listDirectories":
                    var files = client.listFiles("/");
                    return gson.toJson(new SoapResponse(true, gson.toJson(files)));

                default:
                    return gson.toJson(new SoapResponse(false, "Acción de directorio no soportada: " + action));
            }
        } catch (Exception e) {
            return gson.toJson(new SoapResponse(false, "Error procesando directorio: " + e.getMessage()));
        }
    }

    @Override public String ping() { return ""; }
    @Override public String processAuthRequest(String action, String data) { return ""; }
    @Override public String processFileRequest(String action, String data) { return ""; }
    @Override public String processShareRequest(String action, String data) { return ""; }
}
