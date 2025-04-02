package Service;

import controller.implementations.FileDepotImplementation;
import grpc.GrpcNodeManager;
import jakarta.xml.ws.Endpoint;

public class SoapPublisher {
    public static void main(String[] args) {
        try {
            GrpcNodeManager.verifyConnectionReady();

            Endpoint.publish("http://localhost:2005/FileDepotService", new FileDepotImplementation());
            System.out.println("SOAP Service.Service iniciado en http://localhost:2005/FileDepotService?wsdl");

        } catch (IllegalStateException e) {
            System.err.println("Error al iniciar SOAP: " + e.getMessage());
            System.exit(1);
        }
    }
}
