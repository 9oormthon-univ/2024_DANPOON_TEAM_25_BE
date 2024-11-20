package _oormthon.univ.flakeide.backend.auth.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final String secretKey = "yourSuperSecretKey1234567890yourSuperSecretKey123456";  // 비밀 키 설정 (256비트 이상)
    private final long accessTokenValidityTime = 3600000L;  // 토큰 유효 기간 (1시간)

    public Token createToken(User user) {
        // 현재 시간
        long nowTime = (new Date()).getTime();
        Date tokenExpiredTime = new Date(nowTime + accessTokenValidityTime);  // 만료 시간 설정

        // JWT 생성
        String accessToken = Jwts.builder()
            .setSubject(user.getId().toString()) // 사용자 ID를 subject로 설정
            .setExpiration(tokenExpiredTime) // 토큰 만료 시간 설정
            .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 설정 (비밀 키 사용)
            .compact();

        return Token.builder()
            .accessToken(accessToken)
            .build();
    }
}
