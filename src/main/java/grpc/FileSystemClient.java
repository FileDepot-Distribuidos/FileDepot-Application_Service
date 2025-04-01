package grpc;

import com.google.protobuf.ByteString;
import filesystem.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class FileSystemClient {

    private final FileSystemServiceGrpc.FileSystemServiceBlockingStub stub;

    public FileSystemClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        stub = FileSystemServiceGrpc.newBlockingStub(channel);
    }

    public String uploadBase64File(String filename, String base64Content, String directory) {
        try {
            if (filename == null || base64Content == null) {
                return "El nombre del archivo y el contenido base64 no pueden ser nulos.";
            }

            UploadRequest request = UploadRequest.newBuilder()
                    .setFilename(filename)
                    .setDirectory(directory != null ? directory : "")
                    .setContent(ByteString.copyFrom(base64Content.getBytes()))
                    .build();

            Response response = stub.uploadFile(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error en subida gRPC: " + e.getMessage();
        }
    }
    public String renameFile(String oldName, String newName) {
        try {
            RenameRequest request = RenameRequest.newBuilder()
                    .setOldName(oldName)
                    .setNewName(newName)
                    .build();

            Response response = stub.renameFile(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error al renombrar archivo: " + e.getMessage();
        }
    }
    public String deleteFile(String path) {
        try {
            DeleteRequest request = DeleteRequest.newBuilder()
                    .setPath(path)
                    .build();

            Response response = stub.deleteFile(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error al eliminar archivo/directorio: " + e.getMessage();
        }
    }

    public String createDirectory(String path) {
        try {
            DirectoryRequest request = DirectoryRequest.newBuilder()
                    .setPath(path)
                    .build();

            Response response = stub.createDirectory(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error al crear directorio: " + e.getMessage();
        }
    }
    public String createSubdirectory(String parentDir, String subdirName) {
        try {
            SubdirectoryRequest request = SubdirectoryRequest.newBuilder()
                    .setParentDirectory(parentDir)
                    .setSubdirectoryName(subdirName)
                    .build();

            Response response = stub.createSubdirectory(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error al crear subdirectorio: " + e.getMessage();
        }
    }

    public List<String> listFiles(String directory) {
        try {
            DirectoryRequest request = DirectoryRequest.newBuilder()
                    .setPath(directory)
                    .build();

            ListResponse response = stub.listFiles(request);
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

            Response response = stub.moveFile(request);
            return response.getMessage();

        } catch (Exception e) {
            return "Error al mover archivo/directorio: " + e.getMessage();
        }
    }
}
