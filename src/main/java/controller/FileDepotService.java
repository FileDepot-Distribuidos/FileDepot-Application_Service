package controller;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;

@WebService(targetNamespace = "http://filedepot.appserver/ws")
public interface FileDepotService {

    @WebMethod
    String ping();

    @WebMethod
    String processAuthRequest(
            @WebParam(name = "action") String action,
            @WebParam(name = "data") String data
    );

    @WebMethod
    String processFileRequest(
            @WebParam(name = "action") String action,
            @WebParam(name = "data") String data
    );

    @WebMethod
    String processDirectoryRequest(
            @WebParam(name = "action") String action,
            @WebParam(name = "data") String data
    );

    @WebMethod
    String processShareRequest(
            @WebParam(name = "action") String action,
            @WebParam(name = "data") String data
    );
}
