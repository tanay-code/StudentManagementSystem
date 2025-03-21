package com.cts.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
   List<Course> findByStudentsEnrolledContaining(int studentId);
}
