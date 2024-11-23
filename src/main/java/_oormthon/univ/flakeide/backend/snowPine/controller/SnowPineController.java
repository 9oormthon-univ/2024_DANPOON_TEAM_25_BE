package _oormthon.univ.flakeide.backend.snowPine.controller;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineId;
import _oormthon.univ.flakeide.backend.global.util.user.snowPine.AccessSnowPineToken;
import _oormthon.univ.flakeide.backend.snowPine.service.SnowPineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "눈솔(snowPine)의 수업 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 눈솔(snowPine)의 수업 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListCourseResDto.class))),
        @ApiResponse(responseCode = "404", description = "눈솔(snowPine)을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<ListCourseResDto> getCourseOfSnowPine(@PathVariable("snowpineId") long snowPineId) {
        return ResponseEntity.ok(snowPineService.getCourseOfSnowPine(snowPineId));
    }

}
