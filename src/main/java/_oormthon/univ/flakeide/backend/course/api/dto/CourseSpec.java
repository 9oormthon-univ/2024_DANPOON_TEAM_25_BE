package _oormthon.univ.flakeide.backend.course.api.dto;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CourseSpec {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User student;

    @JsonProperty("spec")
    @Column
    String spec;


    @Builder
    public CourseSpec(Course course, User student, String spec) {
        this.course = course;
        this.student = student;
        this.spec = spec;
    }
}
