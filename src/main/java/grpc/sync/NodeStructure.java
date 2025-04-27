package grpc.sync;

import java.util.ArrayList;
import java.util.List;

public class NodeStructure {
    private List<String> directories;
    private List<String> files;

    public NodeStructure() {
        this.directories = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    public List<String> getDirectories() {
        return directories;
    }

    public List<String> getFiles() {
        return files;
    }

    public void addDirectory(String path) {
        directories.add(path);
    }

    public void addFile(String path) {
        files.add(path);
    }
}
