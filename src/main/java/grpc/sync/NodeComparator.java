package grpc.sync;

import dto.StructureResponse;
import dto.directory.DbDirectory;
import dto.files.DbFile;
import grpc.FileSystemClient;

import java.util.*;

public class NodeComparator {

    public static void compareAndSynchronize(FileSystemClient client, NodeStructure nodeStructure, StructureResponse dbStructure) {
        System.out.println("\n--- Comparando y sincronizando nodo ---");

        List<String> dbDirectories = buildDbDirectories(dbStructure);
        List<String> dbFiles = buildDbFiles(dbStructure);

        List<String> userRoots = getUserRoots(dbStructure);

        for (String userRoot : userRoots) {
            System.out.println("Sincronizando usuario: " + userRoot);

            List<String> userDbDirectories = filterPathsByRoot(dbDirectories, userRoot);
            List<String> userDbFiles = filterPathsByRoot(dbFiles, userRoot);
            List<String> userNodeDirectories = filterPathsByRoot(nodeStructure.getDirectories(), userRoot);
            List<String> userNodeFiles = filterPathsByRoot(nodeStructure.getFiles(), userRoot);

            System.out.println("Directorios en DB para usuario " + userRoot + ":");
            for (String dir : dbDirectories) {
                if (dir.equals(userRoot) || dir.startsWith(userRoot + "/")) {
                    System.out.println("DB " + dir);
                }
            }

            System.out.println("Directorios en Nodo para usuario " + userRoot + ":");
            for (String dir : nodeStructure.getDirectories()) {
                if (dir.equals(userRoot) || dir.startsWith(userRoot + "/")) {
                    System.out.println("Nodo " + dir);
                }
            }

            System.out.println("Archivos en DB para usuario " + userRoot + ":");
            for (String file : dbFiles) {
                if (file.equals(userRoot) || file.startsWith(userRoot + "/")) {
                    System.out.println("DB File " + file);
                }
            }

            System.out.println("Archivos en Nodo para usuario " + userRoot + ":");
            for (String file : nodeStructure.getFiles()) {
                if (file.equals(userRoot) || file.startsWith(userRoot + "/")) {
                    System.out.println("Nodo File " + file);
                }
            }

            boolean outOfSync = false;

            for (String expectedDir : dbDirectories) {
                if (expectedDir.equals(userRoot) || expectedDir.startsWith(userRoot + "/")) {
                    if (!nodeStructure.getDirectories().contains(expectedDir)) {
                        System.out.println("Directorio faltante en nodo: " + expectedDir);
                        outOfSync = true;
                        break;
                    }
                }
            }

            if (!outOfSync) {
                for (String expectedFile : dbFiles) {
                    if (expectedFile.equals(userRoot) || expectedFile.startsWith(userRoot + "/")) {
                        if (!nodeStructure.getFiles().contains(expectedFile)) {
                            System.out.println("Archivo faltante en nodo: " + expectedFile);
                            outOfSync = true;
                            break;
                        }
                    }
                }
            }

            if (!outOfSync) {
                for (String actualDir : userNodeDirectories) {
                    if (!userDbDirectories.contains(actualDir)) {
                        System.out.println("[SYNC][ERROR] Directorio extra no registrado: " + actualDir);
                        outOfSync = true;
                        break;
                    }
                }
            }

            if (!outOfSync) {
                for (String actualFile : userNodeFiles) {
                    if (!userDbFiles.contains(actualFile)) {
                        System.out.println("[SYNC][ERROR] Archivo extra no registrado: " + actualFile);
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


    private static String buildFilePath(DbFile file, List<DbDirectory> dirs) {
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
