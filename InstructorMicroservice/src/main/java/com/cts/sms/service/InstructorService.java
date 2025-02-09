package com.cts.sms.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.cts.sms.entity.Instructor;

public interface InstructorService {
	Instructor createInstructor(Instructor instructor);

	Instructor getInstructorById(int id);

	List<Instructor> getAllInstructors();

	Instructor updateInstructor(int id, Instructor instructor);

	void deleteInstructor(int id);

    Instructor assignInstructorToCourse(int instructorId, int courseId);
    List<Integer> getAssignedCourses(int instructorId);
    
    boolean isAssignedCourse(int instructorId, int courseId);
    
    boolean doesInstructorExist(int id);
}
