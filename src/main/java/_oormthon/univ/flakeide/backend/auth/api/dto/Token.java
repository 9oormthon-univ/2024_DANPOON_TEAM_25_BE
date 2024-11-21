package _oormthon.univ.flakeide.backend.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Token {
    @JsonProperty("access_token") // JSON으로 직렬화 or 역직렬화할때 사용할 필드 이름을 지정
    private String accessToken;
}