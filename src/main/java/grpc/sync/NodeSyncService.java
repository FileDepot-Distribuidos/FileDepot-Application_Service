package grpc.sync;

import apirest.ApiClient;
import dto.StructureResponse;
import dto.files.DbFile;
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
            int currentNodeId = GrpcNodeManager.getNodeId(client);
            List<DbFile> allFiles = dbStructure.getFiles();
            List<String> createdDirs = new ArrayList<>();
            List<String> expectedPaths = new ArrayList<>();

            createdDirs.add(userRootDirectory);

            // Restaura archivos válidos
            for (DbFile file : allFiles) {
                String fullPath = NodeComparator.buildFilePath(file, dbStructure.getDirectories());
                if (fullPath == null) continue;
                String normalizedPath = fullPath.replaceFirst("^/", "");

                if (file.getOriginal_idNODE() != currentNodeId && file.getCopy_idNODE() != currentNodeId) {
                    continue;
                }

                if (!normalizedPath.startsWith(userRootDirectory + "/") && !normalizedPath.equals(userRootDirectory)) {
                    continue;
                }

                expectedPaths.add(normalizedPath);

                String directory = getDirectoryFromPath(normalizedPath);
                if (!createdDirs.contains(directory)) {
                    client.createDirectory(directory);
                    createdDirs.add(directory);
                }

                FileSystemClient sourceNode = null;
                for (FileSystemClient other : GrpcNodeManager.getAllClients()) {
                    int nodeId = GrpcNodeManager.getNodeId(other);
                    if ((file.getOriginal_idNODE() == nodeId || file.getCopy_idNODE() == nodeId)
                            && other != client && other.isAlive()) {
                        sourceNode = other;
                        break;
                    }
                }

                if (sourceNode != null) {
                    try {
                        var data = sourceNode.downloadFile(normalizedPath);
                        if (data != null && data.getContentBase64() != null) {
                            String filename = getFilenameFromPath(normalizedPath);
                            client.uploadBase64File(filename, data.getContentBase64(), directory);
                        }
                    } catch (Exception e) {
                        System.err.println("Error copiando archivo " + normalizedPath + ": " + e.getMessage());
                    }
                } else {
                    System.err.println("No se encontró nodo fuente para archivo: " + normalizedPath);
                }
            }

            NodeStructure actualStructure = NodeStructureBuilder.build(client, userRootDirectory);
            for (String actualPath : actualStructure.getFiles()) {
                if (!expectedPaths.contains(actualPath)) {
                    System.out.println("[SYNC][LIMPIEZA] Eliminando archivo sobrante: " + actualPath);
                    client.deleteFile(actualPath);
                }
            }

            for (String actualDir : actualStructure.getDirectories()) {
                if (!createdDirs.contains(actualDir) && actualDir.startsWith(userRootDirectory)) {
                    System.out.println("[SYNC][LIMPIEZA] Eliminando directorio sobrante: " + actualDir);
                    client.deleteFile(actualDir);
                }
            }

            System.out.println("Sincronización puntual finalizada para usuario " + userRootDirectory);
        } catch (Exception e) {
            System.err.println("Error durante sincronización de archivos: " + e.getMessage());
        }
    }






    public static void startSync() {
        int initialDelay = 1;
        int interval = 1;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                synchronizeNodesWithDatabase();
            } catch (Exception e) {
                System.err.println("Error en sincronización programada: " + e.getMessage());
            }
        }, initialDelay, interval, TimeUnit.MINUTES);
    }

    public static List<String> getRootUserDirectories(StructureResponse dbStructure) {
        List<String> roots = new ArrayList<>();
        for (var dir : dbStructure.getDirectories()) {
            if (dir.getPath() != null) {
                String path = dir.getPath().replaceAll("^/|/$", "");
                if (path.matches("\\d+")) {
                    roots.add(path);
                }
            }
        }
        return roots;
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
