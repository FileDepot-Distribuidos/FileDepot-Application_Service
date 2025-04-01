package Service;

import controller.implementations.FileDepotImplementation;
import grpc.GrpcNodeManager;
import jakarta.xml.ws.Endpoint;
import rmi.AuthService;

public class Service {
    public static void main(String[] args) {
        try {
            AuthService.init("localhost", 4423, "AuthService");

            GrpcNodeManager.verifyConnectionReady();

            Endpoint.publish("http://localhost:2005/FileDepotService", new FileDepotImplementation());
            System.out.println("Servicio SOAP iniciado en http://localhost:2005/FileDepotService?wsdl");

        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
