package com.campusflow.repository;

import com.campusflow.model.AcademicReportRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AcademicReportRepository {

    public List<AcademicReportRow> getReportForStudent(
            int studentId) {

        List<AcademicReportRow> reportRows =
                new ArrayList<>();

        String sql = """
                SELECT
                    sub.subject_code,
                    sub.subject_name,
                    g.marks,
                    g.max_marks,
                    g.credits,

                    COALESCE(
                        (
                            SELECT
                                (
                                    SUM(
                                        CASE
                                            WHEN a.status = 'PRESENT'
                                            THEN 1
                                            ELSE 0
                                        END
                                    ) * 100.0
                                    / COUNT(*)
                                )
                            FROM attendance a
                            WHERE a.student_id = ?
                              AND a.subject_id = sub.id
                        ),
                        0
                    ) AS attendance_percentage

                FROM grades g

                INNER JOIN subjects sub
                    ON g.subject_id = sub.id

                WHERE g.student_id = ?

                ORDER BY sub.subject_code
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, studentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    String subjectCode =
                            resultSet.getString(
                                    "subject_code"
                            );

                    String subjectName =
                            resultSet.getString(
                                    "subject_name"
                            );

                    double marks =
                            resultSet.getDouble(
                                    "marks"
                            );

                    double maxMarks =
                            resultSet.getDouble(
                                    "max_marks"
                            );

                    int credits =
                            resultSet.getInt(
                                    "credits"
                            );

                    double attendance =
                            resultSet.getDouble(
                                    "attendance_percentage"
                            );

                    double percentage =
                            (marks / maxMarks) * 100.0;

                    String letterGrade =
                            calculateLetterGrade(
                                    percentage
                            );

                    double gradePoint =
                            calculateGradePoint(
                                    percentage
                            );

                    AcademicReportRow row =
                            new AcademicReportRow(
                                    subjectCode,
                                    subjectName,
                                    marks,
                                    maxMarks,
                                    credits,
                                    letterGrade,
                                    gradePoint,
                                    attendance
                            );

                    reportRows.add(row);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error generating academic report: "
                            + e.getMessage()
            );
        }

        return reportRows;
    }

    private String calculateLetterGrade(
            double percentage) {

        if (percentage >= 90) {
            return "A+";
        }

        if (percentage >= 80) {
            return "A";
        }

        if (percentage >= 70) {
            return "B";
        }

        if (percentage >= 60) {
            return "C";
        }

        if (percentage >= 50) {
            return "D";
        }

        if (percentage >= 40) {
            return "E";
        }

        return "F";
    }

    private double calculateGradePoint(
            double percentage) {

        if (percentage >= 90) {
            return 10.0;
        }

        if (percentage >= 80) {
            return 9.0;
        }

        if (percentage >= 70) {
            return 8.0;
        }

        if (percentage >= 60) {
            return 7.0;
        }

        if (percentage >= 50) {
            return 6.0;
        }

        if (percentage >= 40) {
            return 5.0;
        }

        return 0.0;
    }
}