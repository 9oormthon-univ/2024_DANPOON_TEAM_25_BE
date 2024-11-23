package _oormthon.univ.flakeide.backend.auth.service;

import _oormthon.univ.flakeide.backend.auth.api.dto.UserResDto;
import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.global.util.UserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserTokenService userTokenService;
    private final UserRepository userRepository;

    public UserService(UserTokenService userTokenService, UserRepository userRepository) {
        this.userTokenService = userTokenService;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResDto signUpSnowflake(String authorizationHeader) {
        long id = userTokenService.getUserInfoFromToken(authorizationHeader);
        User user = userRepository.findById(id).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
        user.updateUserType(UserType.SNOWFLAKE);
        return UserResDto.from(user);
    }

    @Transactional
    public UserResDto signUpSnowPine(String authorizationHeader) {
        long id = userTokenService.getUserInfoFromToken(authorizationHeader);
        User user = userRepository.findById(id).orElseThrow(()->new CustomException("사용자를 찾을 수 없습니다.", 404, 1001));
        user.updateUserType(UserType.SNOW_PINE);
        return UserResDto.from(user);
    }

}
