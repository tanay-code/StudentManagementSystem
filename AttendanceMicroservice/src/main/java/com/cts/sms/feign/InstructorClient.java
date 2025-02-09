package com.cts.sms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INSTRUCTORSERVICE", url = "http://localhost:8082/instructors")
public interface InstructorClient {
    @GetMapping("/{instructorId}/isAssigned/{courseId}")
    boolean isInstructorAssigned(@PathVariable int instructorId, @PathVariable int courseId);
    
    @GetMapping("/doesExists/{id}")
	boolean doesInstructorExist(@PathVariable int id);
}
