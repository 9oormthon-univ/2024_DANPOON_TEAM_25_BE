package _oormthon.univ.flakeide.backend.training.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.training.domain.Training;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByCourse(Course course);


}
