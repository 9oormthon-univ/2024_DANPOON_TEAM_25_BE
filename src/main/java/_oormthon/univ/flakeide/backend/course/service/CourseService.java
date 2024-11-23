package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserTokenService userTokenService;
    private final InviteCodeService inviteCodeService;
    private final SnowflakeCourseRepository snowflakeCourseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserTokenService userTokenService,
                         SnowflakeCourseRepository snowflakeCourseRepository, UserRepository userRepository, InviteCodeService inviteCodeService) {
        this.courseRepository = courseRepository;
        this.userTokenService = userTokenService;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
        this.userRepository = userRepository;
        this.inviteCodeService = inviteCodeService;
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

    // 수업 상세조회
    public CourseResDto getCourseDetail(long courseId) {
        return CourseResDto.from(courseRepository.findById(courseId).orElseThrow(() -> new CustomException("수업을 찾을 수 없습니다.", 404, 2001)));
    }

    public ListUserResDto getUsersOfCourse(String authorizationHeader, long courseId) {
        long snowPineId = userTokenService.getUserInfoFromToken(authorizationHeader);
        return ListUserResDto.builder()
                .userResDtoList(getUserResDtoList(courseId))
                .build();
    }

    private List<UserResDto> getUserResDtoList(long courseId) {
        return snowflakeCourseRepository.findAllUserBySnowflake(courseId)
                .stream().map(UserResDto::from).toList();

    }
}
