package com.campusflow.repository;

import com.campusflow.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {

    // -----------------------------
    // Add Subject
    // -----------------------------

    public boolean addSubject(Subject subject) {

        String sql = """
                INSERT INTO subjects
                (subject_code, subject_name, faculty_id)
                VALUES (?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    subject.getSubjectCode()
            );

            statement.setString(
                    2,
                    subject.getSubjectName()
            );

            if (subject.getFacultyId() == null) {

                statement.setNull(
                        3,
                        java.sql.Types.INTEGER
                );

            } else {

                statement.setInt(
                        3,
                        subject.getFacultyId()
                );
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding subject: " +
                            e.getMessage()
            );

            return false;
        }
    }

    // -----------------------------
    // Get All Subjects
    // -----------------------------

    public List<Subject> getAllSubjects() {

        List<Subject> subjects =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       subject_code,
                       subject_name,
                       faculty_id
                FROM subjects
                ORDER BY id
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                int facultyId =
                        resultSet.getInt("faculty_id");

                Integer facultyIdValue =
                        resultSet.wasNull()
                                ? null
                                : facultyId;

                Subject subject =
                        new Subject(
                                resultSet.getInt("id"),
                                resultSet.getString(
                                        "subject_code"
                                ),
                                resultSet.getString(
                                        "subject_name"
                                ),
                                facultyIdValue
                        );

                subjects.add(subject);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving subjects: " +
                            e.getMessage()
            );
        }

        return subjects;
    }

    // -----------------------------
    // Get Subject By ID
    // -----------------------------

    public Subject getSubjectById(int id) {

        String sql = """
                SELECT id,
                       subject_code,
                       subject_name,
                       faculty_id
                FROM subjects
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    int facultyId =
                            resultSet.getInt("faculty_id");

                    Integer facultyIdValue =
                            resultSet.wasNull()
                                    ? null
                                    : facultyId;

                    return new Subject(
                            resultSet.getInt("id"),
                            resultSet.getString(
                                    "subject_code"
                            ),
                            resultSet.getString(
                                    "subject_name"
                            ),
                            facultyIdValue
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving subject: " +
                            e.getMessage()
            );
        }

        return null;
    }

    // -----------------------------
    // Update Subject
    // -----------------------------

    public boolean updateSubject(
            Subject subject) {

        String sql = """
                UPDATE subjects
                SET subject_code = ?,
                    subject_name = ?,
                    faculty_id = ?
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    subject.getSubjectCode()
            );

            statement.setString(
                    2,
                    subject.getSubjectName()
            );

            if (subject.getFacultyId() == null) {

                statement.setNull(
                        3,
                        java.sql.Types.INTEGER
                );

            } else {

                statement.setInt(
                        3,
                        subject.getFacultyId()
                );
            }

            statement.setInt(
                    4,
                    subject.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating subject: " +
                            e.getMessage()
            );

            return false;
        }
    }

    // -----------------------------
    // Delete Subject
    // -----------------------------

    public boolean deleteSubject(
            int id) {

        String sql = """
                DELETE FROM subjects
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    id
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting subject: " +
                            e.getMessage()
            );

            return false;
        }
    }
}