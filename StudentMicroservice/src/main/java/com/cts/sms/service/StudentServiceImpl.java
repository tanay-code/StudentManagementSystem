package com.cts.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sms.dto.Course;
import com.cts.sms.entity.Student;
import com.cts.sms.exceptions.StudentNotFoundException;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	CourseClient courseClient;

	@Override
	public String addStudent(Student student) {
		Student std = studentRepository.save(student);
		if (std != null)
			return "Student Saved Successfully";
		else
			return "Failed To Save Student";
	}

	@Override
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudent(int stdId) throws StudentNotFoundException {
		Optional<Student> optional = studentRepository.findById(stdId);
		if (optional.isPresent()) {
			
			return optional.get();
		}
		else {
			throw new StudentNotFoundException("No Student Found With Given Id!!!!");
		}
	}

	@Override
	public Student updateStudent(Student student) {
		Student std = studentRepository.save(student);
		if (std != null)
			return std;
		else
			return null;
	}

	@Override
	public String deleteStudent(int stdId) {
		studentRepository.deleteById(stdId);
		return "Student Deleted Successfully";
	}

	@Override
	public boolean existsById(int id) {
		return studentRepository.existsById(id);
	}
	
	@Override
	public List<Course> getCoursesEnrolledByStudent(int studentId) {
	    // Fetch courses from CourseService
	    // This would typically be a call to the CourseService's method
	    return courseClient.getCoursesByStudent(studentId); // Implement this method in CourseService
	}

}
