package _oormthon.univ.flakeide.backend.course.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateCourseSpecDto(
        Long course_id,
        Long student_id,
        List<String> spec
) {
    public CreateCourseSpecDto withCourseId(Long newCourseId) {
        return new CreateCourseSpecDto(newCourseId, this.student_id, this.spec);
    }
}
