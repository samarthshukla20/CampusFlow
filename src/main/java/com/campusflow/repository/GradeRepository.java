package com.campusflow.repository;

import com.campusflow.model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {

    public boolean addGrade(Grade grade) {

        String sql = """
                INSERT INTO grades
                (student_id, subject_id, marks, max_marks, credits)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getSubjectId());
            statement.setDouble(3, grade.getMarks());
            statement.setDouble(4, grade.getMaxMarks());
            statement.setInt(5, grade.getCredits());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding grade: " + e.getMessage());
            return false;
        }
    }

    public List<Grade> getGradesForStudent(int studentId) {

        String sql = """
                SELECT id, student_id, subject_id,
                       marks, max_marks, credits
                FROM grades
                WHERE student_id = ?
                ORDER BY subject_id
                """;

        List<Grade> grades = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Grade grade = new Grade(
                        resultSet.getInt("id"),
                        resultSet.getInt("student_id"),
                        resultSet.getInt("subject_id"),
                        resultSet.getDouble("marks"),
                        resultSet.getDouble("max_marks"),
                        resultSet.getInt("credits")
                );

                grades.add(grade);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving grades: " + e.getMessage()
            );
        }

        return grades;
    }

    public Grade getGrade(int studentId, int subjectId) {

        String sql = """
                SELECT id, student_id, subject_id,
                       marks, max_marks, credits
                FROM grades
                WHERE student_id = ? AND subject_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Grade(
                        resultSet.getInt("id"),
                        resultSet.getInt("student_id"),
                        resultSet.getInt("subject_id"),
                        resultSet.getDouble("marks"),
                        resultSet.getDouble("max_marks"),
                        resultSet.getInt("credits")
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving grade: " + e.getMessage()
            );
        }

        return null;
    }

    public boolean updateGrade(Grade grade) {

        String sql = """
                UPDATE grades
                SET marks = ?,
                    max_marks = ?,
                    credits = ?
                WHERE student_id = ?
                  AND subject_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, grade.getMarks());
            statement.setDouble(2, grade.getMaxMarks());
            statement.setInt(3, grade.getCredits());
            statement.setInt(4, grade.getStudentId());
            statement.setInt(5, grade.getSubjectId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error updating grade: " + e.getMessage()
            );
            return false;
        }
    }

    public boolean deleteGrade(int studentId, int subjectId) {

        String sql = """
                DELETE FROM grades
                WHERE student_id = ? AND subject_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting grade: " + e.getMessage()
            );
            return false;
        }
    }
}