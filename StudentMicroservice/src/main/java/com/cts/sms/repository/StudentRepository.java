package com.cts.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

//	List<Student> findByMarksGreaterThan(int marks);
}
