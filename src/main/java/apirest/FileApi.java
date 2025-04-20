package apirest;

import com.google.gson.Gson;
import dto.files.UploadFile;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.util.Base64;

public class FileApi {

    public static boolean registerFile(UploadFile file, String nodeId, String fileType) {
        Gson gson = new Gson();

        FilePayload payload = new FilePayload(file, nodeId, fileType);
        String json = gson.toJson(payload);

        System.out.println("Enviando archivo a la API REST: " + json);

        return ApiClient.post("/upload", json);
    }

    public static boolean deleteFile(String fileId) {
        try {
            String endpoint = "/delete/" + fileId;
            return ApiClient.delete(endpoint);
        } catch (Exception e) {
            System.err.println("Error al eliminar archivo: " + e.getMessage());
            return false;
        }
    }

    public static boolean renameFile(String oldName, String newName) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("oldFileName", oldName);
            body.put("newFileName", newName);

            String json = new Gson().toJson(body);
            return ApiClient.put("/rename", json);
        } catch (Exception e) {
            System.err.println("Error al renombrar archivo: " + e.getMessage());
            return false;
        }
    }

    public static String getAllFiles(int userId) {
        try {
            String endpoint = "/files/" + userId;
            return ApiClient.get(endpoint);
        } catch (Exception e) {
            System.err.println("Error al obtener archivos del usuario: " + e.getMessage());
            return null;
        }
    }

    public static String getFiles(int userId, int directoryId) {
        try {
            String endpoint = "/files/" + userId + "/" + directoryId;
            return ApiClient.get(endpoint);
        } catch (Exception e) {
            System.err.println("Error al obtener archivos del usuario: " + e.getMessage());
            return null;
        }
    }

    public static String generateHash(String base64Content) {
        try {

            String cleanBase64 = base64Content;

            if (base64Content.contains(",")) {
                cleanBase64 = base64Content.split(",", 2)[1];
            }

            byte[] fileBytes = Base64.getDecoder().decode(cleanBase64);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);

            // Convertir a string hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String downloadFile(String fileID) {
        try {
            String endpoint = "/download/" + fileID;
            String response = ApiClient.get(endpoint);

            System.out.println("Respuesta de descarga: " + response);
            return response;
        } catch (Exception e) {
            System.err.println("Error al descargar archivo: " + e.getMessage());
            return null;
        }
    }




    static class FilePayload {
        String name;
        String type;
        long size;
        String hash;
        String owner_id;
        int NODE_idNODE;
        Integer DIRECTORY_idDIRECTORY;

        public FilePayload(UploadFile f, String nodeId, String fileType) {
            this.name = f.name;
            this.type = fileType;
            this.size = f.size;
            this.hash = generateHash(f.base64);
            this.owner_id = f.owner;
            this.NODE_idNODE = Integer.parseInt(nodeId);

            if (f.directoryId != 0) {
                this.DIRECTORY_idDIRECTORY = f.directoryId;
            } else {
                int dirId = DirectoryApi.getRootDirectoryId(f.owner);
                this.DIRECTORY_idDIRECTORY = dirId != -1 ? dirId : null;
            }
        }

        private String guessMimeType(String filename) {
            if (filename.endsWith(".pdf")) return "application/pdf";
            if (filename.endsWith(".png")) return "image/png";
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
            if (filename.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            return "application/octet-stream";
        }
    }
}
