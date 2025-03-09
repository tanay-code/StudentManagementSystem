package com.cts.sms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sms.entity.Course;
import com.cts.sms.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
	
	@Autowired
    private CourseService courseService;

    @PostMapping("/addCourse")  // http://localhost:8081/courses/addCourse
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    @GetMapping("/getById/{id}") // http://localhost:8081/courses/getById/1
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(item -> ResponseEntity.ok(item))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PutMapping("/updateCourse/{id}") // http://localhost:8081/courses/updateCourse/1
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }


    @DeleteMapping("/deleteCourse/{id}") // http://localhost:8081/courses/deleteCourse/1
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    
    @PostMapping("/enroll/{courseId}/{studentId}")
    public String enrollStudent(@PathVariable int courseId, @PathVariable int studentId) {
        return courseService.enrollStudent(courseId, studentId);
    }

    @GetMapping("/enrolled/{studentId}")
    public ResponseEntity<List<Course>> getCoursesEnrolledByStudent(@PathVariable int studentId) {
        List<Course> courses = courseService.getCoursesEnrolledByStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/getAll") // Fixed to match Feign Client
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/exists/{courseId}")
    public boolean doesCourseExist(@PathVariable int courseId) {
        return courseService.doesCourseExists(courseId);
    }
    
    @GetMapping("/{courseId}/students")
    public List<Integer> getEnrolledStudents(@PathVariable int courseId) {
        return courseService.getEnrolledStudents(courseId);
    }
    
    @GetMapping("/getCourseNames")
    public List<String> getCourseNames(@RequestParam List<Integer> courseIds) {
        return courseService.getCourseNames(courseIds);
    }
    
	@GetMapping("/{courseId}/isStudentEnrolled/{studentId}")
	boolean isStudentEnrolled(@PathVariable int courseId, @PathVariable int studentId) {
		return courseService.isStudentEnrolled(courseId,studentId);
	};
    @PutMapping("/unenrollStudent/{studentId}")
    public ResponseEntity<String> unenrollStudent(@PathVariable int studentId) {
        courseService.unenrollStudentFromAllCourses(studentId);
        return ResponseEntity.ok("Student unenrolled from all courses successfully");
    }
	

    
}
