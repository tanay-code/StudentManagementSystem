package com.cts.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.sms.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer>{

}



