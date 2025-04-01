package controller.implementations;

import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.user.UserLogin;
import dto.user.UserRegister;
import jakarta.jws.WebService;

@WebService(endpointInterface = "controller.FileDepotService")
public class UserImplementation implements FileDepotService {

    private final Gson gson = new Gson();

    @Override
    public String processAuthRequest(String action, String data) {
        try {
            switch (action) {
                case "login" -> {
                    UserLogin login = gson.fromJson(data, UserLogin.class);

                    if ("ex@ex.com".equals(login.email) && "password123".equals(login.password)) {
                        return gson.toJson(new SoapResponse(true, gson.toJson(login)));
                    } else {
                        return gson.toJson(new SoapResponse(false, "Credenciales inválidas"));
                    }
                }

                case "register" -> {
                    UserRegister register = gson.fromJson(data, UserRegister.class);

                    if (!register.email.isEmpty() && !register.password.isEmpty() && !register.phone.isEmpty()) {
                        return gson.toJson(new SoapResponse(true, "Usuario registrado exitosamente"));
                    } else {
                        return gson.toJson(new SoapResponse(false, "Datos incompletos"));
                    }
                }

                default -> {
                    return gson.toJson(new SoapResponse(false, "Acción no soportada"));
                }
            }
        } catch (Exception e) {
            return gson.toJson(new SoapResponse(false, "Error en el servidor: " + e.getMessage()));
        }
    }

    @Override public String processFileRequest(String action, String data) { return notImplemented(); }
    @Override public String processDirectoryRequest(String action, String data) { return notImplemented(); }
    @Override public String processShareRequest(String action, String data) { return notImplemented(); }

    private String notImplemented() {
        return gson.toJson(new SoapResponse(false, "Método no implementado en este controlador."));
    }

}
