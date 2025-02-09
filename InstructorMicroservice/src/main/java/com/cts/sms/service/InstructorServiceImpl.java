package com.cts.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sms.entity.Instructor;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseClient courseClient;

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
		existingInstructor.setAssignedCourses(instructor.getAssignedCourses());
		return instructorRepository.save(existingInstructor);
	}

	@Override
	public void deleteInstructor(int id) {
		instructorRepository.deleteById(id);
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

}
