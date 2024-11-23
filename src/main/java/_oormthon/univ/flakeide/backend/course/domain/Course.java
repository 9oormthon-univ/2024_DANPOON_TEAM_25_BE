package _oormthon.univ.flakeide.backend.course.domain;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "snowflakeCount")
    private int snowflakeCount;

    @Column(name = "max_snowflakes")
    private int maxSnowflakes;

    @Column(name = "invite_code")
    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User snowPine;

    @Builder
    public Course(String title, String description, int snowflakeCount, int maxSnowflakes, String inviteCode, User snowPine) {
        this.title = title;
        this.description = description;
        this.snowflakeCount = snowflakeCount;
        this.maxSnowflakes = maxSnowflakes;
        this.inviteCode = inviteCode;
        this.snowPine = snowPine;
    }

    private void increaseSnowflake(int snowflakeCount) {
        this.snowflakeCount++;
    }
}
