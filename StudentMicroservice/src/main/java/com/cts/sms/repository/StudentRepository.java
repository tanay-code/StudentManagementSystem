package com.cts.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

//	List<Student> findByMarksGreaterThan(int marks);
	
	Optional<Student> findByUserId(int userId);
}
