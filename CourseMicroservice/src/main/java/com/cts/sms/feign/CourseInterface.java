package com.cts.sms.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="STUDENTMICROSERVICE",url="http://localhost:8080/students")
public interface CourseInterface {

	@GetMapping("/doesExist/{id}")
	boolean doesStudentExist(@PathVariable int id);
		
}
