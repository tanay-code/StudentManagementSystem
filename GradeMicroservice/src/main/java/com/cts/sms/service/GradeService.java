package com.cts.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.sms.entity.Grade;
import com.cts.sms.repository.GradeRepository;


@Service
public class GradeService {
	
	@Autowired
	GradeRepository gradeRepository;
	
	public String addStudentGrade(Grade grade) {
		Grade obj = gradeRepository.save(grade);
		if (obj != null)
			return "Student Grade Saved Successfully";
		else
			return "Failed To Save Student Grade";
	}
	

	public List<Grade> getAll() {
		return gradeRepository.findAll();
	}
	
	public List<Grade> getGradesByCourseIds(List<Integer> courseIds) {
        return gradeRepository.findByCourseIdIn(courseIds);
    }

}
