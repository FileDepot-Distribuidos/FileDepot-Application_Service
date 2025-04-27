package dto;

import java.util.List;
import dto.files.DbFile;
import dto.directory.DbDirectory;

public class StructureResponse {
    private List<DbDirectory> directories;
    private List<DbFile> files;

    public List<DbDirectory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<DbDirectory> directories) {
        this.directories = directories;
    }

    public List<DbFile> getFiles() {
        return files;
    }

    public void setFiles(List<DbFile> files) {
        this.files = files;
    }
}
