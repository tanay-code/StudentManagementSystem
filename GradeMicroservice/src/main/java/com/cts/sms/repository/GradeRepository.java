package com.cts.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.sms.entity.Grade;



public interface GradeRepository extends JpaRepository<Grade,Integer>{
	List<Grade> findByCourseIdIn(List<Integer> courseIds);
}
