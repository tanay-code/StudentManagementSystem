package com.cts.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sms.entity.Course;
import com.cts.sms.exceptions.ResourceNotFoundException;
import com.cts.sms.feign.CourseInterface;
import com.cts.sms.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	CourseInterface courseInterface;
	
//	private CourseInterface courseInterface;
//	
//	public CourseServiceImpl(CourseInterface courseInterface) {
//        this.courseInterface = courseInterface;
//    }

	@Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course updateCourse(Integer id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            course.setCourseName(updatedCourse.getCourseName());
            course.setDescription(updatedCourse.getDescription());
            course.setDuration(updatedCourse.getDuration());
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public String enrollStudent(int courseId, int studentId) {
        // Check if the student exists in the Student Microservice
        boolean studentExists = courseInterface.doesStudentExist(studentId);

        if (!studentExists) {
            throw new ResourceNotFoundException("Student with ID " + studentId + " does not exist!");
        }

        // Check if the student is already enrolled in the course
        if (isStudentEnrolledInCourse(courseId, studentId)) {
            throw new ResourceNotFoundException("Student is already enrolled in this course!");
        }

        // Enroll student if they exist and are not already enrolled
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.getStudentsEnrolled().add(studentId);
            courseRepository.save(course);
            return "Student " + studentId + " enrolled successfully!";
        } else {
            throw new ResourceNotFoundException("Course not found!");
        }
    }
    
    @Override //helper method
    public boolean isStudentEnrolledInCourse(int courseId, int studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        return courseOptional.map(course -> course.getStudentsEnrolled().contains(studentId)).orElse(false);
    }
    
    @Override
    public List<Course> getCoursesEnrolledByStudent(int studentId) {
        // Assuming you have an Enrollment entity that relates students to courses.
        return courseRepository.findByStudentsEnrolledContaining(studentId);
    }

	@Override
	public boolean doesCourseExists(int courseId) {
		return courseRepository.existsById(courseId);
	}

	@Override
	public List<Integer> getEnrolledStudents(int courseId) {
		Optional<Course> course = courseRepository.findById(courseId);
        return course.map(Course::getStudentsEnrolled).orElseThrow(() -> 
        new ResourceNotFoundException("Course not found with ID: " + courseId)
        );
	}

	@Override
	public boolean isStudentEnrolled(int courseId, int studentId) {
		Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		boolean studentExists = courseInterface.doesStudentExist(studentId);

        if (!studentExists) {
            throw new ResourceNotFoundException("Student with ID " + studentId + " does not exist!");
        }
		return course.getStudentsEnrolled().contains(studentId);
	}

}
