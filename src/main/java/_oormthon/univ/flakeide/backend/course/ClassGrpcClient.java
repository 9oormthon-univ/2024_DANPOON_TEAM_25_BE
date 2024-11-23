package _oormthon.univ.flakeide.backend.course;

import _oormthon.univ.flakeide.backend.grpc.client.GrpcClient;
import course.Course;
import course.CourseIDEServiceGrpc;

@GrpcClient(stubClass = CourseIDEServiceGrpc.class)
public interface ClassGrpcClient {
    Course.CourseIDECreateResponse create(Course.CourseIDECreateRequest request);
}
