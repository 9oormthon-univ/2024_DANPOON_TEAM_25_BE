package _oormthon.univ.flakeide.backend.training.domain;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.training.api.dto.request.TrainingCreateReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TrainingStatus trainingStatus;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public Training(String title, String description, String comment, TrainingStatus trainingStatus, LocalDateTime dueDate, Course course) {
        this.title = title;
        this.description = description;
        this.comment = comment;
        this.trainingStatus = trainingStatus;
        this.dueDate = dueDate;
        this.course = course;
    }

    public static Training createTraining(Course course, TrainingCreateReqDto trainingCreateReqDto) {
        return Training.builder()
            .title(trainingCreateReqDto.title())
            .description(trainingCreateReqDto.description())
            .comment(trainingCreateReqDto.comment())
            .trainingStatus(TrainingStatus.NOT_SUBMITTED)
            .dueDate(trainingCreateReqDto.dueDate())
            .course(course)
            .build();
    }
}
