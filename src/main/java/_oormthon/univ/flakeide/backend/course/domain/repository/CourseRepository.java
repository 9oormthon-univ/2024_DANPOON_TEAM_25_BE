package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllBySnowPine(User user);
}
