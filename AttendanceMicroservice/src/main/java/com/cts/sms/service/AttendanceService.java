package com.cts.sms.service;

import java.util.List;

import com.cts.sms.entity.Attendance;
import com.cts.sms.enums.AttendanceStatus;

public interface AttendanceService {
    Attendance markAttendance(int studentId, int courseId, int instructorId, AttendanceStatus status);
    List<Attendance> getStudentAttendance(int studentId, int courseId);
//    void markMissingAttendanceAsNA();
}
