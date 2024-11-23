package _oormthon.univ.flakeide.backend.work.api.dto.request;

import _oormthon.univ.flakeide.backend.work.domain.WorkStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record WorkCreateReqDto(
    @JsonProperty(namespace = "title")
    String title,

    @JsonProperty(namespace = "description")
    String description,

    @JsonProperty(namespace = "comment")
    String comment,
    @JsonProperty(namespace = "work_status")
    WorkStatus workStatus,

    @JsonProperty(namespace = "dueDate")
    LocalDateTime dueDate
) {

}
