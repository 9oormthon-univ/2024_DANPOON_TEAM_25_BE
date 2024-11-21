package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // 수업 상세조회
    public Course getCourseDetail(long courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }

}
