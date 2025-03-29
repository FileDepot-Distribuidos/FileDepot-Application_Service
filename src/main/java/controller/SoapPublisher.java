package controller;

import jakarta.xml.ws.Endpoint;

public class SoapPublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8080/FileDepotService";
        Endpoint.publish(url, new ServiceImpl());
        System.out.println("SOAP Service publicado en: " + url);
    }
}
