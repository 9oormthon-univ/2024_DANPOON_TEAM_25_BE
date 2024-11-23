package _oormthon.univ.flakeide.backend.training.api.dto.response;

import _oormthon.univ.flakeide.backend.training.domain.Training;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TrainingResDto(
    String title,
    String description,
    LocalDateTime dueDate
) {
    public static TrainingResDto from(Training training) {
        return TrainingResDto.builder()
            .title(training.getTitle())
            .description(training.getDescription())
            .dueDate(training.getDueDate())
            .build();
    }

}
