package _oormthon.univ.flakeide.backend.course.service;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import _oormthon.univ.flakeide.backend.auth.domain.UserType;
import _oormthon.univ.flakeide.backend.auth.domain.repository.UserRepository;
import _oormthon.univ.flakeide.backend.course.api.dto.CreateCourseDto;
import _oormthon.univ.flakeide.backend.course.domain.Course;
import _oormthon.univ.flakeide.backend.course.domain.repository.CourseRepository;
import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InviteCodeService inviteCodeService;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse_ShouldThrowException_WhenUserNotFound() {
        // given

        CreateCourseDto dto = new CreateCourseDto("test", "Course Title", 10, 1L);
        when(userRepository.findById(dto.snowpine_id())).thenReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> courseService.createCourse(dto));

        // then
        assertEquals("유저를 찾을 수 없습니다.", exception.getMessage());
        assertEquals(404, exception.getStatusCode());
        assertEquals(1001, exception.getErrorCode());
        verify(userRepository, times(1)).findById(dto.snowpine_id());
    }

    @Test
    void createCourse_ShouldThrowException_WhenUserIsNotTeacher() {
        // given
        CreateCourseDto dto = new CreateCourseDto("test", "Course Title", 10, 1L);
        User user = new User(1L, UserType.SNOWFLAKE, "test", "test");
        when(userRepository.findById(dto.snowpine_id())).thenReturn(Optional.of(user));

        // when
        CustomException exception = assertThrows(CustomException.class, () -> courseService.createCourse(dto));

        // then
        assertEquals("선생님이 아닙니다.", exception.getMessage());
        assertEquals(401, exception.getStatusCode());
        assertEquals(2002, exception.getErrorCode());
        verify(userRepository, times(1)).findById(dto.snowpine_id());
    }

    @Test
    void createCourse_ShouldThrowException_WhenCourseSaveFails() {
        // given
        CreateCourseDto dto = new CreateCourseDto("test", "Course Title", 10, 1L);
        User user = new User(1L, UserType.SNOW_PINE, "test", "test");
        String inviteCode = "ABC123";

        when(userRepository.findById(dto.snowpine_id())).thenReturn(Optional.of(user));
        when(inviteCodeService.generateRandomString()).thenReturn(inviteCode);
        doThrow(new RuntimeException("DB Error")).when(courseRepository).save(any(Course.class));

        // when
        CustomException exception = assertThrows(CustomException.class, () -> courseService.createCourse(dto));

        // then
        assertEquals("수업 생성도중 오류가 생겼습니다.", exception.getMessage());
        assertEquals(500, exception.getStatusCode());
        assertEquals(2003, exception.getErrorCode());
        verify(userRepository, times(1)).findById(dto.snowpine_id());
        verify(inviteCodeService, times(1)).generateRandomString();
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void createCourse_ShouldCreateCourseSuccessfully() {
        // given
        CreateCourseDto dto = new CreateCourseDto("title", "description", 10, 1L);
        User user = new User(1L, UserType.SNOW_PINE, "test", "test");
        String inviteCode = "ABC123";

        when(userRepository.findById(dto.snowpine_id())).thenReturn(Optional.of(user));
        when(inviteCodeService.generateRandomString()).thenReturn(inviteCode);
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Course createdCourse = courseService.createCourse(dto);

        // then
        assertNotNull(createdCourse);
        assertEquals(dto.title(), createdCourse.getTitle());
        assertEquals(dto.description(), createdCourse.getDescription());
        assertEquals(dto.max_students(), createdCourse.getMaxSnowflakes());
        assertEquals(inviteCode, createdCourse.getInviteCode());
        assertEquals(user, createdCourse.getSnowPine());

        verify(userRepository, times(1)).findById(dto.snowpine_id());
        verify(inviteCodeService, times(1)).generateRandomString();
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}

