package filesystem;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Servicio para el registro y estado de los nodos
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class NodeServiceGrpc {

  private NodeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "filesystem.NodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<filesystem.NodeInfo,
      filesystem.Response> getRegisterNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterNode",
      requestType = filesystem.NodeInfo.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.NodeInfo,
      filesystem.Response> getRegisterNodeMethod() {
    io.grpc.MethodDescriptor<filesystem.NodeInfo, filesystem.Response> getRegisterNodeMethod;
    if ((getRegisterNodeMethod = NodeServiceGrpc.getRegisterNodeMethod) == null) {
      synchronized (NodeServiceGrpc.class) {
        if ((getRegisterNodeMethod = NodeServiceGrpc.getRegisterNodeMethod) == null) {
          NodeServiceGrpc.getRegisterNodeMethod = getRegisterNodeMethod =
              io.grpc.MethodDescriptor.<filesystem.NodeInfo, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.NodeInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
              .setSchemaDescriptor(new NodeServiceMethodDescriptorSupplier("RegisterNode"))
              .build();
        }
      }
    }
    return getRegisterNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<filesystem.NodeStatus,
      filesystem.Response> getReportStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportStatus",
      requestType = filesystem.NodeStatus.class,
      responseType = filesystem.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<filesystem.NodeStatus,
      filesystem.Response> getReportStatusMethod() {
    io.grpc.MethodDescriptor<filesystem.NodeStatus, filesystem.Response> getReportStatusMethod;
    if ((getReportStatusMethod = NodeServiceGrpc.getReportStatusMethod) == null) {
      synchronized (NodeServiceGrpc.class) {
        if ((getReportStatusMethod = NodeServiceGrpc.getReportStatusMethod) == null) {
          NodeServiceGrpc.getReportStatusMethod = getReportStatusMethod =
              io.grpc.MethodDescriptor.<filesystem.NodeStatus, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.NodeStatus.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filesystem.Response.getDefaultInstance()))
              .setSchemaDescriptor(new NodeServiceMethodDescriptorSupplier("ReportStatus"))
              .build();
        }
      }
    }
    return getReportStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NodeServiceStub>() {
        @java.lang.Override
        public NodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NodeServiceStub(channel, callOptions);
        }
      };
    return NodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NodeServiceBlockingStub>() {
        @java.lang.Override
        public NodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NodeServiceBlockingStub(channel, callOptions);
        }
      };
    return NodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NodeServiceFutureStub>() {
        @java.lang.Override
        public NodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NodeServiceFutureStub(channel, callOptions);
        }
      };
    return NodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void registerNode(filesystem.NodeInfo request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterNodeMethod(), responseObserver);
    }

    /**
     */
    default void reportStatus(filesystem.NodeStatus request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportStatusMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NodeService.
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static abstract class NodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NodeService.
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NodeServiceStub> {
    private NodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NodeServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerNode(filesystem.NodeInfo request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reportStatus(filesystem.NodeStatus request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NodeService.
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NodeServiceBlockingStub> {
    private NodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NodeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public filesystem.Response registerNode(filesystem.NodeInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response reportStatus(filesystem.NodeStatus request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NodeService.
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NodeServiceFutureStub> {
    private NodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NodeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> registerNode(
        filesystem.NodeInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> reportStatus(
        filesystem.NodeStatus request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_NODE = 0;
  private static final int METHODID_REPORT_STATUS = 1;

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
        case METHODID_REGISTER_NODE:
          serviceImpl.registerNode((filesystem.NodeInfo) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
          break;
        case METHODID_REPORT_STATUS:
          serviceImpl.reportStatus((filesystem.NodeStatus) request,
              (io.grpc.stub.StreamObserver<filesystem.Response>) responseObserver);
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
          getRegisterNodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.NodeInfo,
              filesystem.Response>(
                service, METHODID_REGISTER_NODE)))
        .addMethod(
          getReportStatusMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              filesystem.NodeStatus,
              filesystem.Response>(
                service, METHODID_REPORT_STATUS)))
        .build();
  }

  private static abstract class NodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NodeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return filesystem.Filesystem.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NodeService");
    }
  }

  private static final class NodeServiceFileDescriptorSupplier
      extends NodeServiceBaseDescriptorSupplier {
    NodeServiceFileDescriptorSupplier() {}
  }

  private static final class NodeServiceMethodDescriptorSupplier
      extends NodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NodeServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (NodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NodeServiceFileDescriptorSupplier())
              .addMethod(getRegisterNodeMethod())
              .addMethod(getReportStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
