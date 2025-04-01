package controller.implementations;

import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.files.*;
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
                    String result = client.uploadBase64File(upload.name, upload.base64, "/");
                    boolean success = result.toLowerCase().contains("correctamente");

                    SoapResponse response = new SoapResponse(success, result);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "delete": {
                    DeleteFile delete = gson.fromJson(data, DeleteFile.class);
                    String result = client.deleteFile(delete.fileID);
                    boolean success = result.toLowerCase().contains("correctamente");

                    SoapResponse response = new SoapResponse(success, result);
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
