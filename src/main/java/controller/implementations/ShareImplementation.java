package controller.implementations;

import apirest.ShareApi;
import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.share.*;
import jakarta.jws.WebService;
import rmi.AuthService;

@WebService(endpointInterface = "controller.FileDepotService")
public class ShareImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processShareRequest(String action, String data) {
        try {
            switch (action) {
                case "shareFile": {

                    ShareFile shareFile = gson.fromJson(data, ShareFile.class);

                    int userId = AuthService.getService().getUserByEmail(shareFile.sharedWith);

                    if (userId <= 0) {
                        String message = "El usuario con email " + shareFile.sharedWith + " no existe.";
                        SoapResponse response = new SoapResponse(false, message);
                        String json = gson.toJson(response);
                        System.out.println("Respuesta enviada al backend cliente: " + json);
                        return json;
                    } else if (shareFile.sharedBy == userId) {
                        String message = "El usuario no se puede compartir un archivo a sí mismo.";
                        SoapResponse response = new SoapResponse(false, message);
                        String json = gson.toJson(response);
                        System.out.println("Respuesta enviada al backend cliente: " + json);
                        return json;
                    }

                    shareFile.sharedWithId = userId;

                    boolean success = ShareApi.shareFile(shareFile);

                    String message = success
                            ? "Archivo " + shareFile.sharedFile + " compartido con " + shareFile.sharedWith + " por " + shareFile.sharedBy
                            : "No se pudo compartir el archivo";

                    SoapResponse response = new SoapResponse(success, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "shareDirectory": {

                    ShareDirectory shareDirectory = gson.fromJson(data, ShareDirectory.class);

                    int userId = AuthService.getService().getUserByEmail(shareDirectory.sharedWith);

                    if (userId <= 0) {
                        String message = "El usuario con email " + shareDirectory.sharedWith + " no existe.";
                        SoapResponse response = new SoapResponse(false, message);
                        String json = gson.toJson(response);
                        System.out.println("Respuesta enviada al backend cliente: " + json);
                        return json;
                    } else if (shareDirectory.sharedBy == userId) {
                        String message = "El usuario no se puede compartir una carpeta a sí mismo.";
                        SoapResponse response = new SoapResponse(false, message);
                        String json = gson.toJson(response);
                        System.out.println("Respuesta enviada al backend cliente: " + json);
                        return json;
                    }

                    shareDirectory.sharedWithId = userId;

                    boolean success = ShareApi.shareDirectory(shareDirectory);

                    String message = success
                            ? "Carpeta " + shareDirectory.sharedDirectory + " compartida con " + shareDirectory.sharedWith + " por " + shareDirectory.sharedBy
                            : "No se pudo compartir la carpeta";

                    SoapResponse response = new SoapResponse(success, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

//                case "revokeAccess": {
//                    RevokeAccess revoke = gson.fromJson(data, RevokeAccess.class);
//                    boolean success = ShareApi.revokeAccess(revoke.shareID);
//
//                    String message = success
//                            ? "Acceso revocado para ID: " + revoke.shareID
//                            : "No se pudo revocar el acceso";
//
//                    SoapResponse response = new SoapResponse(success, message);
//                    String json = gson.toJson(response);
//                    System.out.println("Respuesta enviada al backend cliente: " + json);
//                    return json;
//                }
//
                case "listSharedFiles": {
                    ListSharedFiles request = gson.fromJson(data, ListSharedFiles.class);

                    String responseJson = ShareApi.listSharedFiles(request.userId);

                    if (responseJson == null) {
                        return gson.toJson(new SoapResponse(false, "No se pudo obtener los archivos compartidos"));
                    }

                    SoapResponse response = new SoapResponse(true, "Se han recuperado los archivos compartidos", responseJson);
                    return gson.toJson(response);
                }

                default: {
                    SoapResponse response = new SoapResponse(false, "Acción de compartido no soportada: " + action);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Agrega trazabilidad
            SoapResponse response = new SoapResponse(false, "Error en acción de compartido: " + e.getMessage());
            String json = gson.toJson(response);
            System.out.println("Respuesta enviada al backend cliente: " + json);
            return json;
        }
    }

    @Override public String processAuthRequest(String action, String data) { return ""; }
    @Override public String processFileRequest(String action, String data) { return ""; }
    @Override public String processDirectoryRequest(String action, String data) { return ""; }
}
