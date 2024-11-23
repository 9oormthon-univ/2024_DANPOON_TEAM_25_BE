package _oormthon.univ.flakeide.backend.global.util.user.snowPine;

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
public class UserTypeSnowPineId {
    private final UserRepository userRepository;

    public UserTypeSnowPineId(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("@annotation(AccessSnowPineId)")
    public void checkSnowPineIdUserType(JoinPoint joinPoint) {
        Long snowPineId = (Long) joinPoint.getArgs()[0];
        User user = getUser(snowPineId);
        if (!user.getUserType().equals(UserType.SNOW_PINE)) {
            throw new CustomException("snowPine이 아닙니다.", 401, 1002);
        }
    }

    private User getUser(Long snowPineId) {
        return userRepository.findById(snowPineId).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
    }
}
