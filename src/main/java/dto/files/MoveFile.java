package dto.files;

public class MoveFile {
    public String fileID;
    public String newDirectoryID;

    public MoveFile() {}

    public MoveFile(String fileID, String newDirectoryID) {
        this.fileID = fileID;
        this.newDirectoryID = newDirectoryID;
    }
}
