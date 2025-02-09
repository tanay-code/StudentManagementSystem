package com.cts.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.sms.entity.Attendance;
import com.cts.sms.enums.AttendanceStatus;
import com.cts.sms.feign.CourseClient;
import com.cts.sms.feign.InstructorClient;
import com.cts.sms.feign.StudentClient;
import com.cts.sms.repository.AttendanceRepository;
import com.cts.sms.service.AttendanceServiceImpl;

@SpringBootTest
class AttendanceMicroserviceApplicationTests {

	@Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentClient studentClient;

    @Mock
    private CourseClient courseClient;

    @Mock
    private InstructorClient instructorClient;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private Attendance attendance;

    @BeforeEach
    public void setUp() {
        attendance = new Attendance();
        attendance.setStudentId(1);
        attendance.setCourseId(1);
        attendance.setInstructorId(1);
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setDate(LocalDate.now());
    }

    @Test
    public void testMarkAttendance_Success() {
        when(studentClient.doesStudentExist(1)).thenReturn(true);
        when(courseClient.doesCourseExist(1)).thenReturn(true);
        when(instructorClient.doesInstructorExist(1)).thenReturn(true);
        when(instructorClient.isInstructorAssigned(1, 1)).thenReturn(true);
        when(courseClient.isStudentEnrolled(1, 1)).thenReturn(true);
        when(attendanceRepository.findByStudentIdAndCourseId(1, 1)).thenReturn(new ArrayList<>());

        Attendance result = attendanceService.markAttendance(1, 1, 1, AttendanceStatus.PRESENT);

        assertNull(result);
        assertEquals(AttendanceStatus.PRESENT, result.getStatus());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    public void testMarkAttendance_StudentNotExist() {
        when(studentClient.doesStudentExist(1)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            attendanceService.markAttendance(1, 1, 1, AttendanceStatus.PRESENT);
        });

        assertEquals("Student does not exist", exception.getMessage());
    }

}
