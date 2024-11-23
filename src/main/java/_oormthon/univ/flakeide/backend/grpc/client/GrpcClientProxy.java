package _oormthon.univ.flakeide.backend.grpc.client;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.channels.Channel;

public class GrpcClientProxy implements InvocationHandler {
    private final Channel channel;
    private final Class<?> grpcStubClass;

    public GrpcClientProxy(Channel channel, Class<?> grpcStubClass) {
        this.channel = channel;
        this.grpcStubClass = grpcStubClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Get the blocking stub instance
        Method newBlockingStubMethod = grpcStubClass.getMethod("newBlockingStub", Channel.class);
        Object blockingStub = newBlockingStubMethod.invoke(null, channel);

        // Find and invoke the actual gRPC method
        Method grpcMethod = blockingStub.getClass().getMethod(method.getName(), method.getParameterTypes());
        return grpcMethod.invoke(blockingStub, args);
    }

    public static <T> T createClient(Class<T> interfaceClass, Channel channel, Class<?> grpcStubClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new GrpcClientProxy(channel, grpcStubClass)
        );
    }
}
