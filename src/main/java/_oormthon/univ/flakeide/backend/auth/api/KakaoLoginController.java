package _oormthon.univ.flakeide.backend.auth.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "로그인/회원가입 관련 컨트롤러")
public class KakaoLoginController {

    @Autowired
    public final KakaoService kakaoService;

    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/oauth/callback/kakao")
    @Operation(summary = "카카오 로그인 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 카카오 유저 정보를 받아옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class)))
    })
    public @ResponseBody Token kakaoLogin(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoService.getAccessToken(code);
        Token token = kakaoService.loginOrSignUp(kakaoAccessToken);
        System.out.println("로그인 성공 !");
        return token;
    }

    @PostMapping("/snowflake/signup")
    @Operation(summary = "눈송이(Snowflake) 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 눈송이(Snowflake) 회원가입을 성공함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<User> signUpSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(kakaoService.signUpSnowflake(authorizationHeader));
    }

    @PostMapping("/snowPine/signup")
    @Operation(summary = "눈설(SnowPine) 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 눈설(SnowPine) 회원가입을 성공함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<User> signUpSnowPine(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(kakaoService.signUpSnowPine(authorizationHeader));
    }
}
