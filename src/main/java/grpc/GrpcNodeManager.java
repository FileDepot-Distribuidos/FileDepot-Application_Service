package grpc;

import apirest.NodeApi;
import util.ConfigLoader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GrpcNodeManager {
    private static final List<FileSystemClient> clients = new ArrayList<>();
    private static final Map<FileSystemClient, Integer> clientIds = new HashMap<>();
    private static final Map<String, FileSystemClient> clientMap = new HashMap<>();
    private static final AtomicInteger roundRobinCounter = new AtomicInteger(0);

    static {
        String[] hosts = {
                ConfigLoader.get("NODE_1_HOST"),
                ConfigLoader.get("NODE_2_HOST"),
                ConfigLoader.get("NODE_3_HOST"),
                ConfigLoader.get("NODE_4_HOST")
        };

        int port = Integer.parseInt(ConfigLoader.get("NODE_PORT"));
        int idCounter = 1;

        for (String host : hosts) {
            if (!host.isEmpty()) {
                FileSystemClient client = new FileSystemClient(host, port);
                try {
                    client.listFiles("/");
                    clients.add(client);
                    clientIds.put(client, idCounter);
                    clientMap.put(host + ":" + port, client);

                    System.out.println("Nodo gRPC activo y agregado: " + host + ":" + port + " con ID: " + idCounter);

                    NodeApi.registerNode(host, 1000000000, 1000000000);
                    idCounter++;
                } catch (Exception e) {
                    System.err.println("Error al conectar con nodo gRPC " + host + ":" + port + " -> " + e.getMessage());
                }
            }
        }

        if (clients.isEmpty()) {
            throw new IllegalStateException("No se pudo establecer conexión con ningún nodo.");
        }

        System.out.println("Total de nodos gRPC conectados: " + clients.size());
    }

    public static List<FileSystemClient> getAllClients() {
        return clients;
    }

    public static FileSystemClient getClientByHost(String host) {
        for (FileSystemClient client : clients) {
            if (client.getHost().equals(host)) {
                return client;
            }
        }
        throw new RuntimeException("No se encontró cliente gRPC para el host: " + host);
    }

    public static FileSystemClient getClientByIpAndPort(String ip, int port) {
        String key = ip + ":" + port;
        FileSystemClient client = clientMap.get(key);
        if (client == null) {
            throw new RuntimeException("No se encontró cliente gRPC para: " + key);
        }
        return client;
    }

    public static int getNodeId(FileSystemClient client) {
        return clientIds.getOrDefault(client, -1);
    }

    public static void verifyConnectionReady() {
        if (clients.isEmpty()) {
            throw new IllegalStateException("No hay nodos gRPC disponibles.");
        }
    }
}
