package _oormthon.univ.flakeide.backend.course.api.dto;

import lombok.Builder;

@Builder
public record CreateCourseDto(
        String title,
        String description,
        Integer max_students,
        Long snowpine_id
) {
}
