package _oormthon.univ.flakeide.backend.course.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseSpec;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseDto;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseSpecDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.service.CourseService;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{courseId}")
    @Operation(summary = "수업 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수업 상세 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResDto.class))),
            @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<CourseResDto> getCourseDetail(@PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(courseService.getCourseDetail(courseId));
    }

    @PostMapping()
    @Operation(summary = "수업 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 수업을 등록함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<Course> createCourse(@RequestBody CreateCourseDto dto) {
        return ResponseEntity.ok(courseService.createCourse(dto));
    }

    @PostMapping("/{id}/spec")
    public ResponseEntity<CourseSpec> createCourseSpec(@PathVariable("id") Long courseId, @RequestBody CreateCourseSpecDto dto) {
        dto = dto.withCourseId(courseId);
        return ResponseEntity.ok(courseService.createSpec(dto));
    }

    @GetMapping("{courseId}/snowflake")
    @AccessSnowPineToken
    @Operation(summary = "수업에 참여하는 눈송이(snowflake) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수업에 참여한 눈송이(snowflake) 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListUserResDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<ListUserResDto> getUsersOfCourse(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("courseId") long curseId) {
        return ResponseEntity.ok(courseService.getUsersOfCourse(authorizationHeader, curseId));
    }

}
