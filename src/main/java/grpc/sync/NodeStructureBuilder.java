package grpc.sync;

import filesystem.ListAllResponse;
import grpc.FileSystemClient;

public class NodeStructureBuilder {
    public static NodeStructure build(FileSystemClient client, String rootPath) {
        System.out.println("Iniciando construcción desde: " + rootPath);
        NodeStructure structure = new NodeStructure();
        traverse(client, rootPath, structure);
        System.out.println("Estructura finalizada para: " + rootPath);
        return structure;
    }

    private static void traverse(FileSystemClient client, String currentPath, NodeStructure structure) {
        try {
            System.out.println("Explorando ruta: " + currentPath);
            ListAllResponse response = client.listAll(currentPath);

            // Agregar el directorio actual
            if (!currentPath.equals("/")) {
                String normalizedPath = currentPath.startsWith("/") ? currentPath.substring(1) : currentPath;
                System.out.println("Directorio encontrado: " + normalizedPath);
                structure.addDirectory(normalizedPath);
            }

            // Agregar archivos encontrados
            if (response.getFilesList() != null) {
                for (String fileName : response.getFilesList()) {
                    String fullFilePath = currentPath.equals("/") ? fileName : currentPath + "/" + fileName;
                    if (fullFilePath.startsWith("/")) {
                        fullFilePath = fullFilePath.substring(1);
                    }
                    System.out.println("Archivo encontrado: " + fullFilePath);
                    structure.addFile(fullFilePath);
                }
            }

            // Recorrer subdirectorios encontrados
            if (response.getDirectoriesList() != null) {
                for (String directoryName : response.getDirectoriesList()) {
                    String fullDirPath = currentPath.equals("/") ? directoryName : currentPath + "/" + directoryName;
                    if (fullDirPath.startsWith("/")) {
                        fullDirPath = fullDirPath.substring(1);
                    }
                    traverse(client, fullDirPath, structure);
                }
            }

        } catch (Exception e) {
            System.err.println("Error recorriendo " + currentPath + ": " + e.getMessage());
        }
    }

}
