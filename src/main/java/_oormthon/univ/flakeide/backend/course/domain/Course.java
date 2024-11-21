package _oormthon.univ.flakeide.backend.course.domain;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MAX_SNOWFLAKES")
    private int maxSnowflakes;

    @Column(name = "INVITE_CODE")
    private int inviteCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User snowPine;

    @Builder
    public Course(String title, String description, int maxSnowflakes) {
        this.title = title;
        this.description = description;
        this.maxSnowflakes = maxSnowflakes;
    }
}
