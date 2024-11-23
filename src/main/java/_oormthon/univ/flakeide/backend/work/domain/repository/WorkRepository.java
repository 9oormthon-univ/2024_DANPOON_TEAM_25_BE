package _oormthon.univ.flakeide.backend.work.domain.repository;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.work.domain.Work;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByCourse(Course course);
}
