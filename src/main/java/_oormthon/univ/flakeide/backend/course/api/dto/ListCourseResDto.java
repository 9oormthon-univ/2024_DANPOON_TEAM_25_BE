package _oormthon.univ.flakeide.backend.course.api.dto;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import java.util.List;
import lombok.Builder;

@Builder
public record ListCourseResDto(
    List<CourseResDto> courseList
) {
}
