package com.cts.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query("SELECT i FROM Instructor i WHERE :courseId MEMBER OF i.assignedCourses")
    List<Instructor> findInstructorsByCourseId(@Param("courseId") int courseId);
}
