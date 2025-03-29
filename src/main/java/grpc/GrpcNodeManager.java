package grpc;

import util.ConfigLoader;

public class GrpcNodeManager {
    public static FileSystemClient getAvailableNodeClient() {
        String host = ConfigLoader.get("NODE1_HOST");
        int port = Integer.parseInt(ConfigLoader.get("NODE1_PORT"));
        return new FileSystemClient(host, port);
    }
}
