package dto.files;

public class UploadFile {
    public String base64;
    public String name;
    public long size;
    public String owner;
    public int directoryId;

    public UploadFile() {
    }

    public UploadFile(String base64, String name, int size, String owner, int directoryId) {
        this.base64 = base64;
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.directoryId = directoryId;
    }
}

