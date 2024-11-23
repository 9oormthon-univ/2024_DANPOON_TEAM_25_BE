package _oormthon.univ.flakeide.backend.snowPine.controller;

import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPine;
import _oormthon.univ.flakeide.backend.snowPine.service.SnowPineService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snowpine")
public class SnowPineController {

    private final SnowPineService snowPineService;

    public SnowPineController(SnowPineService snowPineService) {
        this.snowPineService = snowPineService;
    }

    @GetMapping("/{snowpineId}/course")
    @AccessSnowPine
    public ResponseEntity<ListCourseResDto> getCourseOfSnowPine(@PathVariable("snowpineId") long snowPineId) {
        return ResponseEntity.ok(snowPineService.getCourseOfSnowPine(snowPineId));
    }
}
