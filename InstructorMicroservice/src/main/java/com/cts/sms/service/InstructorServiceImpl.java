package com.cts.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sms.entity.Instructor;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.feign.SecurityClient;
import com.cts.sms.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseClient courseClient;
	
	@Autowired
	private SecurityClient securityClient;

	@Override
	public Instructor createInstructor(Instructor instructor) {
		return instructorRepository.save(instructor);
	}

	@Override
	public Instructor getInstructorById(int id) {
		return instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor not found"));
	}

	@Override
	public List<Instructor> getAllInstructors() {
		return instructorRepository.findAll();
	}

	@Override
	public Instructor updateInstructor(int id, Instructor instructor) {
		Instructor existingInstructor = getInstructorById(id);
		existingInstructor.setName(instructor.getName());
		existingInstructor.setEmail(instructor.getEmail());
		if(instructor.getAssignedCourses().size()>0) {
		existingInstructor.setAssignedCourses(instructor.getAssignedCourses());
		}
		securityClient.updateInstructorByUserId(existingInstructor.getUserId(),instructor);
		return instructorRepository.save(existingInstructor);
	}

	@Override
	public void deleteInstructor(int id) {
		
		
		Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
		int userId = instructor.getUserId();
		instructorRepository.deleteById(id);
		securityClient.deleteByUserId(userId);
	}

	@Override
    public Instructor assignInstructorToCourse(int instructorId, int courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Check if course exists before assigning
        if (!courseClient.doesCourseExist(courseId)) {
            throw new RuntimeException("Course not found");
        }

        // Assign course if it's not already assigned
        if (!instructor.getAssignedCourses().contains(courseId)) {
            instructor.getAssignedCourses().add(courseId);
            instructorRepository.save(instructor);
        }

        return instructor;
    }

    @Override
    public List<Integer> getAssignedCourses(int instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        
        return instructor.getAssignedCourses(); // Return list of course IDs
    }
    
//	@Override
//	public List<Integer> getAssignedCoursesByUserId(int userId) {
//		// TODO Auto-generated method stub
//		Instructor instructor = instructorRepository.findByUserId(userId)
//				.orElseThrow(() -> new RuntimeException("Instructor not found"));
//		return instructor.getAssignedCourses();
//	}

	@Override
	public boolean isAssignedCourse(int instructorId, int courseId) {
		Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
		
		if (!courseClient.doesCourseExist(courseId)) {
            throw new RuntimeException("Course not found");
        }
        return instructor.getAssignedCourses().contains(courseId);
	}

	@Override
	public boolean doesInstructorExist(int id) {
		return instructorRepository.existsById(id);
	}
	
    @Override
    public void removeCourseFromInstructor(int instructorId, int courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));
        instructor.getAssignedCourses().remove(Integer.valueOf(courseId));
        instructorRepository.save(instructor);
    }

	@Override
	public List<Instructor> getInstructorsByCourseId(int courseId) {
		return instructorRepository.findInstructorsByCourseId(courseId);
	}

	@Override
	public List<Integer> getAssignedCoursesByUserId(int userId) {
		Instructor instructor = instructorRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Instructor not found"));
		return instructor.getAssignedCourses();
	}



}
