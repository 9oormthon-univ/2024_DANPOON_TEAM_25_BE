package _oormthon.univ.flakeide.backend.auth.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.service.UserService;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/snowflake/signup")
    @Operation(summary = "눈송이(Snowflake) 회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 눈송이(Snowflake) 회원가입을 성공함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<UserResDto> signUpSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(userService.signUpSnowflake(authorizationHeader));
    }

    @PostMapping("/snowPine/signup")
    @Operation(summary = "눈솔(SnowPine) 회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 눈솔(SnowPine) 회원가입을 성공함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<UserResDto> signUpSnowPine(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(userService.signUpSnowPine(authorizationHeader));
    }
}
