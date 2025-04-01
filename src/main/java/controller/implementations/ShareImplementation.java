package controller.implementations;

import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.share.*;
import jakarta.jws.WebService;

@WebService(endpointInterface = "controller.FileDepotService")
public class ShareImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processShareRequest(String action, String data) {
        try {
            switch (action) {
                case "shareFile":
                case "shareDirectory": {
                    Share share = gson.fromJson(data, Share.class);
                    String tipo = action.equals("shareFile") ? "archivo" : "directorio";
                    String message = tipo + " compartido con " + share.sharedWith + " por " + share.sharedBy;
                    SoapResponse response = new SoapResponse(true, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "revokeAccess": {
                    RevokeAccess revoke = gson.fromJson(data, RevokeAccess.class);
                    String message = "Acceso revocado para ID: " + revoke.shareID;
                    SoapResponse response = new SoapResponse(true, message);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "listSharedFiles": {
                    String[] shared = {"/docs/tarea.pdf", "/proyectos/code/"};
                    SoapResponse response = new SoapResponse(true, gson.toJson(shared));
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                default: {
                    SoapResponse response = new SoapResponse(false, "Acción de compartido no soportada: " + action);
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
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
