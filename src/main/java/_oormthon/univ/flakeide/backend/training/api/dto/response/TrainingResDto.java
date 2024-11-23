package _oormthon.univ.flakeide.backend.training.api.dto.response;

import _oormthon.univ.flakeide.backend.training.domain.Training;
import _oormthon.univ.flakeide.backend.training.domain.TrainingStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TrainingResDto(
    String title,
    String description,
    String comment,
    TrainingStatus trainingStatus,
    LocalDateTime dueDate
) {
    public static TrainingResDto from(Training training) {
        return TrainingResDto.builder()
            .title(training.getTitle())
            .description(training.getDescription())
            .comment(training.getComment())
            .trainingStatus(training.getTrainingStatus())
            .dueDate(training.getDueDate())
            .build();
    }

}
