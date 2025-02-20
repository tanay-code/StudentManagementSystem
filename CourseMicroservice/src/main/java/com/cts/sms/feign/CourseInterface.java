package com.cts.sms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="STUDENTMICROSERVICE",url="http://localhost:8080/students")
public interface CourseInterface {

	@GetMapping("/doesExist/{id}")
	boolean doesStudentExist(@PathVariable int id);
}
