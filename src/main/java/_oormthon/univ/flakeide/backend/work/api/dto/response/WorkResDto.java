package _oormthon.univ.flakeide.backend.work.api.dto.response;

import _oormthon.univ.flakeide.backend.work.domain.Work;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record WorkResDto(
    String title,
    String description,
    LocalDateTime dueTime
) {
    public static WorkResDto from(Work work) {
        return WorkResDto.builder()
            .title(work.getTitle())
            .description(work.getDescription())
            .dueTime(work.getDueDate())
            .build();
    }

}
