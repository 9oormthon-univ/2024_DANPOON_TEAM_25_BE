package _oormthon.univ.flakeide.backend.global.util;

import _oormthon.univ.flakeide.backend.auth.service.TokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    private final TokenProvider tokenProvider;

    public UserTokenService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public long getUserInfoFromToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7).trim();
        Claims claims = tokenProvider.parseJwt(token);
        return Long.parseLong(claims.getSubject());
    }
}
