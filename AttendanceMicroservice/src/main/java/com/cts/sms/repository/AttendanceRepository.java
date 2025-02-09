package com.cts.sms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudentIdAndCourseId(int studentId, int courseId);

    List<Attendance> findByStudentIdAndCourseIdAndDate(int studentId, int courseId, LocalDate date);    
}


