package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.course.ClassGrpcClient;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseSpec;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseSpecDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseSpecRepository;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import _oormthon.univ.flakeide.backend.grpc.client.GrpcClientService;
import course.CourseIDEServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserTokenService userTokenService;
    private final InviteCodeService inviteCodeService;
    private final SnowflakeCourseRepository snowflakeCourseRepository;
    private final UserRepository userRepository;
    private final CourseSpecRepository courseSpecRepository;
    private final GrpcClientService grpcClientService;

    public CourseService(CourseRepository courseRepository, UserTokenService userTokenService,
                         SnowflakeCourseRepository snowflakeCourseRepository, UserRepository userRepository, InviteCodeService inviteCodeService, CourseSpecRepository courseSpecRepository, GrpcClientService grpcClientService) {
        this.courseRepository = courseRepository;
        this.userTokenService = userTokenService;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
        this.userRepository = userRepository;
        this.inviteCodeService = inviteCodeService;
        this.courseSpecRepository = courseSpecRepository;
        this.grpcClientService = grpcClientService;
    }

    public Course createCourse(CreateCourseDto dto) {
        User user = userRepository.findById(dto.snowpine_id()).orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", 404, 1001));
        if (user.getUserType() != UserType.SNOW_PINE) {
            throw new CustomException("선생님이 아닙니다.", 401, 2002);
        }
        String code = inviteCodeService.generateRandomString();
        Course course = Course.builder().
                title(dto.title())
                .description(dto.description())
                .maxSnowflakes(dto.max_students())
                .inviteCode(code)
                .snowPine(user).build();
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            throw new CustomException("수업 생성도중 오류가 생겼습니다.", 500, 2003);
        }
        return course;
    }

    public CourseSpec createSpec(CreateCourseSpecDto dto) {
        User student = userRepository.findById(dto.student_id()).orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", 404, 2001));
        Course existed_course = courseRepository.findById(dto.course_id()).orElseThrow(() -> new CustomException("수업을 찾을 수 없습니다.", 404, 1001));
        CourseSpec spec = CourseSpec.builder().
                spec(String.join(",", dto.spec()))
                .student(student)
                .course(existed_course)
                .build();
        try {
            courseSpecRepository.save(spec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("수업 스펙 생성 도중 오류가 생겼습니다.", 500, 3001);
        }
        ManagedChannel channel = ManagedChannelBuilder.forAddress("213.190.4.144", 31000)
                .usePlaintext()
                .build();
        CourseIDEServiceGrpc.CourseIDEServiceBlockingStub blockingStub = CourseIDEServiceGrpc.newBlockingStub(channel);
        course.Course.CourseIDECreateRequest request = course.Course.CourseIDECreateRequest.newBuilder()
                .setCourseId(dto.course_id().toString())
                .setStudentId(dto.student_id().toString())
                .build();
        ClassGrpcClient client = grpcClientService.getClient(ClassGrpcClient.class);
        System.out.println(client);
        Iterator<course.Course.CourseIDECreateResponse> responses = blockingStub.create(request);

        // Iterate over the responses
        while (responses.hasNext()) {
            course.Course.CourseIDECreateResponse response = responses.next();
            System.out.println("Received response: " + response.getMessage() + ", OK: " + response.getOk());
        }

        // Shutdown the channel
        channel.shutdown();
        return spec;
    }

    // 수업 상세조회
    public CourseResDto getCourseDetail(long courseId) {
        return CourseResDto.from(courseRepository.findById(courseId).orElseThrow(() -> new CustomException("수업을 찾을 수 없습니다.", 404, 2001)));
    }

    public ListUserResDto getUsersOfCourse(String authorizationHeader, long courseId) {

        return ListUserResDto.builder()
                .userResDtoList(getUserResDtoList(courseId))
                .build();
    }

    private List<UserResDto> getUserResDtoList(long courseId) {
        return snowflakeCourseRepository.findAllUserBySnowflake(courseId)
                .stream().map(UserResDto::from).toList();

    }
}
