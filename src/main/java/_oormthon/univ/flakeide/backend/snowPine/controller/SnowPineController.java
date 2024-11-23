package _oormthon.univ.flakeide.backend.snowPine.controller;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineId;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineToken;
import _oormthon.univ.flakeide.backend.snowPine.service.SnowPineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snowpine")
public class SnowPineController {

    private final SnowPineService snowPineService;

    public SnowPineController(SnowPineService snowPineService) {
        this.snowPineService = snowPineService;
    }

    @GetMapping("/{snowpineId}/course")
    @AccessSnowPineId
    public ResponseEntity<ListCourseResDto> getCourseOfSnowPine(@PathVariable("snowpineId") long snowPineId) {
        return ResponseEntity.ok(snowPineService.getCourseOfSnowPine(snowPineId));
    }

}
