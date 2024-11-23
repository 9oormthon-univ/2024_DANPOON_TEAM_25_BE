package _oormthon.univ.flakeide.backend.global.util.user.snowPine;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserTypeSnowPine {
    private final UserRepository userRepository;

    public UserTypeSnowPine(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("@annotation(AccessSnowPine)")
    public void checkSnowPineUserType(JoinPoint joinPoint) {
        long snowPineId = (long) joinPoint.getArgs()[0];
        User user = getUser(snowPineId);
        if (!user.getUserType().equals(UserType.SNOW_PINE)) {
            throw new CustomException("snowPine이 아닙니다.", 401, 1002);
        }
    }

    private User getUser(Long snowPineId) {
        return userRepository.findById(snowPineId).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
    }
}
