package _oormthon.univ.flakeide.backend.auth.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KakaoLoginController {

    @Autowired
    public final KakaoService kakaoService;

    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("")
    @GetMapping("/oauth/callback/kakao")
    public @ResponseBody Token kakaoLogin(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoService.getAccessToken(code);
        Token token = kakaoService.loginOrSignUp(kakaoAccessToken);
        System.out.println("로그인 성공 !");
        return token;
    }

    @PostMapping("/snowflake/signup")
    public ResponseEntity<User> signUpSnowflake(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(kakaoService.signUpSnowflake(authorizationHeader));
    }

    @PostMapping("/snowPine/signup")
    public ResponseEntity<User> signUpSnowPine(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(kakaoService.signUpSnowPine(authorizationHeader));
    }
}
