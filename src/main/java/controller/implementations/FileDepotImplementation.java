package controller.implementations;

import controller.FileDepotService;
import jakarta.jws.WebService;

@WebService(endpointInterface = "controller.FileDepotService")
public class FileDepotImplementation implements FileDepotService {

    private final DirectoryImplementation directoryImpl = new DirectoryImplementation();
    private final FileImplementation fileImpl = new FileImplementation();
    private final ShareImplementation shareImpl = new ShareImplementation();
    private final UserImplementation userImpl = new UserImplementation();

    @Override
    public String processDirectoryRequest(String action, String data) {
        return directoryImpl.processDirectoryRequest(action, data);
    }

    @Override
    public String processFileRequest(String action, String data) {
        return fileImpl.processFileRequest(action, data);
    }

    @Override
    public String processShareRequest(String action, String data) {
        return shareImpl.processShareRequest(action, data);
    }

    @Override
    public String processAuthRequest(String action, String data) {
        return userImpl.processAuthRequest(action, data);
    }
}
