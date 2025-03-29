package controller;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://filedepot.appserver/ws")
public interface FileDepotService {
    @WebMethod
    String ping();
}
