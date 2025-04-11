package apirest;

import com.google.gson.Gson;
import dto.files.UploadFile;

import java.util.HashMap;
import java.util.Map;

public class FileApi {

    public static boolean registerFile(UploadFile file, String nodeId, String fileType) {
        Gson gson = new Gson();

        FilePayload payload = new FilePayload(file, nodeId, fileType);
        String json = gson.toJson(payload);

        System.out.println("Enviando archivo a la API REST: " + json);

        return ApiClient.post("/upload", json);
    }

    public static boolean deleteFile(String fileName) {
        try {
            String endpoint = "/delete/" + fileName;
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
            this.hash = "abc123";
            this.owner_id = f.owner;
            this.NODE_idNODE = Integer.parseInt(nodeId);
            int dirId = DirectoryApi.getRootDirectoryId(f.owner);
            this.DIRECTORY_idDIRECTORY = dirId != -1 ? dirId : null;
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
