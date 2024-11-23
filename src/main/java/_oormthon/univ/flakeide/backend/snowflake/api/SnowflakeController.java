package _oormthon.univ.flakeide.backend.snowflake.api;

import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.global.util.user.snowflake.AccessSnowflake;
import _oormthon.univ.flakeide.backend.snowflake.service.SnowflakeService;
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
    @AccessSnowflake
    public ResponseEntity<ListCourseResDto> getCourseOfSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(snowflakeService.getCourseOfSnowflake(authorizationHeader));
    }
}
