package controller.implementations;

import com.google.gson.Gson;
import controller.FileDepotService;
import dto.SoapResponse;
import dto.user.UserLogin;
import dto.user.UserRegister;
import jakarta.jws.WebService;
import rmi.AuthService;

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
                    System.out.println("→ Registro con email: " + register.email + ", phone: " + register.phone);

                    int userId = AuthService.getService().register(
                            "a", register.email, register.password, register.phone);

                    boolean success = userId > 0;
                    System.out.println("  Resultado registro: " + success + " (ID: " + userId + ")");

                    if (success) {
                        boolean dirCreatedDb = apirest.DirectoryApi.createRootDirectory(String.valueOf(userId), userId);
                        boolean dirCreatedNode = false;

                        try {
                            grpc.FileSystemClient client = grpc.GrpcNodeManager.getAvailableNodeClient();
                            String result = client.createDirectory(userId + "/");
                            dirCreatedNode = result.toLowerCase().contains("correctamente");
                        } catch (Exception e) {
                            System.err.println("Error creando directorio en nodo: " + e.getMessage());
                        }

                        if (!dirCreatedDb || !dirCreatedNode) {
                            System.out.println("Usuario registrado, pero hubo problema al crear el directorio.");
                        } else {
                            System.out.println("Directorio creado para user_id: " + userId);
                        }

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
