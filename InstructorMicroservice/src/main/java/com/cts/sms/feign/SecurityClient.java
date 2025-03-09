package com.cts.sms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.sms.entity.Instructor;


@FeignClient(name = "SECURITY-SERVICE", url = "http://localhost:8084")
public interface SecurityClient {

	@DeleteMapping("auth/deleteByUserId/{userId}")
    String deleteByUserId(@PathVariable("userId") int userId);
	
	@PutMapping("auth/updateByUserId/{userId}")
	String updateInstructorByUserId(@PathVariable int userId, @RequestBody Instructor instructorDetails);
}
