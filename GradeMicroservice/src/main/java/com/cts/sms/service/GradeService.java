package com.cts.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public String updateStudentGrade(Grade grade) {
        Optional<Grade> existingGrade = gradeRepository.findById(grade.getId());
        if (existingGrade.isPresent()) {
            Grade gradeToUpdate = existingGrade.get();
            gradeToUpdate.setStudentId(grade.getStudentId());
            gradeToUpdate.setCourseId(grade.getCourseId());
            gradeToUpdate.setMarks(grade.getMarks());
            gradeRepository.save(gradeToUpdate);
            return "Grade updated successfully";
        } else {
            return "Grade not found";
        }
    }
	

	public List<Grade> getAll() {
		return gradeRepository.findAll();
	}
	
	public List<Grade> getGradesByCourseIds(List<Integer> courseIds) {
        return gradeRepository.findByCourseIdIn(courseIds);
    }

	public Grade getStudentMarks(int studentId, int courseId) {
        return gradeRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }
}
