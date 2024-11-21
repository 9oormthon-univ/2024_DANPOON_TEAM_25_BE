package _oormthon.univ.flakeide.backend.course.api;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Course> getCourseDetail(@RequestParam("courseId") long courseId) {
        Course course = courseService.getCourseDetail(courseId);
        return ResponseEntity.ok(course);
    }
}
