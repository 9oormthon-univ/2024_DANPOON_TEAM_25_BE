package _oormthon.univ.flakeide.backend.snowflake.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.ListUserResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.user.snowflake.AccessSnowflake;
import _oormthon.univ.flakeide.backend.snowflake.service.SnowflakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "눈송이(snowflake)의 수업 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 눈송이(snowflake)의 수업 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListCourseResDto.class))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "404", description = "눈송이(snowflake)를 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<ListCourseResDto> getCourseOfSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(snowflakeService.getCourseOfSnowflake(authorizationHeader));
    }
}
