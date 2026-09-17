package com.campusflow.repository;

import com.campusflow.model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AttendanceRepository {

    public boolean markAttendance(
            Attendance attendance
    ) {

        String sql = """
                INSERT INTO attendance
                (student_id,
                 subject_id,
                 attendance_date,
                 status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setInt(
                    2,
                    attendance.getSubjectId()
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            statement.setString(
                    4,
                    attendance.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error marking attendance: " +
                            e.getMessage()
            );

            return false;
        }
    }

    public List<Attendance> getAttendanceForStudent(
            int studentId
    ) {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql = """
            SELECT
                id,
                student_id,
                subject_id,
                attendance_date,
                status
            FROM attendance
            WHERE student_id = ?
            ORDER BY attendance_date DESC
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Attendance attendance =
                            new Attendance(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("student_id"),
                                    resultSet.getInt("subject_id"),
                                    resultSet.getDate(
                                            "attendance_date"
                                    ).toLocalDate(),
                                    resultSet.getString("status")
                            );

                    attendanceList.add(attendance);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving attendance: " +
                            e.getMessage()
            );
        }

        return attendanceList;
    }

    public double getAttendancePercentage(
            int studentId,
            int subjectId
    ) {

        String sql = """
            SELECT
                COUNT(*) AS total_classes,
                SUM(
                    CASE
                        WHEN status = 'PRESENT'
                        THEN 1
                        ELSE 0
                    END
                ) AS present_classes
            FROM attendance
            WHERE student_id = ?
              AND subject_id = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    int totalClasses =
                            resultSet.getInt("total_classes");

                    int presentClasses =
                            resultSet.getInt("present_classes");

                    if (totalClasses == 0) {
                        return 0.0;
                    }

                    return ((double) presentClasses /
                            totalClasses) * 100;
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error calculating attendance: " +
                            e.getMessage()
            );
        }

        return 0.0;
    }
}