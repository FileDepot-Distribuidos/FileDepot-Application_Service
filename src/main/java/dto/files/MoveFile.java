package dto.files;

public class MoveFile {
    public int fileID;
    public int newDirectoryID;

    public MoveFile() {}

    public MoveFile(int fileID, int newDirectoryID) {
        this.fileID = fileID;
        this.newDirectoryID = newDirectoryID;
    }
}
