package com.cts.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sms.entity.Grade;
import com.cts.sms.service.GradeService;



@RestController
@RequestMapping("/grade")
public class GradeController {
	
	@Autowired
	GradeService gradeService;

	@PostMapping("/addStudentGrade") // http://localhost:8080/students/addStudent
    public String addStudentGrade(@RequestBody Grade grade) {
		System.out.println("Came here");
        return gradeService.addStudentGrade(grade);
    }
	@GetMapping("/getAll")
	public List<Grade> getAll() {
		return gradeService.getAll();
	}
	
    @GetMapping("/filterByCourseIds")
    public List<Grade> getGradesByCourseIds(@RequestParam List<Integer> courseIds) {
        return gradeService.getGradesByCourseIds(courseIds);
    }
}
