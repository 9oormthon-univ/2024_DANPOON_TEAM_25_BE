package _oormthon.univ.flakeide.backend.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GrpcClientService {
    private final Map<String, Object> grpcClients = new ConcurrentHashMap<>();

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    @PostConstruct
    public void init() {
        // Channel 생성
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // Reflection을 통해 모든 gRPC 클라이언트 인터페이스를 찾아서 프록시 생성
        Reflections reflections = new Reflections("course.CourseIDEService");
        Set<Class<?>> grpcInterfaces = reflections.getTypesAnnotatedWith(GrpcClient.class);

        for (Class<?> interfaceClass : grpcInterfaces) {
            GrpcClient annotation = interfaceClass.getAnnotation(GrpcClient.class);
            Class<?> stubClass = annotation.stubClass();

            Object clientProxy = GrpcClientProxy.createClient(interfaceClass, (Channel) channel, stubClass);
            grpcClients.put(interfaceClass.getName(), clientProxy);
        }
    }

    public <T> T getClient(Class<T> clientClass) {
        return (T) grpcClients.get(clientClass.getName());
    }
}
