package com.cts.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sms.dto.Course;
import com.cts.sms.entity.Student;
import com.cts.sms.exceptions.StudentNotFoundException;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {
	
    private final StudentService studentService;
    private final CourseClient courseClient;

    @Autowired
    public StudentController(StudentService studentService, CourseClient courseClient) {
        this.studentService = studentService;
        this.courseClient = courseClient;
    }


    @PostMapping("/addStudent") // http://localhost:8080/students/addStudent
    public String addStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }
    @GetMapping("/getAll") // http://localhost:8080/students/getAll
	public List<Student> getAllStudent() {
		return studentService.getAllStudent();
	}

    @GetMapping("/getById/{id}") // http://localhost:8080/students/getById/1
    public Student getStudent(@PathVariable("id") int studentId) {
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            throw new StudentNotFoundException("No such student id");
        }
        return student;
    }
    
    @PutMapping("/update") // http://localhost:8080/students/update
	public Student updateStudent(@RequestBody Student student) {
		return studentService.updateStudent(student);
	}
    
    @DeleteMapping("/deleteById/{id}") // http://localhost:8080/students/deleteById
	public String deleteStudent(@PathVariable("id") int studentId) {
		return studentService.deleteStudent(studentId);
	}
    
    //called from course-microservice
    @GetMapping("doesExist/{id}") // http://localhost:8080/students/doesExist/1
	public boolean doesStudentExist(@PathVariable int id) {
    	return studentService.existsById(id);
    }
    
    //calling course-microservice
    @GetMapping("/courses/all") // http://localhost:8080/students/courses/all
    public List<Course> getAllCourses() { // Updated return type
        return courseClient.getAllCourses();
    }

    @GetMapping("/{id}/courses")
    public List<Course> getEnrolledCourses(@PathVariable int id) { // Updated return type
        return courseClient.getCoursesByStudent(id);
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public String enrollStudent(@PathVariable int studentId, @PathVariable int courseId) {
        return courseClient.enrollStudent(courseId, studentId); // Fixed parameter order
    }
}
