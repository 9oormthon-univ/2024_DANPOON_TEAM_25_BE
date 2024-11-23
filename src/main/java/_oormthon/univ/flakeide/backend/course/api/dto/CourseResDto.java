package _oormthon.univ.flakeide.backend.course.api.dto;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import lombok.Builder;

@Builder
public record CourseResDto(
        String title,
        String description,
        int maxSnowflakes,
        int snowflakeCount
) {
    public static CourseResDto from(Course course) {
        return CourseResDto.builder()
            .title(course.getTitle())
            .description(course.getDescription())
            .maxSnowflakes(course.getMaxSnowflakes())
            .snowflakeCount(course.getSnowflakeCount())
            .build();
    }

}
