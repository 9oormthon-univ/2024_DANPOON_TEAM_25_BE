package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserTokenService userTokenService;
    private final SnowflakeCourseRepository snowflakeCourseRepository;

    public CourseService(CourseRepository courseRepository, UserTokenService userTokenService,
        SnowflakeCourseRepository snowflakeCourseRepository) {
        this.courseRepository = courseRepository;
        this.userTokenService = userTokenService;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
    }

    // 수업 상세조회
    public CourseResDto getCourseDetail(long courseId) {
        return CourseResDto.from(courseRepository.findById(courseId).orElseThrow(()->new CustomException("수업을 찾을 수 없습니다.", 404, 2001)));
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
