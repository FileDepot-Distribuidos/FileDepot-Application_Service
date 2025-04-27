package grpc.sync;

import dto.StructureResponse;
import dto.directory.DbDirectory;
import dto.files.DbFile;
import grpc.FileSystemClient;

import java.util.*;

public class NodeComparator {

    public static void compareAndSynchronize(FileSystemClient client, NodeStructure nodeStructure, StructureResponse dbStructure) {
        System.out.println("\n--- Comparando y sincronizando nodo ---");

        if (isNodeTooDifferent(nodeStructure, dbStructure)) {
            System.out.println("Nodo desactualizado, sincronizando...");
            NodeSyncService.synchronizeFromOtherNode(client, dbStructure);
            return;
        }

        Set<String> dbDirectories = buildDbDirectories(dbStructure);
        Set<String> dbFiles = buildDbFiles(dbStructure);

        createMissingDirectories(client, nodeStructure, dbStructure);
        uploadMissingFiles(client, nodeStructure, dbStructure);

        System.out.println("--- Nodo sincronizado ---");
    }


    private static boolean isNodeTooDifferent(NodeStructure nodeStructure, StructureResponse dbStructure) {
        int archivosNodo = nodeStructure.getFiles().size();
        int archivosDb = dbStructure.getFiles().size();
        int carpetasNodo = nodeStructure.getDirectories().size();
        int carpetasDb = dbStructure.getDirectories().size();

        int diferenciaArchivos = Math.abs(archivosNodo - archivosDb);
        int diferenciaDirectorios = Math.abs(carpetasNodo - carpetasDb);

        double porcentajeArchivos = (double) diferenciaArchivos / Math.max(archivosNodo, archivosDb);
        double porcentajeDirectorios = (double) diferenciaDirectorios / Math.max(carpetasNodo, carpetasDb);

        return porcentajeArchivos > 0.2 || porcentajeDirectorios > 0.2; // Si hay más del 20% de diferencia
    }


    private static void createMissingDirectories(FileSystemClient client, NodeStructure nodeStructure, StructureResponse dbStructure) {
        System.out.println("Creando carpetas faltantes...");
        Set<String> dbDirectories = buildDbDirectories(dbStructure);

        for (String dir : dbDirectories) {
            if (!nodeStructure.getDirectories().contains(dir)) {
                System.out.println("Creando carpeta: " + dir);
                try {
                    client.createDirectory("/" + dir);
                } catch (Exception e) {
                    System.err.println("Error creando carpeta " + dir + ": " + e.getMessage());
                }
            }
        }
    }

    private static void uploadMissingFiles(FileSystemClient client, NodeStructure nodeStructure, StructureResponse dbStructure) {
        System.out.println("Subiendo archivos faltantes...");
        Set<String> dbFiles = buildDbFiles(dbStructure);

        for (String file : dbFiles) {
            if (!nodeStructure.getFiles().contains(file)) {
                System.out.println("Archivo faltante, intentando recuperar: " + file);
            }
        }
    }



    private static Set<String> buildDbDirectories(StructureResponse dbStructure) {
        Set<String> dirs = new HashSet<>();
        for (DbDirectory dir : dbStructure.getDirectories()) {
            if (dir != null && dir.getPath() != null && !dir.getPath().isBlank()) {
                String clean = dir.getPath();
                if (clean.endsWith("/")) clean = clean.substring(0, clean.length() - 1);
                dirs.add(clean);
            }
        }
        return dirs;
    }

    private static Set<String> buildDbFiles(StructureResponse dbStructure) {
        Set<String> files = new HashSet<>();
        for (DbFile file : dbStructure.getFiles()) {
            if (file != null && file.getName() != null) {
                String path = buildFilePath(file, dbStructure.getDirectories());
                if (path != null) files.add(path);
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
        if (dirPath.endsWith("/")) dirPath = dirPath.substring(0, dirPath.length() - 1);

        return dirPath + "/" + file.getName();
    }
}
