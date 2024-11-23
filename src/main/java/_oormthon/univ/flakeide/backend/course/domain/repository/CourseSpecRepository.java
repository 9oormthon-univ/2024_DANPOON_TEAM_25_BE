package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.course.api.dto.CourseSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSpecRepository extends JpaRepository<CourseSpec, Long> {
}
