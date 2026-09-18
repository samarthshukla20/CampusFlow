package com.campusflow.repository;

import com.campusflow.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.campusflow.util.PasswordUtil;

public class StudentRepository {

    public boolean addStudent(Student student) {

        String userSql = """
                INSERT INTO users
                (name, email, password, role)
                VALUES (?, ?, ?, 'STUDENT')
                """;

        String studentSql = """
                INSERT INTO students
                (user_id, enrollment_number, department, semester)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // Step 1: Create user
            int userId;

            try (PreparedStatement userStatement =
                         connection.prepareStatement(
                                 userSql,
                                 PreparedStatement.RETURN_GENERATED_KEYS
                         )) {

                userStatement.setString(1, student.getName());
                userStatement.setString(2, student.getEmail());

                userStatement.setString(
                        3,
                        PasswordUtil.hashPassword("temporary123")
                );

                userStatement.executeUpdate();

                try (ResultSet generatedKeys =
                             userStatement.getGeneratedKeys()) {

                    if (!generatedKeys.next()) {
                        throw new SQLException(
                                "Failed to generate user ID."
                        );
                    }

                    userId = generatedKeys.getInt(1);
                }
            }

            // Step 2: Create student record
            try (PreparedStatement studentStatement =
                         connection.prepareStatement(studentSql)) {

                studentStatement.setInt(1, userId);
                studentStatement.setString(
                        2,
                        student.getEnrollmentNumber()
                );
                studentStatement.setString(
                        3,
                        student.getDepartment()
                );
                studentStatement.setInt(
                        4,
                        student.getSemester()
                );

                studentStatement.executeUpdate();
            }

            // Everything succeeded
            connection.commit();

            return true;

        } catch (SQLException e) {

            // Undo partial changes
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Error adding student: " +
                            e.getMessage()
            );

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = """
            SELECT
                s.id,
                s.user_id,
                u.name,
                u.email,
                s.enrollment_number,
                s.department,
                s.semester
            FROM students s
            INNER JOIN users u
                ON s.user_id = u.id
            ORDER BY s.id
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("enrollment_number"),
                        resultSet.getString("department"),
                        resultSet.getInt("semester")
                );

                students.add(student);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving students: " +
                            e.getMessage()
            );
        }

        return students;
    }

    public Student getStudentById(int id) {

        String sql = """
            SELECT
                s.id,
                s.user_id,
                u.name,
                u.email,
                s.enrollment_number,
                s.department,
                s.semester
            FROM students s
            INNER JOIN users u
                ON s.user_id = u.id
            WHERE s.id = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Student(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("enrollment_number"),
                            resultSet.getString("department"),
                            resultSet.getInt("semester")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving student: " +
                            e.getMessage()
            );
        }

        return null;
    }

    public boolean updateStudent(Student student) {

        String userSql = """
            UPDATE users
            SET name = ?, email = ?
            WHERE id = ?
            """;

        String studentSql = """
            UPDATE students
            SET enrollment_number = ?,
                department = ?,
                semester = ?
            WHERE id = ?
            """;

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Update user information
            try (PreparedStatement statement =
                         connection.prepareStatement(userSql)) {

                statement.setString(1, student.getName());
                statement.setString(2, student.getEmail());
                statement.setInt(3, student.getUserId());

                statement.executeUpdate();
            }

            // Update student information
            try (PreparedStatement statement =
                         connection.prepareStatement(studentSql)) {

                statement.setString(
                        1,
                        student.getEnrollmentNumber()
                );
                statement.setString(
                        2,
                        student.getDepartment()
                );
                statement.setInt(
                        3,
                        student.getSemester()
                );
                statement.setInt(
                        4,
                        student.getId()
                );

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Error updating student: " +
                            e.getMessage()
            );

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean deleteStudent(int id) {

        String findUserSql = """
            SELECT user_id
            FROM students
            WHERE id = ?
            """;

        String deleteStudentSql = """
            DELETE FROM students
            WHERE id = ?
            """;

        String deleteUserSql = """
            DELETE FROM users
            WHERE id = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            int userId;

            // Find associated user
            try (PreparedStatement statement =
                         connection.prepareStatement(findUserSql)) {

                statement.setInt(1, id);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }

                    userId = resultSet.getInt("user_id");
                }
            }

            // Delete student
            try (PreparedStatement statement =
                         connection.prepareStatement(deleteStudentSql)) {

                statement.setInt(1, id);

                int studentRows =
                        statement.executeUpdate();

                if (studentRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Delete associated user
            try (PreparedStatement statement =
                         connection.prepareStatement(deleteUserSql)) {

                statement.setInt(1, userId);
                statement.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting student: " +
                            e.getMessage()
            );

            return false;
        }
    }

    public List<Student> searchStudents(String keyword) {

        List<Student> students = new ArrayList<>();

        String sql = """
            SELECT
                s.id,
                s.user_id,
                u.name,
                u.email,
                s.enrollment_number,
                s.department,
                s.semester
            FROM students s
            INNER JOIN users u
                ON s.user_id = u.id
            WHERE LOWER(u.name) LIKE ?
               OR LOWER(u.email) LIKE ?
               OR LOWER(s.enrollment_number) LIKE ?
               OR LOWER(s.department) LIKE ?
            ORDER BY s.id
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            String searchPattern =
                    "%" + keyword.toLowerCase() + "%";

            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            statement.setString(4, searchPattern);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Student student = new Student(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString(
                                    "enrollment_number"
                            ),
                            resultSet.getString("department"),
                            resultSet.getInt("semester")
                    );

                    students.add(student);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error searching students: " +
                            e.getMessage()
            );
        }

        return students;
    }
}