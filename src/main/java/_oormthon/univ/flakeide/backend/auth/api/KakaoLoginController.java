package _oormthon.univ.flakeide.backend.auth.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.service.KakaoService;
import _oormthon.univ.flakeide.backend.auth.service.TokenProperties;
import _oormthon.univ.flakeide.backend.auth.service.TokenProvider;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth", description = "로그인/회원가입 관련 컨트롤러")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final TokenProperties tokenProperties;
    private final TokenProvider tokenProvider;

    public KakaoLoginController(KakaoService kakaoService, TokenProperties tokenProperties,
        TokenProvider tokenProvider) {
        this.kakaoService = kakaoService;
        this.tokenProperties = tokenProperties;
        this.tokenProvider = tokenProvider;
    }



    @GetMapping("/oauth/kakao")
    @Operation(summary = "카카오 로그인 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 카카오 유저 정보를 받아옴", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<Token> kakaoLogin(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoService.getAccessToken(code);
        Token token = kakaoService.loginOrSignUp(kakaoAccessToken);
        System.out.println("로그인 성공 !");

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
