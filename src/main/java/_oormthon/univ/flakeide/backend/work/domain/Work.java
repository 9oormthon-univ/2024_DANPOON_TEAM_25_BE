package _oormthon.univ.flakeide.backend.work.domain;

import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.training.domain.TrainingStatus;
import _oormthon.univ.flakeide.backend.work.api.dto.request.WorkCreateReqDto;
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

@Entity
@Getter
@NoArgsConstructor
public class Work {

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
    private WorkStatus workStatus;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public Work(String title, String description, String comment,
        WorkStatus workStatus, LocalDateTime dueDate, Course course) {
        this.title = title;
        this.description = description;
        this.comment = comment;
        this.workStatus = workStatus;
        this.dueDate = dueDate;
        this.course = course;
    }


    public static Work createWork(Course course, WorkCreateReqDto workCreateReqDto) {
        return Work.builder()
            .title(workCreateReqDto.title())
            .description(workCreateReqDto.description())
            .comment(workCreateReqDto.comment())
            .workStatus(WorkStatus.NOT_SUBMITTED)
            .dueDate(workCreateReqDto.dueDate())
            .course(course)
            .build();
    }

}
