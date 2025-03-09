package com.cts.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sms.dto.Course;
import com.cts.sms.entity.Student;
import com.cts.sms.exceptions.StudentNotFoundException;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.feign.SecurityClient;
import com.cts.sms.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	CourseClient courseClient;
	
	@Autowired
	SecurityClient securityClient;

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
	public Student getStudentByUserId(int userId) throws StudentNotFoundException {
		Optional<Student> optional = studentRepository.findByUserId(userId);
		if (optional.isPresent()) {
			
			return optional.get();
		}
		else {
			throw new StudentNotFoundException("No Student Found With Given Id!!!!");
		}
	}

	@Override
	public String deleteStudent(int stdId) {
		
		Student student = studentRepository.findById(stdId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + stdId));
		int userId = student.getUserId();
		studentRepository.deleteById(stdId);
		securityClient.deleteByUserId(userId);
		courseClient.unenrollStudentFromAllCourses(stdId);
		
		return "Student Deleted Successfully";
	}

	@Override
	public boolean existsById(int id) {
		return studentRepository.existsById(id);
	}
	
	@Override
	public List<Course> getCoursesEnrolledByStudent(int studentId) {
	    // Fetch courses from CourseService
	    return courseClient.getCoursesByStudent(studentId);
	}

	@Override
	public Student updateStudent(int id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        securityClient.updateStudentByUserId(student.getUserId(),studentDetails);
        
        return studentRepository.save(student);
	}
}
