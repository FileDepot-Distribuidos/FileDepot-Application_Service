package apirest;

import com.google.gson.Gson;
import dto.share.ShareDirectory;
import dto.share.ShareFile;

import java.util.HashMap;
import java.util.Map;

public class ShareApi {

    private static final Gson gson = new Gson();

    public static boolean shareFile(ShareFile shareFile) {
        try {
            String json = gson.toJson(shareFile);
            System.out.println("Compartiendo archivo: " + json);
            return ApiClient.post("/shareFile", json);
        } catch (Exception e) {
            System.err.println("Error al compartir archivo: " + e.getMessage());
            return false;
        }
    }

    public static boolean shareDirectory(ShareDirectory shareDirectory) {
        try {
            String json = gson.toJson(shareDirectory);
            System.out.println("Compartiendo carpeta: " + json);
            return ApiClient.post("/shareDirectory", json);
        } catch (Exception e) {
            System.err.println("Error al compartir carpeta: " + e.getMessage());
            return false;
        }
    }

//    public static boolean revokeAccess(int shareId) {
//        try {
//            String endpoint = "/share/revoke/" + shareId;
//            return ApiClient.delete(endpoint);
//        } catch (Exception e) {
//            System.err.println("Error al revocar acceso: " + e.getMessage());
//            return false;
//        }
//    }
//
    public static String listSharedFiles(int userId) {
        try {
            String endpoint = "/shared/" + userId;
            return ApiClient.get(endpoint);
        } catch (Exception e) {
            System.err.println("Error al listar archivos compartidos: " + e.getMessage());
            return null;
        }
    }
}
