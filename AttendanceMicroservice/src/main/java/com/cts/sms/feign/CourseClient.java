package com.cts.sms.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COURSESERVICE", url = "http://localhost:8081")
public interface CourseClient {
	@GetMapping("/courses/exists/{courseId}")
	boolean doesCourseExist(@PathVariable("courseId") int courseId);

	@GetMapping("/courses/{courseId}/isStudentEnrolled/{studentId}")
	boolean isStudentEnrolled(@PathVariable int courseId, @PathVariable int studentId);

	@GetMapping("/courses/{courseId}/students")
	List<Integer> getEnrolledStudents(@PathVariable int courseId);
}
