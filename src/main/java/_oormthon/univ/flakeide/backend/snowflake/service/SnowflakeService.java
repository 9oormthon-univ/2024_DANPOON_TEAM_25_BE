package _oormthon.univ.flakeide.backend.snowflake.service;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.auth.service.TokenProvider;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.SnowflakeCourse;
import _oormthon.univ.flakeide.backend.course.domain.repository.SnowflakeCourseRepository;
import io.jsonwebtoken.Claims;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SnowflakeService {
    private final UserRepository snowflakeRepository;
    private final TokenProvider tokenProvider;
    private final SnowflakeCourseRepository snowflakeCourseRepository;

    public SnowflakeService(UserRepository snowflakeRepository, TokenProvider tokenProvider,
        SnowflakeCourseRepository snowflakeCourseRepository) {
        this.snowflakeRepository = snowflakeRepository;
        this.tokenProvider = tokenProvider;
        this.snowflakeCourseRepository = snowflakeCourseRepository;
    }

    public List<Course> getCourseOfSnowflake(String token) {
        Claims claims = tokenProvider.parseJwt(token);
        Long id = Long.valueOf(claims.getSubject());

        User snowflake = snowflakeRepository.findById(id).orElseThrow();
        return snowflakeCourseRepository.findAllBySnowflake(snowflake)
            .stream()
            .map(SnowflakeCourse::getCourse)
            .toList();
    }
}
