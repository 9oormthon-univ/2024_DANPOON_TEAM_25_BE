package _oormthon.univ.flakeide.backend.course.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.SnowflakeCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SnowflakeCourseRepository extends JpaRepository<SnowflakeCourse, Long> {
    @Query("SELECT sc.course FROM SnowflakeCourse sc WHERE sc.user.id = :userId")
    List<Course> findAllBySnowflake(@Param("userId") Long userId);

    @Query("SELECT sc.course FROM SnowflakeCourse sc WHERE sc.user.userType = :userType")
    List<Course> findAllByUserType(@Param("userType") UserType userType);

    @Query("SELECT c "
        + "FROM SnowflakeCourse sc "
        + "JOIN sc.course c "
        + "JOIN sc.user u "
        + "WHERE u.userType = :userType")
    List<Course> findAllByUser(@Param("userType") UserType userType);

}
