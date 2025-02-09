package com.cts.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.sms.entity.Course;
import com.cts.sms.exceptions.ResourceNotFoundException;
import com.cts.sms.feign.CourseInterface;
import com.cts.sms.repository.CourseRepository;
import com.cts.sms.service.CourseServiceImpl;

@SpringBootTest
class CourseMicroserviceApplicationTests {

	@Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseInterface courseInterface;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setId(1);
        course.setCourseName("Math");
        course.setDescription("Basic Math Course");
        course.setDuration("30 weeks");
        course.setStudentsEnrolled(new ArrayList<>());
    }

    @Test
    public void testAddCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertNotNull(result);
        assertEquals("Math", result.getCourseName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testGetCourseById_Success() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1);

        assertTrue(result.isPresent());
        assertEquals("Math", result.get().getCourseName());
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCourseById_NotFound() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.getCourseById(1);

        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    public void testEnrollStudent_Success() {
        when(courseInterface.doesStudentExist(1)).thenReturn(true);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        String result = courseService.enrollStudent(1, 1);

        assertEquals("Student 1 enrolled successfully!", result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testEnrollStudent_StudentNotExist() {
        when(courseInterface.doesStudentExist(1)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.enrollStudent(1, 1);
        });

        assertEquals("Student with ID 1 does not exist!", exception.getMessage());
    }

    @Test
    public void testEnrollStudent_AlreadyEnrolled() {
        when(courseInterface.doesStudentExist(1)).thenReturn(true);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        course.getStudentsEnrolled().add(1);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseService.enrollStudent(1, 1);
        });

        assertEquals("Student is already enrolled in this course!", exception.getMessage());
    }

}
