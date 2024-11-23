package _oormthon.univ.flakeide.backend.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        try {
            // gRPC 채널 생성
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Reflections 객체 초기화 - 실제 gRPC 클라이언트 인터페이스가 위치한 패키지로 변경
            Reflections reflections = new Reflections("_oormthon.univ.flakeide.backend.course"); // 실제 패키지 경로로 변경

            // @GrpcClient 애노테이션이 있는 모든 인터페이스 스캔
            Set<Class<?>> grpcInterfaces = reflections.getTypesAnnotatedWith(GrpcClient.class);

            for (Class<?> interfaceClass : grpcInterfaces) {
                GrpcClient annotation = interfaceClass.getAnnotation(GrpcClient.class);
                Class<?> stubClass = annotation.stubClass();

                try {
                    // 클라이언트 프록시 생성
                    Object clientProxy = GrpcClientProxy.createClient(interfaceClass, channel, stubClass);
                    grpcClients.put(interfaceClass.getName(), clientProxy);
                    System.out.println("Successfully created gRPC client for: " + interfaceClass.getName());
                } catch (Exception e) {
                    System.err.println("Failed to create gRPC client for: " + interfaceClass.getName());
                    e.printStackTrace();
                }
            }

            System.out.println("Initialized gRPC clients: " + grpcClients.keySet());
        } catch (Exception e) {
            System.err.println("Failed to initialize gRPC clients.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(Class<T> clientClass) {
        System.out.println("Fetching gRPC client for: " + clientClass.getName());
        T client = (T) grpcClients.get(clientClass.getName());
        if (client == null) {
            System.err.println("gRPC client not found for: " + clientClass.getName());
        }
        return client;
    }
}