package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
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
    public CourseResDto getCourseDetail(long courseId) {
        return CourseResDto.from(courseRepository.findById(courseId).orElseThrow());
    }
}
