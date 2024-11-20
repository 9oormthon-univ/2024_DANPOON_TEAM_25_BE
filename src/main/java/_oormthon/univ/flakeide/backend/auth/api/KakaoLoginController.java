package _oormthon.univ.flakeide.backend.auth.api;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class KakaoLoginController {

    @Autowired
    public final KakaoService kakaoService;

    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/oauth/callback/kakao")
    public @ResponseBody Token kakaoLogin(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoService.getAccessToken(code);
        Token token = kakaoService.loginOrSignUp(kakaoAccessToken);
        System.out.println("로그인 성공 !");
        return token;
    }
}
