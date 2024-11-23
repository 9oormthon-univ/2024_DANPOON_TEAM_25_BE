package _oormthon.univ.flakeide.backend.global.util.user.snowPine;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

public class UserTypeSnowPineToken {
    private final UserTokenService userTokenService;
    private final UserRepository userRepository;

    public UserTypeSnowPineToken(UserTokenService userTokenService, UserRepository userRepository) {
        this.userTokenService = userTokenService;
        this.userRepository = userRepository;
    }

    @Before("@annotation(AccessSnowPineToken)")
    public void checkSnowflakeUserType(JoinPoint joinPoint) {
        String token = Arrays.toString(joinPoint.getArgs());
        System.out.println("출력: "+ joinPoint.getArgs()[0]);
        User user = getUser(userTokenService.getUserInfoFromToken(token));
        if (!user.getUserType().equals(UserType.SNOWFLAKE)) {
            throw new CustomException("snowfPine이 아닙니다.", 401, 1002);
        }
    }
    private User getUser(Long snowPineId) {
        return userRepository.findById(snowPineId).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
    }

}
