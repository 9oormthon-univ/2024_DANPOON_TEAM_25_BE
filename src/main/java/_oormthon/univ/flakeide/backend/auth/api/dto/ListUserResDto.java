package _oormthon.univ.flakeide.backend.auth.api.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ListUserResDto (
    List<UserResDto> userResDtoList
) {
}

