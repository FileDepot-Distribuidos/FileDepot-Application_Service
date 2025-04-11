package dto.files;

public class UploadResult {
    public boolean success;
    public String message;
    public String name;
    public long size;
    public String type;
    public String nodeId;
    public String filePath;

    public UploadResult(filesystem.Response response) {
        this.success = response.getMessage().toLowerCase().contains("correctamente");
        this.message = response.getMessage();
        this.name = response.getFileName();
        this.size = response.getFileSize();
        this.type = response.getFileType();
        this.nodeId = response.getNodeId();
        this.filePath = response.getFilePath();
    }
}
