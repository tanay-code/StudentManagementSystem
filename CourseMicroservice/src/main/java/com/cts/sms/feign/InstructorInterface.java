package com.cts.sms.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cts.sms.dto.Instructor;

@FeignClient(name = "INSTRUCTORSERVICE",url="http://localhost:8082")
public interface InstructorInterface {

	@PutMapping("/instructors/{instructorId}/removeCourse/{courseId}")
    void removeCourseFromInstructor(@PathVariable("instructorId") int instructorId, @PathVariable("courseId") int courseId);

    @GetMapping("/instructors/byCourse/{courseId}")
    List<Instructor> getInstructorsByCourseId(@PathVariable("courseId") int courseId);
}
