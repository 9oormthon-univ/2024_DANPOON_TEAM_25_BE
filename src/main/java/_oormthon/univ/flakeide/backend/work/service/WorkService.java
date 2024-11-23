package _oormthon.univ.flakeide.backend.work.service;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.work.api.dto.request.WorkCreateReqDto;
import _oormthon.univ.flakeide.backend.work.api.dto.response.ListWorkResDto;
import _oormthon.univ.flakeide.backend.work.api.dto.response.WorkResDto;
import _oormthon.univ.flakeide.backend.work.domain.Work;
import _oormthon.univ.flakeide.backend.work.domain.repository.WorkRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WorkService {

    private final WorkRepository workRepository;
    private final CourseRepository courseRepository;

    public WorkService(WorkRepository workRepository, CourseRepository courseRepository) {
        this.workRepository = workRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void createWork(long courseId, WorkCreateReqDto workCreateReqDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        course.increaseWorkCount();
        Work work = Work.createWork(course, workCreateReqDto);
        workRepository.save(work);
    }

    public ListWorkResDto getAllWorkOfCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        return ListWorkResDto.builder()
            .workResDtoList(getWorkResDtoList(course))
            .build();
    }

    public WorkResDto getWorkDetail(long courseId, long workId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        Work work = workRepository.findById(workId).orElseThrow(()-> new CustomException("과제를 찾을 수 업습니다.", 404, 5001));
        if (!(Objects.equals(course.getId(), work.getCourse().getId()))) {
            throw new CustomException("해당 수업의 과제가 아닙니다.", 400, 5002);
        }
        return WorkResDto.from(work);
    }

    private List<WorkResDto> getWorkResDtoList(Course course) {
        return workRepository.findAllByCourse(course).stream()
            .map(WorkResDto::from)
            .toList();
    }
}
