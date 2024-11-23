package _oormthon.univ.flakeide.backend.work.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record WorkCreateReqDto(
    @JsonProperty(namespace = "title")
    String title,

    @JsonProperty(namespace = "description")
    String description,

    @JsonProperty(namespace = "dueDate")
    LocalDateTime dueDate
) {

}
