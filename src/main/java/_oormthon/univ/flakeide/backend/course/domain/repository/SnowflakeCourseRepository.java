package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.SnowflakeCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SnowflakeCourseRepository extends JpaRepository<SnowflakeCourse, Long> {
    @Query("SELECT sc.course FROM SnowflakeCourse sc "
        + "JOIN fetch sc.course c "
        + "WHERE sc.snowflake.id = :snowflakeId")
    List<Course> findAllBySnowflake(Long snowflakeId);

}
