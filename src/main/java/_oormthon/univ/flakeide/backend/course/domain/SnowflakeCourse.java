package _oormthon.univ.flakeide.backend.course.domain;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class SnowflakeCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User snowflake;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
