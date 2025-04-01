import grpc.FileSystemClient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    FileSystemClient client = new FileSystemClient("127.0.0.1", 50051);

    while (true) {
        System.out.println("\n=== MENÚ DE OPCIONES ===");
        System.out.println("1. Subir archivo");
        System.out.println("2. Crear directorio");
        System.out.println("3. Crear subdirectorio");
        System.out.println("4. Renombrar archivo/directorio");
        System.out.println("5. Eliminar archivo/directorio");
        System.out.println("6. Mover archivo/directorio");
        System.out.println("7. Listar archivos en directorio");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                try {
                    System.out.print("Ruta del archivo local: ");
                    String ruta = scanner.nextLine();
                    System.out.print("Nombre del archivo al subir: ");
                    String filename = scanner.nextLine();
                    System.out.print("Directorio destino: ");
                    String dir = scanner.nextLine();

                    byte[] fileBytes = Files.readAllBytes(Paths.get(ruta));
                    String base64 = Base64.getEncoder().encodeToString(fileBytes);

                    String resultado = client.uploadBase64File(filename, base64, dir);
                    System.out.println("Resultado: " + resultado);
                } catch (Exception e) {
                    System.err.println("Error subiendo archivo: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Ruta del nuevo directorio: ");
                String path = scanner.nextLine();
                String resultado = client.createDirectory(path);
                System.out.println("Resultado: " + resultado);
            }
            case 3 -> {
                System.out.print("Directorio padre: ");
                String parent = scanner.nextLine();
                System.out.print("Nombre del subdirectorio: ");
                String subdir = scanner.nextLine();
                String resultado = client.createSubdirectory(parent, subdir);
                System.out.println("Resultado: " + resultado);
            }
            case 4 -> {
                System.out.print("Nombre actual: ");
                String oldName = scanner.nextLine();
                System.out.print("Nuevo nombre: ");
                String newName = scanner.nextLine();
                String resultado = client.renameFile(oldName, newName);
                System.out.println("Resultado: " + resultado);
            }
            case 5 -> {
                System.out.print("Ruta del archivo/directorio a eliminar: ");
                String path = scanner.nextLine();
                String resultado = client.deleteFile(path);
                System.out.println("Resultado: " + resultado);
            }
            case 6 -> {
                System.out.print("Ruta origen: ");
                String origen = scanner.nextLine();
                System.out.print("Ruta destino: ");
                String destino = scanner.nextLine();
                String resultado = client.moveFile(origen, destino);
                System.out.println("Resultado: " + resultado);
            }
            case 7 -> {
                System.out.print("Ruta del directorio: ");
                String dir = scanner.nextLine();
                List<String> archivos = client.listFiles(dir);
                System.out.println("Archivos:");
                archivos.forEach(System.out::println);
            }
            case 0 -> {
                System.out.println("Saliendo...");
                return;
            }
            default -> System.out.println("Opción inválida.");
        }
    }
}
