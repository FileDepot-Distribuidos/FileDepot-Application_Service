package filesystem;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Servicio para operaciones del sistema de archivos
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.3)",
    comments = "Source: filesystem.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FileSystemServiceGrpc {

  private FileSystemServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "filesystem.FileSystemService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<filesystem.UploadRequest,
      filesystem.Response> getUploadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadFile",
      requestType = filesystem.UploadRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.UploadRequest,
      filesystem.Response> getUploadFileMethod() {
    io.grpc.MethodDescriptor<filesystem.UploadRequest, filesystem.Response> getUploadFileMethod;
    if ((getUploadFileMethod = FileSystemServiceGrpc.getUploadFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getUploadFileMethod = FileSystemServiceGrpc.getUploadFileMethod) == null) {
          FileSystemServiceGrpc.getUploadFileMethod = getUploadFileMethod =
              io.grpc.MethodDescriptor.<filesystem.UploadRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadFile"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.Response> getCreateDirectoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateDirectory",
      requestType = filesystem.DirectoryRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.Response> getCreateDirectoryMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.Response> getCreateDirectoryMethod;
    if ((getCreateDirectoryMethod = FileSystemServiceGrpc.getCreateDirectoryMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getCreateDirectoryMethod = FileSystemServiceGrpc.getCreateDirectoryMethod) == null) {
          FileSystemServiceGrpc.getCreateDirectoryMethod = getCreateDirectoryMethod =
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateDirectory"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest,
      filesystem.Response> getCreateSubdirectoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSubdirectory",
      requestType = filesystem.SubdirectoryRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest,
      filesystem.Response> getCreateSubdirectoryMethod() {
    io.grpc.MethodDescriptor<filesystem.SubdirectoryRequest, filesystem.Response> getCreateSubdirectoryMethod;
    if ((getCreateSubdirectoryMethod = FileSystemServiceGrpc.getCreateSubdirectoryMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getCreateSubdirectoryMethod = FileSystemServiceGrpc.getCreateSubdirectoryMethod) == null) {
          FileSystemServiceGrpc.getCreateSubdirectoryMethod = getCreateSubdirectoryMethod =
              io.grpc.MethodDescriptor.<filesystem.SubdirectoryRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSubdirectory"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.RenameRequest,
      filesystem.Response> getRenameFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RenameFile",
      requestType = filesystem.RenameRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.RenameRequest,
      filesystem.Response> getRenameFileMethod() {
    io.grpc.MethodDescriptor<filesystem.RenameRequest, filesystem.Response> getRenameFileMethod;
    if ((getRenameFileMethod = FileSystemServiceGrpc.getRenameFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getRenameFileMethod = FileSystemServiceGrpc.getRenameFileMethod) == null) {
          FileSystemServiceGrpc.getRenameFileMethod = getRenameFileMethod =
              io.grpc.MethodDescriptor.<filesystem.RenameRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RenameFile"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.DeleteRequest,
      filesystem.Response> getDeleteFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteFile",
      requestType = filesystem.DeleteRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.DeleteRequest,
      filesystem.Response> getDeleteFileMethod() {
    io.grpc.MethodDescriptor<filesystem.DeleteRequest, filesystem.Response> getDeleteFileMethod;
    if ((getDeleteFileMethod = FileSystemServiceGrpc.getDeleteFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getDeleteFileMethod = FileSystemServiceGrpc.getDeleteFileMethod) == null) {
          FileSystemServiceGrpc.getDeleteFileMethod = getDeleteFileMethod =
              io.grpc.MethodDescriptor.<filesystem.DeleteRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteFile"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListResponse> getListFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListFiles",
      requestType = filesystem.DirectoryRequest.class,
      responseType = filesystem.ListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListResponse> getListFilesMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.ListResponse> getListFilesMethod;
    if ((getListFilesMethod = FileSystemServiceGrpc.getListFilesMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getListFilesMethod = FileSystemServiceGrpc.getListFilesMethod) == null) {
          FileSystemServiceGrpc.getListFilesMethod = getListFilesMethod =
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.ListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListFiles"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.MoveRequest,
      filesystem.Response> getMoveFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MoveFile",
      requestType = filesystem.MoveRequest.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.MoveRequest,
      filesystem.Response> getMoveFileMethod() {
    io.grpc.MethodDescriptor<filesystem.MoveRequest, filesystem.Response> getMoveFileMethod;
    if ((getMoveFileMethod = FileSystemServiceGrpc.getMoveFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getMoveFileMethod = FileSystemServiceGrpc.getMoveFileMethod) == null) {
          FileSystemServiceGrpc.getMoveFileMethod = getMoveFileMethod =
              io.grpc.MethodDescriptor.<filesystem.MoveRequest, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MoveFile"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListAllResponse> getListAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListAll",
      requestType = filesystem.DirectoryRequest.class,
      responseType = filesystem.ListAllResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.DirectoryRequest,
      filesystem.ListAllResponse> getListAllMethod() {
    io.grpc.MethodDescriptor<filesystem.DirectoryRequest, filesystem.ListAllResponse> getListAllMethod;
    if ((getListAllMethod = FileSystemServiceGrpc.getListAllMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getListAllMethod = FileSystemServiceGrpc.getListAllMethod) == null) {
          FileSystemServiceGrpc.getListAllMethod = getListAllMethod =
              io.grpc.MethodDescriptor.<filesystem.DirectoryRequest, filesystem.ListAllResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListAll"))
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

  private static volatile io.grpc.MethodDescriptor<filesystem.DownloadRequest,
      filesystem.DownloadResponse> getDownloadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadFile",
      requestType = filesystem.DownloadRequest.class,
      responseType = filesystem.DownloadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.DownloadRequest,
      filesystem.DownloadResponse> getDownloadFileMethod() {
    io.grpc.MethodDescriptor<filesystem.DownloadRequest, filesystem.DownloadResponse> getDownloadFileMethod;
    if ((getDownloadFileMethod = FileSystemServiceGrpc.getDownloadFileMethod) == null) {
      synchronized (FileSystemServiceGrpc.class) {
        if ((getDownloadFileMethod = FileSystemServiceGrpc.getDownloadFileMethod) == null) {
          FileSystemServiceGrpc.getDownloadFileMethod = getDownloadFileMethod =
              io.grpc.MethodDescriptor.<filesystem.DownloadRequest, filesystem.DownloadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DownloadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.DownloadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FileSystemServiceMethodDescriptorSupplier("DownloadFile"))
              .build();
        }
      }
    }
    return getDownloadFileMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileSystemServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceStub>() {
        @java.lang.Override
        public FileSystemServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSystemServiceStub(channel, callOptions);
        }
      };
    return FileSystemServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileSystemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceBlockingStub>() {
        @java.lang.Override
        public FileSystemServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSystemServiceBlockingStub(channel, callOptions);
        }
      };
    return FileSystemServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileSystemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSystemServiceFutureStub>() {
        @java.lang.Override
        public FileSystemServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSystemServiceFutureStub(channel, callOptions);
        }
      };
    return FileSystemServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void uploadFile(filesystem.UploadRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUploadFileMethod(), responseObserver);
    }

    /**
     */
    default void createDirectory(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateDirectoryMethod(), responseObserver);
    }

    /**
     */
    default void createSubdirectory(filesystem.SubdirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSubdirectoryMethod(), responseObserver);
    }

    /**
     */
    default void renameFile(filesystem.RenameRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRenameFileMethod(), responseObserver);
    }

    /**
     */
    default void deleteFile(filesystem.DeleteRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteFileMethod(), responseObserver);
    }

    /**
     */
    default void listFiles(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListFilesMethod(), responseObserver);
    }

    /**
     */
    default void moveFile(filesystem.MoveRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMoveFileMethod(), responseObserver);
    }

    /**
     */
    default void listAll(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListAllResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListAllMethod(), responseObserver);
    }

    /**
     * <pre>
     * &lt;--- Nuevo método
     * </pre>
     */
    default void downloadFile(filesystem.DownloadRequest request,
        io.grpc.stub.StreamObserver<filesystem.DownloadResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadFileMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FileSystemService.
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static abstract class FileSystemServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FileSystemServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FileSystemService.
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceStub
      extends io.grpc.stub.AbstractAsyncStub<FileSystemServiceStub> {
    private FileSystemServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSystemServiceStub(channel, callOptions);
    }

    /**
     */
    public void uploadFile(filesystem.UploadRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createDirectory(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateDirectoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createSubdirectory(filesystem.SubdirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSubdirectoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void renameFile(filesystem.RenameRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRenameFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteFile(filesystem.DeleteRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listFiles(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void moveFile(filesystem.MoveRequest request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMoveFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listAll(filesystem.DirectoryRequest request,
        io.grpc.stub.StreamObserver<filesystem.ListAllResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListAllMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * &lt;--- Nuevo método
     * </pre>
     */
    public void downloadFile(filesystem.DownloadRequest request,
        io.grpc.stub.StreamObserver<filesystem.DownloadResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDownloadFileMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FileSystemService.
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FileSystemServiceBlockingStub> {
    private FileSystemServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSystemServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public filesystem.Response uploadFile(filesystem.UploadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUploadFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response createDirectory(filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateDirectoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response createSubdirectory(filesystem.SubdirectoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSubdirectoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response renameFile(filesystem.RenameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRenameFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response deleteFile(filesystem.DeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.ListResponse listFiles(filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListFilesMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response moveFile(filesystem.MoveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMoveFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.ListAllResponse listAll(filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListAllMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * &lt;--- Nuevo método
     * </pre>
     */
    public filesystem.DownloadResponse downloadFile(filesystem.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDownloadFileMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FileSystemService.
   * <pre>
   * Servicio para operaciones del sistema de archivos
   * </pre>
   */
  public static final class FileSystemServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<FileSystemServiceFutureStub> {
    private FileSystemServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSystemServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSystemServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> uploadFile(
        filesystem.UploadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> createDirectory(
        filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateDirectoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> createSubdirectory(
        filesystem.SubdirectoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSubdirectoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> renameFile(
        filesystem.RenameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRenameFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> deleteFile(
        filesystem.DeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.ListResponse> listFiles(
        filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> moveFile(
        filesystem.MoveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMoveFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.ListAllResponse> listAll(
        filesystem.DirectoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListAllMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * &lt;--- Nuevo método
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.DownloadResponse> downloadFile(
        filesystem.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDownloadFileMethod(), getCallOptions()), request);
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
  private static final int METHODID_DOWNLOAD_FILE = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
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
        case METHODID_DOWNLOAD_FILE:
          serviceImpl.downloadFile((filesystem.DownloadRequest) request,
              (io.grpc.stub.StreamObserver<filesystem.DownloadResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getUploadFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.UploadRequest,
              filesystem.Response>(
                service, METHODID_UPLOAD_FILE)))
        .addMethod(
          getCreateDirectoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.DirectoryRequest,
              filesystem.Response>(
                service, METHODID_CREATE_DIRECTORY)))
        .addMethod(
          getCreateSubdirectoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.SubdirectoryRequest,
              filesystem.Response>(
                service, METHODID_CREATE_SUBDIRECTORY)))
        .addMethod(
          getRenameFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.RenameRequest,
              filesystem.Response>(
                service, METHODID_RENAME_FILE)))
        .addMethod(
          getDeleteFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.DeleteRequest,
              filesystem.Response>(
                service, METHODID_DELETE_FILE)))
        .addMethod(
          getListFilesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.DirectoryRequest,
              filesystem.ListResponse>(
                service, METHODID_LIST_FILES)))
        .addMethod(
          getMoveFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.MoveRequest,
              filesystem.Response>(
                service, METHODID_MOVE_FILE)))
        .addMethod(
          getListAllMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.DirectoryRequest,
              filesystem.ListAllResponse>(
                service, METHODID_LIST_ALL)))
        .addMethod(
          getDownloadFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.DownloadRequest,
              filesystem.DownloadResponse>(
                service, METHODID_DOWNLOAD_FILE)))
        .build();
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
    private final java.lang.String methodName;

    FileSystemServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
              .addMethod(getDownloadFileMethod())
              .build();
        }
      }
    }
    return result;
  }
}
