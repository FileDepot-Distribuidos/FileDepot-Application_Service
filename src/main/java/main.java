import grpc.FileSystemClient;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

public class main {
    public static void main(String[] args) {
        FileSystemClient client = new FileSystemClient("127.0.0.1", 50051); // Nodo gRPC

        //move file/directory
//        String source = "mis_archivos/nueva_carpeta/documento.jpg";
//        String destination = "mis_archivos/documento.jpg";
//
//        String resultado = client.moveFile(source, destination);
//        System.out.println("Resultado: " + resultado);

        //Listfiles
//        String directory = "mis_archivos"; // Ruta relativa dentro de 'storage/'
//
//        List<String> archivos = client.listFiles(directory);
//
//        System.out.println("Contenido de '" + directory + "':");
//        archivos.forEach(System.out::println);

        // Eliminar directorio/subdirectorio
//        String path = "mis_archivos/nueva_carpeta/subcarpeta";
//
//        String resultado = client.deleteFile(path);
//        System.out.println("Resultado: " + resultado);

        // Renombrar directorio/subdirectorio
//        String oldDir = "mis_archivos/nuevo_directorio";
//        String newDir = "mis_archivos/nueva_carpeta";
//
//
//        String resultado = client.renameFile(oldDir, newDir);
//        System.out.println("Resultado: " + resultado);

        // Crear subdirectorio
//        String parent = "mis_archivos/nuevo_directorio";
//        String subdir = "subcarpeta";
//
//        String resultado = client.createSubdirectory(parent, subdir);
//        System.out.println("Resultado: " + resultado);

        // Crear directorio
//        String path = "mis_archivos/nuevo_directorio";
//
//        String resultado = client.createDirectory(path);
//        System.out.println("Resultado: " + resultado);


        //Eliminar archivos
//        String path = "mis_archivos/documento_renombrado.docx";
//
//        String resultado = client.deleteFile(path);
//        System.out.println("Resultado: " + resultado);


        // Modificar nombre de archivos
//        String oldName = "mis_archivos/documento.docx";
//        String newName = "mis_archivos/documento_renombrado.docx";
//
//        String resultado = client.renameFile(oldName, newName);
//        System.out.println("Resultado: " + resultado);



        // Convertir archivos base64 y mandar a los nodos
//        try {
//            // Ruta del archivo real a subir (por ejemplo, un .docx o .jpg)
//            Path path = Paths.get("src/main/java/propuesta-proyecto.docx");
//
//            // Leer archivo como bytes y codificar en base64
//            byte[] fileBytes = Files.readAllBytes(path);
//            String base64 = Base64.getEncoder().encodeToString(fileBytes);
//
//            String filename = "documento.docx";
//            String directory = "mis_archivos";
//
//            // Enviar por gRPC
//            String resultado = client.uploadBase64File(filename, base64, directory);
//            System.out.println("Resultado: " + resultado);
//
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
    }
}
