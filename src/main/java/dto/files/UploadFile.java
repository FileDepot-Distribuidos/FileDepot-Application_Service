package dto.files;

public class UploadFile {
    public String base64;
    public String name;
    public int size;
    public String owner;

    public UploadFile() {
    }

    public UploadFile(String base64, String name, int size, String owner) {
        this.base64 = base64;
        this.name = name;
        this.size = size;
        this.owner = owner;
    }
}

