package _oormthon.univ.flakeide.backend.auth.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.Token;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private final TokenProperties tokenProperties;

    public TokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public Token createToken(User user) {
        long nowTime = (new Date()).getTime();
        Date tokenExpiredTime = new Date(nowTime + tokenProperties.getTokenValidityTime());

        String accessToken = Jwts.builder()
            .setSubject(user.getId().toString())
            .setExpiration(tokenExpiredTime)
            .signWith(SignatureAlgorithm.HS256, getSignInKey())
            .compact();

        return Token.builder()
            .accessToken(accessToken)
            .build();
    }

    public Claims parseJwt(String token) {
        token = token.trim();
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = tokenProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(bytes, "HmacSHA256");
    }
}
