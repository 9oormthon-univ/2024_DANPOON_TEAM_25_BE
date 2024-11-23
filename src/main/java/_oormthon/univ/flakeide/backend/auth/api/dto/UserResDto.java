package _oormthon.univ.flakeide.backend.auth.api.dto;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import lombok.Builder;

@Builder
public record UserResDto(
    String name,
    String email
) {
    public static UserResDto from(User user) {
        return UserResDto.builder()
            .name(user.getName())
            .email(user.getEmail())
            .build();
    }

}
