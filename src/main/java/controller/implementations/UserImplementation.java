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

                    boolean valid = "ex@ex.com".equals(login.email) && "password123".equals(login.password);
                    SoapResponse response;

                    if (valid) {
                        response = new SoapResponse(true, gson.toJson(login));
                    } else {
                        response = new SoapResponse(false, "Credenciales inválidas");
                    }

                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                case "register" -> {
                    UserRegister register = gson.fromJson(data, UserRegister.class);

                    boolean valid = !register.email.isEmpty() && !register.password.isEmpty() && !register.phone.isEmpty();
                    SoapResponse response;

                    if (valid) {
                        response = new SoapResponse(true, "Usuario registrado exitosamente");
                    } else {
                        response = new SoapResponse(false, "Datos incompletos");
                    }

                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }

                default -> {
                    SoapResponse response = new SoapResponse(false, "Acción no soportada");
                    String json = gson.toJson(response);
                    System.out.println("Respuesta enviada al backend cliente: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            SoapResponse response = new SoapResponse(false, "Error en el servidor: " + e.getMessage());
            String json = gson.toJson(response);
            System.out.println("Respuesta enviada al backend cliente: " + json);
            return json;
        }
    }

    @Override public String processFileRequest(String action, String data) { return notImplemented(); }
    @Override public String processDirectoryRequest(String action, String data) { return notImplemented(); }
    @Override public String processShareRequest(String action, String data) { return notImplemented(); }

    private String notImplemented() {
        SoapResponse response = new SoapResponse(false, "Método no implementado en este controlador.");
        String json = gson.toJson(response);
        System.out.println("Respuesta enviada al backend cliente: " + json);
        return json;
    }
}
