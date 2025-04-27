package apirest;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class DirectoryApi {

    public static boolean createDirectory(String path, int ownerId, Integer parentId, boolean isRoot) {
        try {
            DirectoryPayload payload = new DirectoryPayload(path, ownerId, parentId, isRoot);

            String json = new GsonBuilder().serializeNulls().create().toJson(payload);

            System.out.println("parentId a enviar: " + parentId);
            System.out.println("\ud83d\udce6 Payload enviado a /directory:");
            System.out.println(json);

            return ApiClient.post("/directory", json);
        } catch (Exception e) {
            System.err.println("\u274c Error POST /directory: " + e.getMessage());
            return false;
        }
    }


    public static boolean createRootDirectory(String email, int ownerId) {
        try {
            DirectoryPayload payload = new DirectoryPayload(email + "/", ownerId, null, true);
            String json = new Gson().toJson(payload);
            return ApiClient.post("/directory", json);
        } catch (Exception e) {
            System.err.println("Excepción creando directorio raíz: " + e.getMessage());
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

    public static int getDirectoryIdByPath(String path) {
        try {
            String encoded = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
            String response = ApiClient.get("/directorio/path/" + encoded);

            JsonElement parsed = JsonParser.parseString(response);

            if (!parsed.isJsonObject()) {
                System.err.println("No se encontró 'directoryId' para path=" + path + " → respuesta: " + response);
                return -1;
            }

            JsonObject json = parsed.getAsJsonObject();
            return json.get("directoryId").getAsInt();

        } catch (Exception e) {
            System.err.println("Error obteniendo ID por path=" + path + ": " + e.getMessage());
            return -1;
        }
    }

    public static String getDirectoryPathById(int directoryId) {
        try {
            String response = ApiClient.get("/directorio/id/" + directoryId);
            JsonElement parsed = JsonParser.parseString(response);
            if (!parsed.isJsonObject()) return null;

            JsonObject obj = parsed.getAsJsonObject();
            if (obj.has("path")) {
                return obj.get("path").getAsString();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener path del directorio: " + e.getMessage());
        }
        return null;
    }

    public static boolean renameDirectory(int id, String newPath) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", id);
            payload.addProperty("newPath", newPath);
            return ApiClient.put("/directory/rename", payload.toString());
        } catch (Exception e) {
            System.err.println("Error renombrando directorio: " + e.getMessage());
            return false;
        }
    }

    public static boolean moveDirectory(int id, int newParentId, String newFullPath) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", id);
            payload.addProperty("newParentId", newParentId);
            payload.addProperty("newFullPath", newFullPath);
            return ApiClient.put("/directory/move", payload.toString());
        } catch (Exception e) {
            System.err.println("Error moviendo directorio: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteDirectory(String id) {
        try {
            System.out.println("Solicitud de eliminación para ID: " + id);

            String endpoint = "/directory/delete/" + id;
            boolean result = ApiClient.delete(endpoint);

            System.out.println("DELETE enviado a endpoint: " + endpoint);
            System.out.println("Resultado de la operación: " + result);

            return result;
        } catch (Exception e) {
            System.err.println("Error eliminando directorio: " + e.getMessage());
            return false;
        }
    }


    public static String getAllDirs(int userId) {
        try {
            String endpoint = "/directory/" + userId;
            return ApiClient.get(endpoint);
        } catch (Exception e) {
            System.err.println("Error al obtener directorios del usuario: " + e.getMessage());
            return null;
        }
    }

    public static String getDirs(int userId, int directoryId) {
        try {
            String endpoint = "/directory/" + userId + "/" + directoryId;
            return ApiClient.get(endpoint);
        } catch (Exception e) {
            System.err.println("Error al obtener directorios del usuario: " + e.getMessage());
            return null;
        }
    }


    static class DirectoryPayload {
        String path;
        String creation_date;
        int owner_id;

        @SerializedName("parentDirectory")
        Integer parent_directory_id;

        boolean isRoot;

        public DirectoryPayload(String path, int ownerId, Integer parentId, boolean isRoot) {
            this.path = path;
            this.creation_date = LocalDateTime.now().toString();
            this.owner_id = ownerId;
            this.parent_directory_id = parentId;
            this.isRoot = isRoot;
        }
    }

}
