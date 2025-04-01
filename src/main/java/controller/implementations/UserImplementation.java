package controller.implementations;

import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.user.UserLogin;
import dto.user.UserRegister;
import jakarta.jws.WebService;
import rmi.AuthService;

import java.rmi.RemoteException;

@WebService(endpointInterface = "controller.FileDepotService")
public class UserImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processAuthRequest(String action, String data) {
        System.out.println("→ Solicitud SOAP recibida en processAuthRequest");
        System.out.println("  Acción: " + action);
        System.out.println("  Datos: " + data);

        try {
            switch (action) {
                case "login" -> {
                    UserLogin login = gson.fromJson(data, UserLogin.class);
                    System.out.println("  → Login con email: " + login.email);

                    boolean success = AuthService.getService().login(login.email, login.password);
                    System.out.println("  Resultado login: " + success);

                    if (success) {
                        return gson.toJson(new SoapResponse(true, "Login exitoso"));
                    } else {
                        return gson.toJson(new SoapResponse(false, "Credenciales inválidas"));
                    }
                }

                case "register" -> {
                    UserRegister register = gson.fromJson(data, UserRegister.class);
                    System.out.println("  → Registro con email: " + register.email + ", phone: " + register.phone);

                    boolean created = AuthService.getService().register(
                            "a", register.email, register.password, register.phone);

                    System.out.println("  Resultado registro: " + created);

                    if (created) {
                        return gson.toJson(new SoapResponse(true, "Usuario registrado correctamente"));
                    } else {
                        return gson.toJson(new SoapResponse(false, "No se pudo registrar el usuario"));
                    }
                }

                default -> {
                    System.out.println("  Acción no soportada");
                    return gson.toJson(new SoapResponse(false, "Acción no soportada"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando auth: " + e.getMessage());
            e.printStackTrace();
            return gson.toJson(new SoapResponse(false, "Error al procesar solicitud de autenticación: " + e.getMessage()));
        }
    }

    @Override public String processFileRequest(String action, String data) { return notImplemented(); }
    @Override public String processDirectoryRequest(String action, String data) { return notImplemented(); }
    @Override public String processShareRequest(String action, String data) { return notImplemented(); }

    private String notImplemented() {
        return gson.toJson(new SoapResponse(false, "Método no implementado en este controlador."));
    }
}
