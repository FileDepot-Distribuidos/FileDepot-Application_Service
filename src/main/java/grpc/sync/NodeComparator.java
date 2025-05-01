package grpc.sync;

import dto.StructureResponse;
import dto.directory.DbDirectory;
import dto.files.DbFile;
import grpc.FileSystemClient;
import grpc.GrpcNodeManager;

import java.util.*;

public class NodeComparator {

    public static void compareAndSynchronize(FileSystemClient client, NodeStructure nodeStructure, StructureResponse dbStructure) {
        System.out.println("\n--- Comparando y sincronizando nodo ---");

        int currentNodeId = GrpcNodeManager.getNodeId(client);

        List<String> dbDirectories = buildDbDirectories(dbStructure);
        List<DbFile> allDbFiles = dbStructure.getFiles();

        List<String> userRoots = getUserRoots(dbStructure);

        for (String userRoot : userRoots) {
            System.out.println("Sincronizando usuario: " + userRoot);

            List<String> userDbDirectories = filterPathsByRoot(dbDirectories, userRoot);
            List<String> userNodeDirectories = filterPathsByRoot(nodeStructure.getDirectories(), userRoot);
            List<String> userNodeFiles = filterPathsByRoot(nodeStructure.getFiles(), userRoot);

            // Filtrar archivos de este usuario y que deben estar en este nodo
            List<DbFile> userDbFiles = new ArrayList<>();
            for (DbFile file : allDbFiles) {
                String filePath = buildFilePath(file, dbStructure.getDirectories());
                if ((filePath.equals(userRoot) || filePath.startsWith(userRoot + "/"))
                        && fileInNode(file, currentNodeId)) {
                    userDbFiles.add(file);
                }
            }

            // Logging
            System.out.println("Directorios esperados en DB:");
            userDbDirectories.forEach(d -> System.out.println("DB Dir: " + d));

            System.out.println("Directorios en Nodo:");
            userNodeDirectories.forEach(d -> System.out.println("Nodo Dir: " + d));

            System.out.println("Archivos esperados en DB:");
            for (DbFile file : userDbFiles) {
                System.out.println("DB File: " + buildFilePath(file, dbStructure.getDirectories()));
            }

            System.out.println("Archivos en Nodo:");
            userNodeFiles.forEach(f -> System.out.println("Nodo File: " + f));

            boolean outOfSync = false;

            // Verificar directorios faltantes
            for (String expectedDir : userDbDirectories) {
                if (!userNodeDirectories.contains(expectedDir)) {
                    System.out.println("Directorio faltante en nodo: " + expectedDir);
                    outOfSync = true;
                    break;
                }
            }

            // Verificar archivos faltantes
            if (!outOfSync) {
                for (DbFile file : userDbFiles) {
                    String expectedPath = normalizePath(buildFilePath(file, dbStructure.getDirectories()));
                    if (!nodeStructure.getFiles().contains(expectedPath)) {
                        System.out.println("Archivo faltante en nodo: " + expectedPath);
                        outOfSync = true;
                        break;
                    }
                }
            }

            // Verificar directorios sobrantes
            if (!outOfSync) {
                for (String actualDir : userNodeDirectories) {
                    if (!userDbDirectories.contains(actualDir)) {
                        System.out.println("[SYNC][ERROR] Directorio extra no registrado: " + actualDir);
                        outOfSync = true;
                        break;
                    }
                }
            }

            // Verificar archivos sobrantes
            if (!outOfSync) {
                Set<String> expectedPaths = new HashSet<>();
                for (DbFile f : userDbFiles) {
                    expectedPaths.add(normalizePath(buildFilePath(f, dbStructure.getDirectories())));
                }

                for (String actualFile : userNodeFiles) {
                    if (!expectedPaths.contains(actualFile)) {
                        System.out.println("[SYNC][ERROR] Archivo extra no asignado a este nodo: " + actualFile);
                        outOfSync = true;
                        break;
                    }
                }
            }

            if (outOfSync) {
                System.out.println("Usuario " + userRoot + " desincronizado, iniciando clonación...");
                NodeSyncService.synchronizeFromOtherNode(client, dbStructure, userRoot);
            } else {
                System.out.println("Usuario " + userRoot + " completamente sincronizado.");
            }
        }

        System.out.println("--- Nodo sincronizado ---");
    }



    private static List<String> buildDbDirectories(StructureResponse dbStructure) {
        List<String> dirs = new ArrayList<>();
        for (DbDirectory dir : dbStructure.getDirectories()) {
            if (dir != null && dir.getPath() != null && !dir.getPath().isBlank()) {
                String clean = normalizePath(dir.getPath());
                dirs.add(clean);
            }
        }
        return dirs;
    }

    private static boolean fileInNode(DbFile file, int currentNodeId) {
        return file.getOriginal_idNODE() == currentNodeId || file.getCopy_idNODE() == currentNodeId;
    }



    private static List<String> buildDbFiles(StructureResponse dbStructure) {
        List<String> files = new ArrayList<>();
        for (DbFile file : dbStructure.getFiles()) {
            if (file != null && file.getName() != null) {
                String path = buildFilePath(file, dbStructure.getDirectories());
                if (path != null) {
                    path = normalizePath(path);
                    files.add(path);
                }
            }
        }
        return files;
    }


    public static String buildFilePath(DbFile file, List<DbDirectory> dirs) {
        DbDirectory parent = dirs.stream()
                .filter(d -> d.getIdDIRECTORY() == file.getDIRECTORY_idDIRECTORY())
                .findFirst()
                .orElse(null);

        String dirPath = (parent != null) ? parent.getPath() : "/";
        dirPath = normalizePath(dirPath);

        return dirPath + "/" + file.getName();
    }

    private static List<String> getUserRoots(StructureResponse dbStructure) {
        List<String> roots = new ArrayList<>();
        for (DbDirectory dir : dbStructure.getDirectories()) {
            if (dir.getPath() != null) {
                String normalized = normalizePath(dir.getPath());
                if (normalized.matches("\\d+")) {
                    roots.add(normalized);
                }
            }
        }
        return roots;
    }


    private static List<String> filterPathsByRoot(List<String> paths, String root) {
        List<String> filtered = new ArrayList<>();
        for (String path : paths) {
            if (path.equals(root) || path.startsWith(root + "/")) {
                filtered.add(path);
            }
        }
        return filtered;
    }


    private static String normalizePath(String path) {
        if (path == null) return "";
        if (path.startsWith("/")) path = path.substring(1);
        if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        return path;
    }

}
