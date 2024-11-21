package _oormthon.univ.flakeide.backend.snowflake.service;

import _oormthon.univ.flakeide.backend.auth.service.TokenProvider;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import io.jsonwebtoken.Claims;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SnowflakeService {
    private final TokenProvider tokenProvider;
    private final SnowflakeCourseRepository snowflakeCourseRepository;

    public SnowflakeService(TokenProvider tokenProvider, SnowflakeCourseRepository snowflakeCourseRepository) {
        this.tokenProvider = tokenProvider;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
    }

    public ListCourseResDto getCourseOfSnowflake(String token) {
        Claims claims = tokenProvider.parseJwt(token);
        Long id = Long.valueOf(claims.getSubject());

        return ListCourseResDto.builder()
            .courseList(getCourseResDtoList(id))
            .build();
    }

    private List<CourseResDto> getCourseResDtoList(long id) {
        return snowflakeCourseRepository.findAllBySnowflake(id)
            .stream().map(CourseResDto::from).toList();
    }
}
