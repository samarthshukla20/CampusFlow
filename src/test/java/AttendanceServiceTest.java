package com.campusflow;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Attendance;
import com.campusflow.service.AttendanceService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    private final AttendanceService attendanceService =
            new AttendanceService();

    @Test
    void shouldAcceptPresentAttendance() {

        Attendance attendance = new Attendance(
                1,
                1,
                LocalDate.now(),
                "PRESENT"
        );

        assertDoesNotThrow(
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldAcceptAbsentAttendance() {

        Attendance attendance = new Attendance(
                1,
                1,
                LocalDate.now(),
                "ABSENT"
        );

        assertDoesNotThrow(
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectInvalidStudentId() {

        Attendance attendance = new Attendance(
                0,
                1,
                LocalDate.now(),
                "PRESENT"
        );

        assertThrows(
                ValidationException.class,
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectInvalidSubjectId() {

        Attendance attendance = new Attendance(
                1,
                0,
                LocalDate.now(),
                "PRESENT"
        );

        assertThrows(
                ValidationException.class,
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectNullDate() {

        Attendance attendance = new Attendance(
                1,
                1,
                null,
                "PRESENT"
        );

        assertThrows(
                ValidationException.class,
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectInvalidStatus() {

        Attendance attendance = new Attendance(
                1,
                1,
                LocalDate.now(),
                "LATE"
        );

        assertThrows(
                ValidationException.class,
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectNullStatus() {

        Attendance attendance = new Attendance(
                1,
                1,
                LocalDate.now(),
                null
        );

        assertThrows(
                ValidationException.class,
                () -> attendanceService.markAttendance(attendance)
        );
    }

    @Test
    void shouldRejectInvalidStudentIdForPercentage() {

        assertThrows(
                ValidationException.class,
                () -> attendanceService
                        .getAttendancePercentage(0, 1)
        );
    }

    @Test
    void shouldRejectInvalidSubjectIdForPercentage() {

        assertThrows(
                ValidationException.class,
                () -> attendanceService
                        .getAttendancePercentage(1, 0)
        );
    }

    @Test
    void attendanceThresholdShouldBe75Percent() {

        assertEquals(
                75.0,
                attendanceService.getAttendanceThreshold()
        );
    }
}