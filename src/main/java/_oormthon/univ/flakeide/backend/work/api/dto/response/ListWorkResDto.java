package _oormthon.univ.flakeide.backend.work.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ListWorkResDto(
    List<WorkResDto> workResDtoList
) {

}
