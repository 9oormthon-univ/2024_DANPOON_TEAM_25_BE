package _oormthon.univ.flakeide.backend.training.service;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.training.api.dto.request.TrainingCreateReqDto;
import _oormthon.univ.flakeide.backend.training.api.dto.response.ListTrainingResDto;
import _oormthon.univ.flakeide.backend.training.api.dto.response.TrainingResDto;
import _oormthon.univ.flakeide.backend.training.domain.Training;
import _oormthon.univ.flakeide.backend.training.domain.repository.TrainingRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final CourseRepository courseRepository;

    public TrainingService(TrainingRepository trainingRepository,
        CourseRepository courseRepository) {
        this.trainingRepository = trainingRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void createTraining(long courseId, TrainingCreateReqDto trainingCreateReqDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        course.increaseTrainingCount();
        Training training = Training.createTraining(course, trainingCreateReqDto);
        trainingRepository.save(training);
    }

    public ListTrainingResDto getAllTrainingOfCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        return ListTrainingResDto.builder()
            .trainingResDtoList(getTrainingResDtoList(course))
            .build();
    }

    public TrainingResDto getTrainingDetail(long courseId, long trainingId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new CustomException("수업을 찾을 수 없습니다.", 404, 3001));
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new CustomException("실습을 찾을 수 없습니다.", 404, 4001));
        if (!(Objects.equals(course.getId(), training.getCourse().getId()))) {
            throw new CustomException("해당수업의 실습이 아닙니다.", 400, 4002);
        }
         return TrainingResDto.from(training);
    }

    private List<TrainingResDto> getTrainingResDtoList(Course course) {
        return trainingRepository.findAllByCourse(course)
            .stream()
            .map(TrainingResDto::from)
            .toList();
    }
}
