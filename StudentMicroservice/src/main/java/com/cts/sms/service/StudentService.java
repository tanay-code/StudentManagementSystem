package com.cts.sms.service;

import java.util.List;

import com.cts.sms.dto.Course;
import com.cts.sms.entity.Student;
import com.cts.sms.exceptions.StudentNotFoundException;

public interface StudentService {
	public abstract String addStudent(Student student);
	
	public abstract List<Student> getAllStudent();
	
	public abstract Student getStudent(int stdId) throws StudentNotFoundException;
	
	public abstract Student updateStudent(int id,Student student);
	
	public abstract String deleteStudent(int stdId);
	
	boolean existsById(int id);
	
	List<Course> getCoursesEnrolledByStudent(int studentId);
	
//	List<Student> getStudentMarksGreaterThan(int marks);

}
