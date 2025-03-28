package controller;

import jakarta.jws.WebService;

@WebService(endpointInterface = "controller.FileDepotService")
public class ServiceImpl implements FileDepotService {
    @Override
    public String ping() {
        return "Conexión exitosa con FileDepot SOAP Server";
    }
}