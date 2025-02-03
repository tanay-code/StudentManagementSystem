package com.cts.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.sms.entity.Student;
import com.cts.sms.exceptions.StudentNotFoundException;
import com.cts.sms.repository.StudentRepository;
import com.cts.sms.service.StudentServiceImpl;

public class StudentMicroserviceApplicationTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1);
        student.setName("John Doe");
    }

    @Test
    public void testAddStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        String result = studentService.addStudent(student);
        assertEquals("Student Saved Successfully", result);
    }

    @Test
    public void testGetStudent() throws StudentNotFoundException {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Student result = studentService.getStudent(1);
        assertEquals(student, result);
    }

    @Test
    public void testGetStudentNotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudent(1);
        });
    }
}
