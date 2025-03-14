package com.cts.sms.service;

import java.util.List;
import java.util.Optional;

import com.cts.sms.entity.Course;

public interface CourseService {

	Course addCourse(Course course);

	List<Course> getAllCourses();

	Optional<Course> getCourseById(Integer id);

	Course updateCourse(Integer id, Course updatedCourse);

	void deleteCourse(Integer id);

	String enrollStudent(int courseId, int studentId);

	boolean isStudentEnrolledInCourse(int courseId, int studentId);

	List<Course> getCoursesEnrolledByStudent(int studentId);

	boolean doesCourseExists(int courseId);
	
	List<Integer> getEnrolledStudents(int courseId);
	
	boolean isStudentEnrolled(int courseId, int studentId);
	
	List<String> getCourseNames(List<Integer> courseIds);
	
	void unenrollStudentFromAllCourses(int studentId);
}
