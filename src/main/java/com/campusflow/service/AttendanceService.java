package com.campusflow.service;

import com.campusflow.model.Attendance;
import com.campusflow.repository.AttendanceRepository;

import java.util.List;

public class AttendanceService {

    private static final double ATTENDANCE_THRESHOLD = 75.0;

    private final AttendanceRepository attendanceRepository;

    public AttendanceService() {
        this.attendanceRepository =
                new AttendanceRepository();
    }

    public boolean markAttendance(
            Attendance attendance
    ) {

        validateAttendance(attendance);

        return attendanceRepository.markAttendance(
                attendance
        );
    }

    public List<Attendance> getAttendanceForStudent(
            int studentId
    ) {

        if (studentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid student ID."
            );
        }

        return attendanceRepository
                .getAttendanceForStudent(studentId);
    }

    public double getAttendancePercentage(
            int studentId,
            int subjectId
    ) {

        if (studentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid student ID."
            );
        }

        if (subjectId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid subject ID."
            );
        }

        return attendanceRepository
                .getAttendancePercentage(
                        studentId,
                        subjectId
                );
    }

    public boolean isLowAttendance(
            int studentId,
            int subjectId
    ) {

        double percentage =
                getAttendancePercentage(
                        studentId,
                        subjectId
                );

        return percentage < ATTENDANCE_THRESHOLD;
    }

    public double getAttendanceThreshold() {
        return ATTENDANCE_THRESHOLD;
    }

    private void validateAttendance(
            Attendance attendance
    ) {

        if (attendance == null) {
            throw new IllegalArgumentException(
                    "Attendance cannot be null."
            );
        }

        if (attendance.getStudentId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid student ID."
            );
        }

        if (attendance.getSubjectId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid subject ID."
            );
        }

        if (attendance.getAttendanceDate() == null) {
            throw new IllegalArgumentException(
                    "Attendance date is required."
            );
        }

        if (!attendance.getStatus().equals("PRESENT") &&
                !attendance.getStatus().equals("ABSENT")) {

            throw new IllegalArgumentException(
                    "Status must be PRESENT or ABSENT."
            );
        }
    }
}