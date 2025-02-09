package com.cts.sms.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cts.sms.dto.Course;

@FeignClient(name = "COURSESERVICE", url = "http://localhost:8081")
public interface CourseClient {
    @GetMapping("/courses/enrolled/{studentId}")
    List<Course> getCoursesByStudent(@PathVariable("studentId") int studentId);
   
    @GetMapping("/courses/getAll") // Fixed endpoint to match CourseController
    List<Course> getAllCourses();
   
    @PostMapping("/courses/enroll/{courseId}/{studentId}")
    String enrollStudent(@PathVariable("courseId") int courseId, @PathVariable("studentId") int studentId);

}
