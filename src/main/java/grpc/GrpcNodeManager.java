package grpc;

import apirest.NodeApi;
import util.ConfigLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GrpcNodeManager {
    private static final List<FileSystemClient> clients = new ArrayList<>();
    private static final AtomicInteger roundRobinCounter = new AtomicInteger(0);

    static {
        String[] hosts = {
                ConfigLoader.get("NODE_1_HOST"),
//              ConfigLoader.get("NODE_2_HOST"),
//              ConfigLoader.get("NODE_3_HOST"),
//              ConfigLoader.get("NODE_4_HOST")
        };

        int port = Integer.parseInt(ConfigLoader.get("NODE_PORT"));

        for (String host : hosts) {
            if (host != null && !host.isEmpty()) {
                clients.add(new FileSystemClient(host, port));

                // Registrar nodo automáticamente en base de datos
                System.out.println("📡 Registrando nodo en microservicio DB: " + host);
                NodeApi.registerNode(host, 1000000, 1000000); // Puedes reemplazar valores simulados
            }
        }

        if (clients.isEmpty()) {
            throw new IllegalStateException("No se pudo establecer conexión con ningún nodo.");
        }

        System.out.println("Total de nodos gRPC conectados: " + clients.size());
    }

    public static FileSystemClient getAvailableNodeClient() {
        int index = roundRobinCounter.getAndUpdate(i -> (i + 1) % clients.size());
        return clients.get(index);
    }

    public static void verifyConnectionReady() {
        if (clients.isEmpty()) {
            throw new IllegalStateException("No hay nodos gRPC disponibles.");
        }
    }
}
