package _oormthon.univ.flakeide.backend.snowPine.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SnowPineService {

    private final UserRepository snowPineRepository;
    private final UserTokenService userTokenService;
    private final CourseRepository courseRepository;
    private final SnowflakeCourseRepository snowflakeCourseRepository;

    public SnowPineService(UserRepository snowPineRepository, UserTokenService userTokenService,
        CourseRepository courseRepository, SnowflakeCourseRepository snowflakeCourseRepository) {
        this.snowPineRepository = snowPineRepository;
        this.userTokenService = userTokenService;
        this.courseRepository = courseRepository;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
    }

    public ListCourseResDto getCourseOfSnowPine(long snowPineId) {
        User snowPine = snowPineRepository.findById(snowPineId).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
        return ListCourseResDto.builder()
            .courseList(getCourseResDtoList(snowPine))
            .build();
    }

    private List<CourseResDto> getCourseResDtoList(User snowPine) {
        return courseRepository.findAllBySnowPine(snowPine).stream().map(
            CourseResDto::from).toList();
    }
}
