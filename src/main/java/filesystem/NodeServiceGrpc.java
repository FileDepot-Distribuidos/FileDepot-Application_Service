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
 * Servicio para el registro y estado de los nodos
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.1)",
    comments = "Source: filesystem.proto")
public final class NodeServiceGrpc {

  private NodeServiceGrpc() {}

  public static final String SERVICE_NAME = "filesystem.NodeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getRegisterNodeMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.NodeInfo,
      filesystem.Response> METHOD_REGISTER_NODE = getRegisterNodeMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.NodeInfo,
      filesystem.Response> getRegisterNodeMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.NodeInfo,
      filesystem.Response> getRegisterNodeMethod() {
    io.grpc.MethodDescriptor<filesystem.NodeInfo, filesystem.Response> getRegisterNodeMethod;
    if ((getRegisterNodeMethod = NodeServiceGrpc.getRegisterNodeMethod) == null) {
      synchronized (NodeServiceGrpc.class) {
        if ((getRegisterNodeMethod = NodeServiceGrpc.getRegisterNodeMethod) == null) {
          NodeServiceGrpc.getRegisterNodeMethod = getRegisterNodeMethod = 
              io.grpc.MethodDescriptor.<filesystem.NodeInfo, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.NodeService", "RegisterNode"))
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
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getReportStatusMethod()} instead. 
  public static final io.grpc.MethodDescriptor<filesystem.NodeStatus,
      filesystem.Response> METHOD_REPORT_STATUS = getReportStatusMethod();

  private static volatile io.grpc.MethodDescriptor<filesystem.NodeStatus,
      filesystem.Response> getReportStatusMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<filesystem.NodeStatus,
      filesystem.Response> getReportStatusMethod() {
    io.grpc.MethodDescriptor<filesystem.NodeStatus, filesystem.Response> getReportStatusMethod;
    if ((getReportStatusMethod = NodeServiceGrpc.getReportStatusMethod) == null) {
      synchronized (NodeServiceGrpc.class) {
        if ((getReportStatusMethod = NodeServiceGrpc.getReportStatusMethod) == null) {
          NodeServiceGrpc.getReportStatusMethod = getReportStatusMethod = 
              io.grpc.MethodDescriptor.<filesystem.NodeStatus, filesystem.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "filesystem.NodeService", "ReportStatus"))
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
    return new NodeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NodeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NodeServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static abstract class NodeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void registerNode(filesystem.NodeInfo request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterNodeMethod(), responseObserver);
    }

    /**
     */
    public void reportStatus(filesystem.NodeStatus request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getReportStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterNodeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.NodeInfo,
                filesystem.Response>(
                  this, METHODID_REGISTER_NODE)))
          .addMethod(
            getReportStatusMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                filesystem.NodeStatus,
                filesystem.Response>(
                  this, METHODID_REPORT_STATUS)))
          .build();
    }
  }

  /**
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceStub extends io.grpc.stub.AbstractStub<NodeServiceStub> {
    private NodeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NodeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NodeServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerNode(filesystem.NodeInfo request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reportStatus(filesystem.NodeStatus request,
        io.grpc.stub.StreamObserver<filesystem.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReportStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceBlockingStub extends io.grpc.stub.AbstractStub<NodeServiceBlockingStub> {
    private NodeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NodeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NodeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public filesystem.Response registerNode(filesystem.NodeInfo request) {
      return blockingUnaryCall(
          getChannel(), getRegisterNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public filesystem.Response reportStatus(filesystem.NodeStatus request) {
      return blockingUnaryCall(
          getChannel(), getReportStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Servicio para el registro y estado de los nodos
   * </pre>
   */
  public static final class NodeServiceFutureStub extends io.grpc.stub.AbstractStub<NodeServiceFutureStub> {
    private NodeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NodeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NodeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NodeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> registerNode(
        filesystem.NodeInfo request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<filesystem.Response> reportStatus(
        filesystem.NodeStatus request) {
      return futureUnaryCall(
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
    private final NodeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NodeServiceImplBase serviceImpl, int methodId) {
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
    private final String methodName;

    NodeServiceMethodDescriptorSupplier(String methodName) {
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
