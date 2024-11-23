package _oormthon.univ.flakeide.backend.training.api.dto.request;

import java.time.LocalDateTime;

public record TrainingCreateReqDto(
    String title,
    String description,
    LocalDateTime dueDate
) {
}
