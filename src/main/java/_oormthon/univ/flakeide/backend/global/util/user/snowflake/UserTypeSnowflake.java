package _oormthon.univ.flakeide.backend.global.util.user.snowflake;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserTypeSnowflake {
    private final UserRepository userRepository;
    private final UserTokenService userTokenService;

    public UserTypeSnowflake(UserRepository userRepository, UserTokenService userTokenService) {
        this.userRepository = userRepository;
        this.userTokenService = userTokenService;
    }

    @Before("@annotation(AccessSnowflake)")
    public void checkSnowflakeUserType(JoinPoint joinPoint) {
        String token = Arrays.toString(joinPoint.getArgs());
        User user = getUser(userTokenService.getUserInfoFromToken(token));
        if (!user.getUserType().equals(UserType.SNOWFLAKE)) {
            throw new CustomException("snowflake가 아닙니다.", 401, 1002);
        }
    }

    private User getUser(Long snowflakeId) {
        return userRepository.findById(snowflakeId).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
    }
}
