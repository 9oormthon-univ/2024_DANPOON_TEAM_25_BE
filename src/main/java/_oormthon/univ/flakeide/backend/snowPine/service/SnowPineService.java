package _oormthon.univ.flakeide.backend.snowPine.service;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.course.api.dto.CourseResDto;
import _oormthon.univ.flakeide.backend.course.api.dto.ListCourseResDto;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SnowPineService {

    private final UserRepository snowPineRepository;
    private final CourseRepository courseRepository;

    public SnowPineService(UserRepository snowPineRepository, CourseRepository courseRepository) {
        this.snowPineRepository = snowPineRepository;
        this.courseRepository = courseRepository;
    }

    public ListCourseResDto getCourseOfSnowPine(long snowPineId) {
        User snowPine = snowPineRepository.findById(snowPineId).orElseThrow();
        return ListCourseResDto.builder()
            .courseList(getCourseResDtoList(snowPine))
            .build();
    }

    private List<CourseResDto> getCourseResDtoList(User snowPine) {
        return courseRepository.findAllBySnowPine(snowPine).stream().map(
            CourseResDto::from).toList();
    }
}
