package com.cts.sms.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cts.sms.entity.Attendance;
import com.cts.sms.enums.AttendanceStatus;
import com.cts.sms.exception.ResourceNotFoundException;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.feign.InstructorClient;
import com.cts.sms.feign.StudentClient;
import com.cts.sms.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private CourseClient courseClient;

	@Autowired
	private InstructorClient instructorClient;

	@Autowired
	private StudentClient studentClient;

    @Override
    public Attendance markAttendance(int studentId, int courseId, int instructorId, AttendanceStatus status) {
        if (!studentClient.doesStudentExist(studentId)) {
            throw new ResourceNotFoundException("Student does not exist");
        }

        if (!courseClient.doesCourseExist(courseId)) {
            throw new ResourceNotFoundException("Course does not exist");
        }

        if (!instructorClient.doesInstructorExist(instructorId)) {
            throw new ResourceNotFoundException("Instructor does not exist");
        }

        if (!instructorClient.isInstructorAssigned(instructorId, courseId)) {
            throw new RuntimeException("Instructor is not assigned to this course!");
        }

        if (!courseClient.isStudentEnrolled(courseId, studentId)) {
            throw new RuntimeException("Student is not enrolled in this course!");
        }

        LocalDate today = LocalDate.now();
        List<Attendance> existingRecords = attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
        for (Attendance record : existingRecords) {
            if (record.getDate().equals(today)) {
                throw new RuntimeException("Attendance already marked for today");
            }
        }

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setCourseId(courseId);
        attendance.setInstructorId(instructorId);
        attendance.setStatus(status);
        attendance.setDate(today);

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getStudentAttendance(int studentId, int courseId) {
        return attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void markMissingAttendanceAsNA() {
        LocalDate today = LocalDate.now();
        List<Integer> courseIds = List.of(1, 2, 3);

        for (int courseId : courseIds) {
            List<Integer> enrolledStudents = courseClient.getEnrolledStudents(courseId);
            List<Integer> missingStudents = enrolledStudents.stream()
                .filter(studentId -> attendanceRepository.findByStudentIdAndCourseIdAndDate(studentId, courseId, today).isEmpty())
                .toList();

            for (int studentId : missingStudents) {
                Attendance attendance = new Attendance();
                attendance.setStudentId(studentId);
                attendance.setCourseId(courseId);
                attendance.setInstructorId(0);
                attendance.setStatus(AttendanceStatus.NA);
                attendance.setDate(today);
                attendanceRepository.save(attendance);
            }
        }
    }
}
