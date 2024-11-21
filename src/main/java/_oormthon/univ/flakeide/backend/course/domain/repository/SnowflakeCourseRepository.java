package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.course.domain.SnowflakeCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnowflakeCourseRepository extends JpaRepository<SnowflakeCourse, Long> {
    List<SnowflakeCourse> findAllBySnowflake(User user);

}
