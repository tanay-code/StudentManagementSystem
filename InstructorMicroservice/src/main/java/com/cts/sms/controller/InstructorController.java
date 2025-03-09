package com.cts.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sms.entity.Instructor;
import com.cts.sms.service.InstructorService;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/addInstructor")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.createInstructor(instructor);
    }

    @GetMapping("getById/{id}")
    public Instructor getInstructorById(@PathVariable int id) {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("/getAll")
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @PutMapping("/update/{id}")
    public Instructor updateInstructor(@PathVariable int id, @RequestBody Instructor instructor) {
        return instructorService.updateInstructor(id, instructor);
    }

    @DeleteMapping("deleteById/{id}")
    public String deleteInstructor(@PathVariable int id) {
        instructorService.deleteInstructor(id);
        return "Instructor deleted successfully!";
    }

    @PutMapping("/assign/{instructorId}/{courseId}")
    public Instructor assignInstructorToCourse(@PathVariable int instructorId, @PathVariable int courseId) {
        return instructorService.assignInstructorToCourse(instructorId, courseId);
    }

    @GetMapping("/{instructorId}/courses")
    public List<Integer> getAssignedCourses(@PathVariable int instructorId) {
        return instructorService.getAssignedCourses(instructorId); // Fetch assigned courses
    }
    @GetMapping("byUserId/{userId}/courses")
    public List<Integer> getAssignedCoursesByUserId(@PathVariable int userId) {
        return instructorService.getAssignedCoursesByUserId(userId); // Fetch assigned courses
    }
    
    @GetMapping("/{instructorId}/isAssigned/{courseId}")
    public boolean isAssignedCourse(@PathVariable int instructorId, @PathVariable int courseId) {
    	return instructorService.isAssignedCourse(instructorId, courseId);
    }
    
    @GetMapping("/doesExists/{id}")
    public boolean doesInstructorExist(@PathVariable int id) {
    	return instructorService.doesInstructorExist(id);
    };
    
    @PutMapping("/{instructorId}/removeCourse/{courseId}")
    public ResponseEntity<Void> removeCourseFromInstructor(@PathVariable int instructorId, @PathVariable int courseId) {
        instructorService.removeCourseFromInstructor(instructorId, courseId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/byCourse/{courseId}")
    public List<Instructor> getInstructorsByCourseId(@PathVariable int courseId) {
        return instructorService.getInstructorsByCourseId(courseId);
    }

}
