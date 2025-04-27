package Service;

import controller.implementations.FileDepotImplementation;
import grpc.GrpcNodeManager;
import grpc.sync.NodeSyncService;
import jakarta.xml.ws.Endpoint;
import rmi.AuthService;
import util.ConfigLoader;

public class Service {
    public static void main(String[] args) {
        try {
            //RMI
            String rmiHost = ConfigLoader.get("RMI_HOST");
            int rmiPort = ConfigLoader.getInt("RMI_PORT");
            String rmiServiceName = ConfigLoader.get("RMI_SERVICE_NAME");
            AuthService.init(rmiHost, rmiPort, rmiServiceName);

            //gRPC
            GrpcNodeManager.verifyConnectionReady();
            NodeSyncService.startSync();

            //SOAP
            int soapPort = ConfigLoader.getInt("SOAP_PORT");
            String soapPath = ConfigLoader.get("SOAP_BASE_PATH");
            String soapHost = ConfigLoader.get("SOAP_HOST");
            String soapURL = "http://" + soapHost + ":" + soapPort + soapPath;

            Endpoint.publish(soapURL, new FileDepotImplementation());
            System.out.println(soapURL);

        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}