package grpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import dto.files.DownloadResult;
import dto.files.UploadResult;
import filesystem.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FileSystemClient {

    private final FileSystemServiceGrpc.FileSystemServiceBlockingStub stub;
    private final String host;
    private final int port;

    public FileSystemClient(String host, int port) {
        this.host = host;
        this.port = port;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .maxInboundMessageSize(20 * 1024 * 1024)
                .maxInboundMetadataSize(10 * 1024 * 1024)
                .build();

        stub = FileSystemServiceGrpc.newBlockingStub(channel);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public UploadResult uploadBase64File(String filename, String base64Content, String directory) {
    try {
      if (filename == null || base64Content == null) {
        return new UploadResult(filesystem.Response.newBuilder()
          .setMessage("El nombre del archivo y el contenido base64 no pueden ser nulos.")
          .build());
      }

      if (base64Content.contains(",")) {
        base64Content = base64Content.split(",")[1];
      }

      UploadRequest request = UploadRequest.newBuilder()
        .setFilename(filename)
        .setDirectory(directory != null ? directory : "")
        .setContentBase64(base64Content)
        .build();

      System.out.println("Subiendo archivo: " + filename + " a " + directory);
      Response response = stub.uploadFile(request);
      System.out.println(" Respuesta del nodo: " + response.getMessage());

      return new UploadResult(response);

    } catch (Exception e) {
      System.err.println(" Error en subida gRPC: " + e.getMessage());
      Response errorResponse = Response.newBuilder()
        .setMessage("Error en subida gRPC: " + e.getMessage())
        .build();
      return new UploadResult(errorResponse);
        }
    }


    public String renameFile(String oldName, String newName) {
        try {
            System.out.println(" Ruta en nodo: " + oldName + " a " + newName);
            RenameRequest request = RenameRequest.newBuilder()
                    .setOldName(oldName)
                    .setNewName(newName)
                    .build();

            System.out.println("Renombrando: " + oldName + " a " + newName);
            Response response = stub.renameFile(request);

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
            return ListAllResponse.newBuilder().build();
        }
    }

    public DownloadResult downloadFile(String filePath) {
      try {
        DownloadRequest request = DownloadRequest.newBuilder()
          .setPath(filePath)
          .build();

        System.out.println("Solicitando descarga del archivo: " + filePath);
        DownloadResponse response = stub.downloadFile(request);

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

    public boolean isAlive() {
        try {
            listFiles("/");
            return true;
        } catch (Exception e) {
            System.err.println("[isAlive] Nodo no disponible: " + e.getMessage());
            return false;
        }
    }

    public boolean nodeIsAlive() {
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return listFiles("/"); // Llamada que puede ser asincrónica
            } catch (Exception e) {
                return null; // Si hay error, devolvemos null
            }
        });

        try {
            // Espera el resultado con un timeout para no bloquear indefinidamente
            List<String> archivos = future.get(5, TimeUnit.SECONDS);
            return archivos != null && !archivos.isEmpty();
        } catch (TimeoutException e) {
            System.err.println("[isAlive] Tiempo de espera agotado");
            return false;
        } catch (Exception e) {
            System.err.println("[isAlive] Error al comprobar el nodo: " + e.getMessage());
            return false;
        }
    }



    public DownloadResult readFile(String filePath) {
      try {
        DownloadRequest request = DownloadRequest.newBuilder()
          .setPath(filePath)
          .build();

        System.out.println("Path " + filePath);
        DownloadResponse response = stub.downloadFile(request);

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
