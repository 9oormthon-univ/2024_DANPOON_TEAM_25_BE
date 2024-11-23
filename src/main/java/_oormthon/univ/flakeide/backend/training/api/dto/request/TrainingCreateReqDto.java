package _oormthon.univ.flakeide.backend.training.api.dto.request;

import _oormthon.univ.flakeide.backend.training.domain.TrainingStatus;
import java.time.LocalDateTime;

public record TrainingCreateReqDto(
    String title,
    String description,
    String comment,
    TrainingStatus trainingStatus,
    LocalDateTime dueDate
) {
}
