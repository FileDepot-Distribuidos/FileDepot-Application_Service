package controller;

import jakarta.jws.WebService;
import org.json.JSONObject;

@WebService(endpointInterface = "controller.FileDepotService")
public class ServiceImpl implements FileDepotService {
    @Override
    public String ping() {
        return "Conexión exitosa con FileDepot SOAP Server";
    }

    @Override
    public String processAuthRequest(String action, String data) {
        try {
            JSONObject jsonData = new JSONObject(data);

            if ("login".equals(action)) {
                String email = jsonData.optString("email", "");
                String password = jsonData.optString("password", "");

                if ("ex@ex.com".equals(email) && "password123".equals(password)) {
                    return "{\"success\": true, \"userID\": 1, \"email\": \"" + email + "\"}";
                } else {
                    return "{\"success\": false, \"message\": \"Credenciales inválidas\"}";
                }
            } else if ("register".equals(action)) {
                String email = jsonData.optString("email", "");
                String password = jsonData.optString("password", "");
                String phone = jsonData.optString("phone", "");

                if (!email.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                    return "{\"success\": true, \"message\": \"Usuario registrado exitosamente\"}";
                } else {
                    return "{\"success\": false, \"message\": \"Datos incompletos\"}";
                }
            }
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"Error en el servidor\"}";
        }

        return "{\"success\": false, \"message\": \"Acción no soportada\"}";
    }
}
