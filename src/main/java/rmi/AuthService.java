package rmi;

import java.rmi.Naming;
import com.FileDepot.Interfaces.AuthInterface;

public class AuthService {
    private static AuthInterface authService;

    public static void init(String host, int port, String serviceName) throws Exception {
        String url = String.format("rmi://%s:%d/%s", host, port, serviceName);
        authService = (AuthInterface) Naming.lookup(url);
        System.out.println("Conexión RMI exitosa con " + url);
    }

    public static AuthInterface getService() {
        if (authService == null) {
            throw new IllegalStateException("Servicio RMI no inicializado.");
        }
        return authService;
    }
}
