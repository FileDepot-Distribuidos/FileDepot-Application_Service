package controller;

import jakarta.xml.ws.Endpoint;

public class SoapPublisher {
    public static void main(String[] args) {

        // User (login, register)
        Endpoint.publish("http://localhost:2005/UserService", new UserImplementation());

        // Files (upload, delete, move, read, download)
        Endpoint.publish("http://localhost:2006/FileService", new FileImplementation());

        // Directories (crear, renombrar, mover, listar, eliminar)
        Endpoint.publish("http://localhost:2007/DirectoryService", new DirectoryImplementation());

        // Shares (shareFile, shareDirectory, revoke, list)
        Endpoint.publish("http://localhost:2008/ShareService", new ShareImplementation());

        System.out.println("Servicios SOAP publicados:");
        System.out.println(" → UserService:       http://localhost:2005/UserService?wsdl");
        System.out.println(" → FileService:       http://localhost:2006/FileService?wsdl");
        System.out.println(" → DirectoryService:  http://localhost:2007/DirectoryService?wsdl");
        System.out.println(" → ShareService:      http://localhost:2008/ShareService?wsdl");
    }
}
