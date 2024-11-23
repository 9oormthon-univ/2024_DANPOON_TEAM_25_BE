package _oormthon.univ.flakeide.backend.auth.api.dto;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import lombok.Builder;

@Builder
public record UserResDto(
    String name,
    String email,
    UserType userType
) {
    public static UserResDto from(User user) {
        return UserResDto.builder()
            .name(user.getName())
            .email(user.getEmail())
            .userType(user.getUserType())
            .build();
    }

}
