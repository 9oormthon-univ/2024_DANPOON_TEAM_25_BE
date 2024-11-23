package _oormthon.univ.flakeide.backend.course.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.service.CourseService;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseResDto> getCourseDetail(@PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(courseService.getCourseDetail(courseId));
    }

    @GetMapping("{courseId}/snowflake")
    @AccessSnowPineToken
    public ResponseEntity<ListUserResDto> getUsersOfCourse(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("courseId") long curseId) {
        return ResponseEntity.ok(courseService.getUsersOfCourse(authorizationHeader, curseId));
    }
}
