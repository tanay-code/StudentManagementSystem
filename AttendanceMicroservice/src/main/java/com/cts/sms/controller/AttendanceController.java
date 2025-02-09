package com.cts.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sms.entity.Attendance;
import com.cts.sms.enums.AttendanceStatus;
import com.cts.sms.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark/{studentId}/{instructorId}/{courseId}/{status}")
    public Attendance markAttendance(
    		@PathVariable int studentId,
    		@PathVariable int instructorId,
    		@PathVariable int courseId,
    		@PathVariable("status") AttendanceStatus status) {
        return attendanceService.markAttendance(studentId, courseId, instructorId, status);
    }

    @GetMapping
    public List<Attendance> getStudentAttendance(@RequestParam int studentId, @RequestParam int courseId) {
        return attendanceService.getStudentAttendance(studentId, courseId);
    }
}
