package filesystem;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Servicio para operaciones del sistema de archivos
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.1)",
    comments = "Source: filesystem.proto")
public final class FileSystemServiceGrpc {

  private FileSystemServiceGrpc() {}

  public static final String SERVICE_NAME = "filesystem.FileSystemService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUploadFileMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.UploadRequest,
      filesystem.Response> METHOD_UPLOAD_FILE = getUploadFileMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.UploadRequest,
      filesystem.Response> getUploadFileMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.UploadRequest,
      filesystem.Response> getUploadFileMethod() {
    io.grpc.MethodDescriptor<filesystem.UploadRequest, filesystem.Response> getUploadFileMethod;
    if ((getUploadFileMethod = FileSystemServiceGrpc.getUploadFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getUploadFileMethod = FileSystemServiceGrpc.getUploadFileMethod) == null) {
          FileSystemServiceGrpc.getUploadFileMethod = getUploadFileMethod = 
              io.grpc.MethodDescriptor.<filesystem.UploadRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "UploadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.UploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("UploadFile"))
                  .build();
          }
        }
     }
     return getUploadFileMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateDirectoryMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.Response> METHOD_CREATE_DIRECTORY = getCreateDirectoryMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.Response> getCreateDirectoryMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.Response> getCreateDirectoryMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.Response> getCreateDirectoryMethod;
    if ((getCreateDirectoryMethod = FileSystemServiceGrpc.getCreateDirectoryMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getCreateDirectoryMethod = FileSystemServiceGrpc.getCreateDirectoryMethod) == null) {
          FileSystemServiceGrpc.getCreateDirectoryMethod = getCreateDirectoryMethod = 
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "CreateDirectory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DirectoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("CreateDirectory"))
                  .build();
          }
        }
     }
     return getCreateDirectoryMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateSubdirectoryMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest,
      filesystem.Response> METHOD_CREATE_SUBDIRECTORY = getCreateSubdirectoryMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest,
      filesystem.Response> getCreateSubdirectoryMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest,
      filesystem.Response> getCreateSubdirectoryMethod() {
    io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest, filesystem.Response> getCreateSubdirectoryMethod;
    if ((getCreateSubdirectoryMethod = FileSystemServiceGrpc.getCreateSubdirectoryMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getCreateSubdirectoryMethod = FileSystemServiceGrpc.getCreateSubdirectoryMethod) == null) {
          FileSystemServiceGrpc.getCreateSubdirectoryMethod = getCreateSubdirectoryMethod = 
              io.grpc.MethodDescriptor.<filesystem.SubdirectoryRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "CreateSubdirectory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.SubdirectoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("CreateSubdirectory"))
                  .build();
          }
        }
     }
     return getCreateSubdirectoryMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getRenameFileMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.RenameRequest,
      filesystem.Response> METHOD_RENAME_FILE = getRenameFileMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.RenameRequest,
      filesystem.Response> getRenameFileMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.RenameRequest,
      filesystem.Response> getRenameFileMethod() {
    io.grpc.MethodDescriptor<filesystem.RenameRequest, filesystem.Response> getRenameFileMethod;
    if ((getRenameFileMethod = FileSystemServiceGrpc.getRenameFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getRenameFileMethod = FileSystemServiceGrpc.getRenameFileMethod) == null) {
          FileSystemServiceGrpc.getRenameFileMethod = getRenameFileMethod = 
              io.grpc.MethodDescriptor.<filesystem.RenameRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "RenameFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.RenameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("RenameFile"))
                  .build();
          }
        }
     }
     return getRenameFileMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDeleteFileMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.DeleteRequest,
      filesystem.Response> METHOD_DELETE_FILE = getDeleteFileMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.DeleteRequest,
      filesystem.Response> getDeleteFileMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.DeleteRequest,
      filesystem.Response> getDeleteFileMethod() {
    io.grpc.MethodDescriptor<filesystem.DeleteRequest, filesystem.Response> getDeleteFileMethod;
    if ((getDeleteFileMethod = FileSystemServiceGrpc.getDeleteFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getDeleteFileMethod = FileSystemServiceGrpc.getDeleteFileMethod) == null) {
          FileSystemServiceGrpc.getDeleteFileMethod = getDeleteFileMethod = 
              io.grpc.MethodDescriptor.<filesystem.DeleteRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "DeleteFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("DeleteFile"))
                  .build();
          }
        }
     }
     return getDeleteFileMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getListFilesMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListResponse> METHOD_LIST_FILES = getListFilesMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListResponse> getListFilesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListResponse> getListFilesMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.ListResponse> getListFilesMethod;
    if ((getListFilesMethod = FileSystemServiceGrpc.getListFilesMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getListFilesMethod = FileSystemServiceGrpc.getListFilesMethod) == null) {
          FileSystemServiceGrpc.getListFilesMethod = getListFilesMethod = 
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.ListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "ListFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DirectoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.ListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("ListFiles"))
                  .build();
          }
        }
     }
     return getListFilesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getMoveFileMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.MoveRequest,
      filesystem.Response> METHOD_MOVE_FILE = getMoveFileMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.MoveRequest,
      filesystem.Response> getMoveFileMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.MoveRequest,
      filesystem.Response> getMoveFileMethod() {
    io.grpc.MethodDescriptor<filesystem.MoveRequest, filesystem.Response> getMoveFileMethod;
    if ((getMoveFileMethod = FileSystemServiceGrpc.getMoveFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getMoveFileMethod = FileSystemServiceGrpc.getMoveFileMethod) == null) {
          FileSystemServiceGrpc.getMoveFileMethod = getMoveFileMethod = 
              io.grpc.MethodDescriptor.<filesystem.MoveRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "MoveFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.MoveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("MoveFile"))
                  .build();
          }
        }
     }
     return getMoveFileMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getListAllMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListAllResponse> METHOD_LIST_ALL = getListAllMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListAllResponse> getListAllMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListAllResponse> getListAllMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.ListAllResponse> getListAllMethod;
    if ((getListAllMethod = FileSystemServiceGrpc.getListAllMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getListAllMethod = FileSystemServiceGrpc.getListAllMethod) == null) {
          FileSystemServiceGrpc.getListAllMethod = getListAllMethod = 
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.ListAllResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.FileSystemService", "ListAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DirectoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.ListAllResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("ListAll"))
                  .build();
          }
        }
     }
     return getListAllMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileSystemServiceStub newStub(io.grpc.Channel channel) {
    return new FileSystemServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileSystemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FileSystemServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileSystemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FileSystemServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static abstract class FileSystemServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void uploadFile(filesystem.UploadRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getUploadFileMethod(), responseObserver);
    }

    /**
     */
    public void createDirectory(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateDirectoryMethod(), responseObserver);
    }

    /**
     */
    public void createSubdirectory(filesystem.SubdirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateSubdirectoryMethod(), responseObserver);
    }

    /**
     */
    public void renameFile(filesystem.RenameRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getRenameFileMethod(), responseObserver);
    }

    /**
     */
    public void deleteFile(filesystem.DeleteRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteFileMethod(), responseObserver);
    }

    /**
     */
    public void listFiles(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListFilesMethod(), responseObserver);
    }

    /**
     */
    public void moveFile(filesystem.MoveRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getMoveFileMethod(), responseObserver);
    }

    /**
     */
    public void listAll(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListAllResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListAllMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.UploadRequest,
                filesystem.Response>(
                  this, METHODID_UPLOAD_FILE)))
          .addMethod(
            getCreateDirectoryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.DirectoryRequest,
                filesystem.Response>(
                  this, METHODID_CREATE_DIRECTORY)))
          .addMethod(
            getCreateSubdirectoryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.SubdirectoryRequest,
                filesystem.Response>(
                  this, METHODID_CREATE_SUBDIRECTORY)))
          .addMethod(
            getRenameFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.RenameRequest,
                filesystem.Response>(
                  this, METHODID_RENAME_FILE)))
          .addMethod(
            getDeleteFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.DeleteRequest,
                filesystem.Response>(
                  this, METHODID_DELETE_FILE)))
          .addMethod(
            getListFilesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.DirectoryRequest,
                filesystem.ListResponse>(
                  this, METHODID_LIST_FILES)))
          .addMethod(
            getMoveFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.MoveRequest,
                filesystem.Response>(
                  this, METHODID_MOVE_FILE)))
          .addMethod(
            getListAllMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.DirectoryRequest,
                filesystem.ListAllResponse>(
                  this, METHODID_LIST_ALL)))
          .build();
    }
  }

  /**
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceStub extends io.grpc.stub.AbstractStub<FileSystemServiceStub> {
    private FileSystemServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileSystemServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileSystemServiceStub(channel, callOptions);
    }

    /**
     */
    public void uploadFile(filesystem.UploadRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createDirectory(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateDirectoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createSubdirectory(filesystem.SubdirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateSubdirectoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void renameFile(filesystem.RenameRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRenameFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteFile(filesystem.DeleteRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listFiles(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void moveFile(filesystem.MoveRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMoveFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listAll(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListAllResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListAllMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceBlockingStub extends io.grpc.stub.AbstractStub<FileSystemServiceBlockingStub> {
    private FileSystemServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileSystemServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileSystemServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public filesystem.Response uploadFile(filesystem.UploadRequest request) {
      return blockingUnaryCall(
          getChannel(), getUploadFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response createDirectory(filesystem.DirectoryRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateDirectoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response createSubdirectory(filesystem.SubdirectoryRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateSubdirectoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response renameFile(filesystem.RenameRequest request) {
      return blockingUnaryCall(
          getChannel(), getRenameFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response deleteFile(filesystem.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.ListResponse listFiles(filesystem.DirectoryRequest request) {
      return blockingUnaryCall(
          getChannel(), getListFilesMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response moveFile(filesystem.MoveRequest request) {
      return blockingUnaryCall(
          getChannel(), getMoveFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.ListAllResponse listAll(filesystem.DirectoryRequest request) {
      return blockingUnaryCall(
          getChannel(), getListAllMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceFutureStub extends io.grpc.stub.AbstractStub<FileSystemServiceFutureStub> {
    private FileSystemServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileSystemServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileSystemServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> uploadFile(
        filesystem.UploadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> createDirectory(
        filesystem.DirectoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateDirectoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> createSubdirectory(
        filesystem.SubdirectoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateSubdirectoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> renameFile(
        filesystem.RenameRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRenameFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> deleteFile(
        filesystem.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.ListResponse> listFiles(
        filesystem.DirectoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> moveFile(
        filesystem.MoveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMoveFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.ListAllResponse> listAll(
        filesystem.DirectoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListAllMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPLOAD_FILE = 0;
  private static final int METHODID_CREATE_DIRECTORY = 1;
  private static final int METHODID_CREATE_SUBDIRECTORY = 2;
  private static final int METHODID_RENAME_FILE = 3;
  private static final int METHODID_DELETE_FILE = 4;
  private static final int METHODID_LIST_FILES = 5;
  private static final int METHODID_MOVE_FILE = 6;
  private static final int METHODID_LIST_ALL = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileSystemServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileSystemServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_FILE:
          serviceImpl.uploadFile((filesystem.UploadRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_CREATE_DIRECTORY:
          serviceImpl.createDirectory((filesystem.DirectoryRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_CREATE_SUBDIRECTORY:
          serviceImpl.createSubdirectory((filesystem.SubdirectoryRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_RENAME_FILE:
          serviceImpl.renameFile((filesystem.RenameRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_DELETE_FILE:
          serviceImpl.deleteFile((filesystem.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_LIST_FILES:
          serviceImpl.listFiles((filesystem.DirectoryRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.ListResponse>) responseObserver);
          break;
        case METHODID_MOVE_FILE:
          serviceImpl.moveFile((filesystem.MoveRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_LIST_ALL:
          serviceImpl.listAll((filesystem.DirectoryRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.ListAllResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FileSystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileSystemServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return filesystem.Filesystem.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileSystemService");
    }
  }

  private static final class FileSystemServiceFileDescriptorSupplier
      extends FileSystemServiceBaseDescriptorSupplier {
    FileSystemServiceFileDescriptorSupplier() {}
  }

  private static final class FileSystemServiceMethodDescriptorSupplier
      extends FileSystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileSystemServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileSystemServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileSystemServiceFileDescriptorSupplier())
              .addMethod(getUploadFileMethod())
              .addMethod(getCreateDirectoryMethod())
              .addMethod(getCreateSubdirectoryMethod())
              .addMethod(getRenameFileMethod())
              .addMethod(getDeleteFileMethod())
              .addMethod(getListFilesMethod())
              .addMethod(getMoveFileMethod())
              .addMethod(getListAllMethod())
              .build();
        }
      }
    }
    return result;
  }
}
