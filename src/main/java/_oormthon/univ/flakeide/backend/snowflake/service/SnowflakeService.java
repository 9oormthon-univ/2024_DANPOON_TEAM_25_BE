package _oormthon.univ.flakeide.backend.snowflake.service;

import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SnowflakeService {
    private final UserTokenService userTokenService;
    private final SnowflakeCourseRepository snowflakeCourseRepository;

    public SnowflakeService(UserTokenService userTokenService,
        SnowflakeCourseRepository snowflakeCourseRepository) {
        this.userTokenService = userTokenService;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
    }

    public ListCourseResDto getCourseOfSnowflake(String authorizationHeader) {
        long snowflakeId = userTokenService.getUserInfoFromToken(authorizationHeader);
        return ListCourseResDto.builder()
            .courseList(getCourseResDtoList(snowflakeId))
            .build();
    }

    private List<CourseResDto> getCourseResDtoList(long snowflakeId) {
        return snowflakeCourseRepository.findAllBySnowflake(snowflakeId)
            .stream().map(CourseResDto::from).toList();
    }
}
