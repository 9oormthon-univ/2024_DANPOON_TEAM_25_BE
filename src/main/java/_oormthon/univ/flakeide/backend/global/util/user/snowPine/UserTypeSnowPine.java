package _oormthon.univ.flakeide.backend.global.util.user.snowPine;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
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
            throw new IllegalArgumentException("snowPine이 아닙니다.");
        }
    }

    private User getUser(Long snowPineId) {
        return userRepository.findById(snowPineId).orElseThrow();
    }
}
