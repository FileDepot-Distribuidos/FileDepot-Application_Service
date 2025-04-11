package apirest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DirectoryApi {

    public static boolean createRootDirectory(String email, int ownerId) {
        try {
            DirectoryPayload payload = new DirectoryPayload(email, ownerId);
            String json = new Gson().toJson(payload);

            boolean ok = ApiClient.post("/directory", json);
            if (ok) {
                System.out.println("Directorio creado para el usuario: " + email);
            } else {
                System.err.println("Error al crear directorio para " + email);
            }

            return ok;
        } catch (Exception e) {
            System.err.println("Excepción creando directorio: " + e.getMessage());
            return false;
        }
    }

    public static int getRootDirectoryId(String userId) {
        try {
            String response = ApiClient.get("/directory/root/" + userId);

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();

            if (json.has("directoryId")) {
                return json.get("directoryId").getAsInt();
            } else {
                System.err.println("No se encontró el campo 'directoryId' en la respuesta: " + response);
                return -1;
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo ID de directorio raíz para userId=" + userId + ": " + e.getMessage());
            return -1;
        }
    }

    static class DirectoryPayload {
        String path;
        String creation_date;
        int owner_id;
        Integer parent_directory_id;

        public DirectoryPayload(String email, int ownerId) {
            this.path = email + "/";
            this.creation_date = java.time.LocalDateTime.now().toString();
            this.owner_id = ownerId;
            this.parent_directory_id = null;
        }
    }
}
