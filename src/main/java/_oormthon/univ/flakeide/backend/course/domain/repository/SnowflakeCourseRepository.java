package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.SnowflakeCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SnowflakeCourseRepository extends JpaRepository<SnowflakeCourse, Long> {
    @Query("SELECT sc.course FROM SnowflakeCourse sc WHERE sc.user.id = :userId")
    List<Course> findAllBySnowflake(@Param("userId") long userId);

    @Query("SELECT sc.user FROM SnowflakeCourse sc "
        + "WHERE sc.course.id = :courseId")
    List<User> findAllUserBySnowflake(@Param("courseId") long courseId);

}
