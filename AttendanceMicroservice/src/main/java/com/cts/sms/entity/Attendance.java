package com.cts.sms.entity;

import java.time.LocalDate;
import java.util.List;

import com.cts.sms.enums.AttendanceStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int studentId;  // Student ID


    private int courseId;   // Course ID


    private int instructorId; // Instructor ID who marked attendance

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // Present, Absent, NA

    private LocalDate date;  // Date of attendance
}

