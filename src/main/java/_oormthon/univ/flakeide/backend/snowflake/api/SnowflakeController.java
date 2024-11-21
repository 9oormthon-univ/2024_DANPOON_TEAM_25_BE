package _oormthon.univ.flakeide.backend.snowflake.api;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.snowflake.service.SnowflakeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snowflake")
public class SnowflakeController {

    private final SnowflakeService snowflakeService;

    public SnowflakeController(SnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @GetMapping("/course")
    public ResponseEntity<List<Course>> getCourseOfSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        List<Course> coursesOfSnowflakes = snowflakeService.getCourseOfSnowflake(authorizationHeader);

        return ResponseEntity.ok(coursesOfSnowflakes);
    }
}
