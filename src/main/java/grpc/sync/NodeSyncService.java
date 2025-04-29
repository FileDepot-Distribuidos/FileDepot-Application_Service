package grpc.sync;

import apirest.ApiClient;
import dto.StructureResponse;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NodeSyncService {
    public static void synchronizeNodesWithDatabase() {
        StructureResponse dbStructure = ApiClient.getStructure();

        int nodosSincronizados = 0;
        int limiteNodos = 4;

        for (FileSystemClient nodeClient : GrpcNodeManager.getAllClients()) {
            if (nodosSincronizados >= limiteNodos) {
                System.out.println("Nodos sincronizados (" + limiteNodos + ").");
                break;
            }

            try {
                if (!nodeClient.isAlive()) {
                    nodosSincronizados++;
                    continue;
                }

                System.out.println("Sincronizando nodos");
                NodeStructure nodeStructure = NodeStructureBuilder.build(nodeClient, "/");
                NodeComparator.compareAndSynchronize(nodeClient, nodeStructure, dbStructure);

                System.out.println("Nodo sincronizado");
                nodosSincronizados++;

            } catch (Exception e) {
                System.err.println("Error al sincronizar nodo: " + e.getMessage());
                nodosSincronizados++;
            }
        }

    }

    public static void synchronizeFromOtherNode(FileSystemClient client, StructureResponse dbStructure, String userRootDirectory) {
        try {
            System.out.println("Eliminando carpeta del usuario: " + userRootDirectory);

            client.deleteFile(userRootDirectory);

            FileSystemClient referenceNode = null;
            for (FileSystemClient otherNode : GrpcNodeManager.getAllClients()) {
                if (otherNode != client && otherNode.isAlive()) {
                    referenceNode = otherNode;
                    break;
                }
            }

            if (referenceNode == null) {
                System.err.println("No hay nodos vivos para copiar estructura.");
                return;
            }

            System.out.println("Nodo de referencia encontrado.");

            NodeStructure referenceStructure = NodeStructureBuilder.build(referenceNode, userRootDirectory);

            List<String> directories = new ArrayList<>(referenceStructure.getDirectories());
            directories.sort(Comparator.comparingInt(NodeSyncService::countSlashes));

            for (String dir : directories) {
                if (!dir.isBlank()) {
                    client.createDirectory(dir);
                }
            }

            for (String filePath : referenceStructure.getFiles()) {
                try {
                    String directory = getDirectoryFromPath(filePath);
                    String filename = getFilenameFromPath(filePath);

                    var fileData = referenceNode.downloadFile(filePath);
                    if (fileData != null && fileData.getContentBase64() != null) {
                        client.uploadBase64File(filename, fileData.getContentBase64(), directory);
                    }
                } catch (Exception e) {
                    System.err.println("Error copiando archivo " + filePath + ": " + e.getMessage());
                }
            }

            System.out.println("Nodo clonado exitosamente.");

        } catch (Exception e) {
            System.err.println("Error durante sincronización completa: " + e.getMessage());
        }
    }



    public static void startSync() {
        int initialDelay = 10000;
        int interval = 10000;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                synchronizeNodesWithDatabase();
            } catch (Exception e) {
                System.err.println("Error en sincronización programada: " + e.getMessage());
            }
        }, initialDelay, interval, TimeUnit.MINUTES);
    }

    private static String getRootUserDirectory(StructureResponse dbStructure) {
        return dbStructure.getDirectories().stream()
                .filter(d -> d.getPath() != null && d.getPath().matches("\\d+/"))
                .map(d -> d.getPath().replace("/", ""))
                .findFirst()
                .orElse(null);
    }

    public static int countSlashes(String path) {
        return (int) path.chars().filter(ch -> ch == '/').count();
    }

    private static String getDirectoryFromPath(String path) {
        int idx = path.lastIndexOf("/");
        return (idx <= 0) ? "/" : path.substring(0, idx);
    }

    private static String getFilenameFromPath(String path) {
        int idx = path.lastIndexOf("/");
        return (idx == -1) ? path : path.substring(idx + 1);
    }

}
