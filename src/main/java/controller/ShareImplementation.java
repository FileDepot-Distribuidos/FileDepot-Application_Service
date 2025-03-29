package controller;

import com.google.gson.Gson;
import dto.*;
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
                case "shareDirectory":
                    Share share = gson.fromJson(data, Share.class);
                    String tipo = action.equals("shareFile") ? "archivo" : "directorio";
                    return gson.toJson(new SoapResponse(true,
                            tipo + " compartido con " + share.sharedWith + " por " + share.sharedBy));

                case "revokeAccess":
                    RevokeAccess revoke = gson.fromJson(data, RevokeAccess.class);
                    return gson.toJson(new SoapResponse(true,
                            "Acceso revocado para ID: " + revoke.shareID));

                case "listSharedFiles":
                    String[] shared = {"/docs/tarea.pdf", "/proyectos/code/"};
                    return gson.toJson(new SoapResponse(true, gson.toJson(shared)));

                default:
                    return gson.toJson(new SoapResponse(false, "Acción de compartido no soportada: " + action));
            }
        } catch (Exception e) {
            return gson.toJson(new SoapResponse(false, "Error en acción de compartido: " + e.getMessage()));
        }
    }

    @Override public String ping() { return ""; }
    @Override public String processAuthRequest(String action, String data) { return ""; }
    @Override public String processFileRequest(String action, String data) { return ""; }
    @Override public String processDirectoryRequest(String action, String data) { return ""; }
}
