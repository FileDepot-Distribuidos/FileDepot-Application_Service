package apirest;

import com.google.gson.Gson;

public class NodeApi {

    public static void registerNode(String ipv4, long total, long available) {
        try {
            NodePayload payload = new NodePayload(ipv4, total, available);
            String json = new Gson().toJson(payload);

            boolean ok = ApiClient.post("/node", json);
            if (ok) {
                System.out.println("Nodo registrado en la base de datos: " + ipv4);
            } else {
                System.err.println("Error al registrar el nodo en la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Excepción al registrar nodo: " + e.getMessage());
        }
    }

    static class NodePayload {
        String ipv4_address;
        long total_size;
        long available_size;

        public NodePayload(String ip, long total, long available) {
            this.ipv4_address = ip;
            this.total_size = total;
            this.available_size = available;
        }
    }
}
