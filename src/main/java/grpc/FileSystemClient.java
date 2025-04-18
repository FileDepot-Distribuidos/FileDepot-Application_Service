package grpc;

import com.google.protobuf.ByteString;
import dto.files.UploadResult;
import filesystem.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class FileSystemClient {

    private final FileSystemServiceGrpc.FileSystemServiceBlockingStub stub;

    public FileSystemClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .maxInboundMessageSize(20 * 1024 * 1024) // 20MB, puedes ajustar
                .maxInboundMetadataSize(10 * 1024 * 1024) // opcional, por si usas metadatos grandes
                .build();

        stub = FileSystemServiceGrpc.newBlockingStub(channel);
        System.out.println("Conectado a nodo gRPC en " + host + ":" + port);
    }

    public UploadResult uploadBase64File(String filename, String base64Content, String directory) {
        try {
            if (filename == null || base64Content == null) {
                return new UploadResult(filesystem.Response.newBuilder()
                        .setMessage("El nombre del archivo y el contenido base64 no pueden ser nulos.")
                        .build());
            }

            UploadRequest request = UploadRequest.newBuilder()
                    .setFilename(filename)
                    .setDirectory(directory != null ? directory : "")
                    .setContent(ByteString.copyFrom(base64Content.getBytes()))
                    .build();

            System.out.println("Subiendo archivo: " + filename + " a " + directory);
            Response response = stub.uploadFile(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());
            System.out.println("Ruta completa devuelta por el nodo: " + response.getFilePath());

            return new UploadResult(response);

        } catch (Exception e) {
            System.err.println("Error en subida gRPC: " + e.getMessage());
            Response errorResponse = Response.newBuilder()
                    .setMessage("Error en subida gRPC: " + e.getMessage())
                    .build();
            return new UploadResult(errorResponse);
        }
    }


    public String renameFile(String oldName, String newName) {
        try {
            RenameRequest request = RenameRequest.newBuilder()
                    .setOldName(oldName)
                    .setNewName(newName)
                    .build();

            System.out.println("Renombrando: " + oldName + " a " + newName);
            Response response = stub.renameFile(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());

            return response.getMessage();

        } catch (Exception e) {
            System.err.println("Error al renombrar: " + e.getMessage());
            return "Error al renombrar archivo: " + e.getMessage();
        }
    }

    public String deleteFile(String path) {
        try {
            DeleteRequest request = DeleteRequest.newBuilder()
                    .setPath(path)
                    .build();

            System.out.println("Eliminando: " + path);
            Response response = stub.deleteFile(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());

            return response.getMessage();

        } catch (Exception e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return "Error al eliminar archivo/directorio: " + e.getMessage();
        }
    }

    public String createDirectory(String path) {
        try {
            DirectoryRequest request = DirectoryRequest.newBuilder()
                    .setPath(path)
                    .build();

            System.out.println("Creando directorio: " + path);
            Response response = stub.createDirectory(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());

            return response.getMessage();

        } catch (Exception e) {
            System.err.println("Error al crear directorio: " + e.getMessage());
            return "Error al crear directorio: " + e.getMessage();
        }
    }

    public String createSubdirectory(String parentDir, String subdirName) {
        try {
            SubdirectoryRequest request = SubdirectoryRequest.newBuilder()
                    .setParentDirectory(parentDir)
                    .setSubdirectoryName(subdirName)
                    .build();

            System.out.println("Creando subdirectorio: " + subdirName + " en " + parentDir);
            Response response = stub.createSubdirectory(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());

            return response.getMessage();

        } catch (Exception e) {
            System.err.println("Error al crear subdirectorio: " + e.getMessage());
            return "Error al crear subdirectorio: " + e.getMessage();
        }
    }

    public List<String> listFiles(String directory) {
        try {
            DirectoryRequest request = DirectoryRequest.newBuilder()
                    .setPath(directory)
                    .build();

            System.out.println("Listando archivos en: " + directory);
            ListResponse response = stub.listFiles(request);

            System.out.println("Archivos encontrados:");
            response.getFilesList().forEach(System.out::println);

            return response.getFilesList();

        } catch (Exception e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            return List.of();
        }
    }

    public String moveFile(String sourcePath, String destinationPath) {
        try {
            MoveRequest request = MoveRequest.newBuilder()
                    .setSourcePath(sourcePath)
                    .setDestinationPath(destinationPath)
                    .build();

            System.out.println("Moviendo de: " + sourcePath + " a " + destinationPath);
            Response response = stub.moveFile(request);
            System.out.println("Respuesta del nodo: " + response.getMessage());

            return response.getMessage();

        } catch (Exception e) {
            System.err.println("Error al mover archivo/directorio: " + e.getMessage());
            return "Error al mover archivo/directorio: " + e.getMessage();
        }
    }

    public ListAllResponse listAll(String directory) {
        try {
            DirectoryRequest request = DirectoryRequest.newBuilder()
                    .setPath(directory)
                    .build();

            System.out.println("Listando archivos y directorios en: " + directory);
            return stub.listAll(request);

        } catch (Exception e) {
            System.err.println("Error al listar todo: " + e.getMessage());
            return ListAllResponse.newBuilder().build(); // vacío
        }
    }

    public String getNodeId(String filename, String base64Content, String directory) {
        try {
            UploadRequest request = UploadRequest.newBuilder()
                    .setFilename(filename)
                    .setDirectory(directory != null ? directory : "")
                    .setContent(ByteString.copyFrom(base64Content.getBytes()))
                    .build();

            Response response = stub.uploadFile(request);
            System.out.println("Respuesta del nodo: " + response.getMessage() + " | Node ID: " + response.getNodeId());
            return response.getNodeId();

        } catch (Exception e) {
            System.err.println("Error al obtener Node ID: " + e.getMessage());
            return null;
        }
    }
     //Download
    public DownloadResult downloadFile(String filePath) {
      try {
        DownloadRequest request = DownloadRequest.newBuilder()
          .setPath(filePath)
          .build();

        System.out.println("Solicitando descarga del archivo: " + filePath);
        DownloadResponse response = stub.downloadFile(request);

        // Obtenemos cada campo del response
        String filename      = response.getFilename();
        String base64String  = response.getContentBase64();
        long   filesize      = response.getFilesize();
        String fileType      = response.getFileType();

        System.out.printf("Recibido del nodo → filename: %s, filesize: %d, fileType: %s%n",
          filename, filesize, fileType);

        return new DownloadResult(filename, base64String, filesize, fileType);

      } catch (Exception e) {
        System.err.println("Error al descargar archivo: " + e.getMessage());
        return null;
      }
    }

}
