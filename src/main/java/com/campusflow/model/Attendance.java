package com.campusflow.model;

import java.time.LocalDate;

public class Attendance {

    private int id;
    private int studentId;
    private int subjectId;
    private LocalDate attendanceDate;
    private String status;

    public Attendance(
            int id,
            int studentId,
            int subjectId,
            LocalDate attendanceDate,
            String status
    ) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public Attendance(
            int studentId,
            int subjectId,
            LocalDate attendanceDate,
            String status
    ) {
        this(
                0,
                studentId,
                subjectId,
                attendanceDate,
                status
        );
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", attendanceDate=" + attendanceDate +
                ", status='" + status + '\'' +
                '}';
    }
}