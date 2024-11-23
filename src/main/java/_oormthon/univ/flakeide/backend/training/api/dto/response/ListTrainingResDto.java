package _oormthon.univ.flakeide.backend.training.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ListTrainingResDto(
    List<TrainingResDto> trainingResDtoList
) {

}
