package apirest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class DirectoryApi {

    // Crea directorio
    public static boolean createDirectory(String path, int ownerId, Integer parentId) {
        try {
            DirectoryPayload payload = new DirectoryPayload(path, ownerId, parentId);
            String json = new Gson().toJson(payload);
            return ApiClient.post("/directory", json);
        } catch (Exception e) {
            System.err.println("❌ Error POST /directory: " + e.getMessage());
            return false;
        }
    }

    // Crea un directorio raíz
    public static boolean createRootDirectory(String email, int ownerId) {
        try {
            DirectoryPayload payload = new DirectoryPayload(email + "/", ownerId, null);
            String json = new Gson().toJson(payload);

            boolean ok = ApiClient.post("/directory", json);
            if (ok) {
                System.out.println("Directorio raíz creado para el usuario: " + email);
            } else {
                System.err.println(" Error al crear directorio raíz para " + email);
            }

            return ok;
        } catch (Exception e) {
            System.err.println("❌ Excepción creando directorio raíz: " + e.getMessage());
            return false;
        }
    }

    // Obtiene el ID del directorio raíz
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

    // Obtiene el ID de directorio
    public static int getDirectoryIdByPath(String path) {
        try {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            String encoded = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
            String response = ApiClient.get("/directory/by-path/" + encoded);

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            if (json.has("directoryId")) {
                return json.get("directoryId").getAsInt();
            } else {
                System.err.println("No se encontró 'directoryId' para path=" + path + " → respuesta: " + response);
                return -1;
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo ID por path=" + path + ": " + e.getMessage());
            return -1;
        }
    }

    // Renombrar directorio
    public static boolean renameDirectory(String id, String newPath) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", Integer.parseInt(id));
            payload.addProperty("newPath", newPath);
            return ApiClient.put("/directory/rename", payload.toString());
        } catch (Exception e) {
            System.err.println("Error renombrando directorio: " + e.getMessage());
            return false;
        }
    }

    // Mover directorio
    public static boolean moveDirectory(String id, String newParentId, String newFullPath) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", Integer.parseInt(id));
            payload.addProperty("newParentId", Integer.parseInt(newParentId));
            payload.addProperty("newFullPath", newFullPath);
            return ApiClient.put("/directory/move", payload.toString());
        } catch (Exception e) {
            System.err.println("Error moviendo directorio: " + e.getMessage());
            return false;
        }
    }

    // Eliminar directorio
    public static boolean deleteDirectory(String id) {
        try {
            return ApiClient.delete("/directory/delete/" + id);
        } catch (Exception e) {
            System.err.println("Error eliminando directorio: " + e.getMessage());
            return false;
        }
    }


    // 🔹 Payload usado para enviar la estructura JSON a la API REST
    static class DirectoryPayload {
        String path;
        String creation_date;
        int owner_id;
        Integer parent_directory_id;

        public DirectoryPayload(String path, int ownerId, Integer parentId) {
            this.path = path;
            this.creation_date = LocalDateTime.now().toString();
            this.owner_id = ownerId;
            this.parent_directory_id = parentId;
        }
    }
}
