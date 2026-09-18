package com.campusflow.service;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.AcademicReportRow;
import com.campusflow.repository.AcademicReportRepository;

import java.util.List;

public class AcademicReportService {

    private static final double
            ATTENDANCE_THRESHOLD = 75.0;

    private final AcademicReportRepository
            academicReportRepository;

    public AcademicReportService() {

        this.academicReportRepository =
                new AcademicReportRepository();
    }

    public List<AcademicReportRow> getReportForStudent(
            int studentId) {

        if (studentId <= 0) {

            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        return academicReportRepository
                .getReportForStudent(studentId);
    }

    public double calculateAveragePercentage(
            List<AcademicReportRow> rows) {

        if (rows == null || rows.isEmpty()) {
            return 0.0;
        }

        double totalPercentage = 0.0;

        for (AcademicReportRow row : rows) {

            totalPercentage +=
                    (row.getMarks()
                            / row.getMaxMarks())
                            * 100.0;
        }

        return totalPercentage / rows.size();
    }

    public double calculateGPA(
            List<AcademicReportRow> rows) {

        if (rows == null || rows.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (AcademicReportRow row : rows) {

            totalPoints +=
                    row.getGradePoint()
                            * row.getCredits();

            totalCredits +=
                    row.getCredits();
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalPoints / totalCredits;
    }

    public double calculateAverageAttendance(
            List<AcademicReportRow> rows) {

        if (rows == null || rows.isEmpty()) {
            return 0.0;
        }

        double totalAttendance = 0.0;

        for (AcademicReportRow row : rows) {

            totalAttendance +=
                    row.getAttendancePercentage();
        }

        return totalAttendance / rows.size();
    }

    public boolean hasLowAttendance(
            AcademicReportRow row) {

        return row.getAttendancePercentage()
                < ATTENDANCE_THRESHOLD;
    }

    public double getAttendanceThreshold() {
        return ATTENDANCE_THRESHOLD;
    }
}