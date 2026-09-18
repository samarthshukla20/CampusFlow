package com.campusflow.repository;

import com.campusflow.model.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.campusflow.util.PasswordUtil;

public class FacultyRepository {

    // -----------------------------
    // Add Faculty
    // -----------------------------

    public boolean addFaculty(Faculty faculty) {

        String userSql = """
                INSERT INTO users
                (name, email, password, role)
                VALUES (?, ?, ?, 'FACULTY')
                """;

        String facultySql = """
                INSERT INTO faculty
                (user_id, employee_id, department)
                VALUES (?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement userStatement =
                            connection.prepareStatement(
                                    userSql,
                                    java.sql.Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                userStatement.setString(
                        1,
                        faculty.getName()
                );

                userStatement.setString(
                        2,
                        faculty.getEmail()
                );


                userStatement.setString(
                        3,
                        PasswordUtil.hashPassword("temporary123")
                );

                userStatement.executeUpdate();

                int userId;

                try (ResultSet keys =
                             userStatement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "Failed to create faculty user."
                        );
                    }

                    userId = keys.getInt(1);
                }

                try (PreparedStatement facultyStatement =
                             connection.prepareStatement(
                                     facultySql
                             )) {

                    facultyStatement.setInt(
                            1,
                            userId
                    );

                    facultyStatement.setString(
                            2,
                            faculty.getEmployeeId()
                    );

                    facultyStatement.setString(
                            3,
                            faculty.getDepartment()
                    );

                    facultyStatement.executeUpdate();
                }

                connection.commit();

                return true;

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Error adding faculty: " +
                                e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error: " +
                            e.getMessage()
            );

            return false;
        }
    }

    // -----------------------------
    // Get All Faculty
    // -----------------------------

    public List<Faculty> getAllFaculty() {

        List<Faculty> facultyList =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.id,
                    f.user_id,
                    u.name,
                    u.email,
                    f.employee_id,
                    f.department
                FROM faculty f
                INNER JOIN users u
                    ON f.user_id = u.id
                ORDER BY f.id
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Faculty faculty =
                        new Faculty(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("employee_id"),
                                resultSet.getString("department")
                        );

                facultyList.add(faculty);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving faculty: " +
                            e.getMessage()
            );
        }

        return facultyList;
    }

    // -----------------------------
    // Get Faculty By ID
    // -----------------------------

    public Faculty getFacultyById(int id) {

        String sql = """
                SELECT
                    f.id,
                    f.user_id,
                    u.name,
                    u.email,
                    f.employee_id,
                    f.department
                FROM faculty f
                INNER JOIN users u
                    ON f.user_id = u.id
                WHERE f.id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Faculty(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("employee_id"),
                            resultSet.getString("department")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving faculty: " +
                            e.getMessage()
            );
        }

        return null;
    }

    // -----------------------------
    // Update Faculty
    // -----------------------------

    public boolean updateFaculty(
            Faculty faculty) {

        String userSql = """
                UPDATE users
                SET name = ?,
                    email = ?
                WHERE id = ?
                """;

        String facultySql = """
                UPDATE faculty
                SET employee_id = ?,
                    department = ?
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement userStatement =
                            connection.prepareStatement(
                                    userSql
                            );

                    PreparedStatement facultyStatement =
                            connection.prepareStatement(
                                    facultySql
                            )
            ) {

                userStatement.setString(
                        1,
                        faculty.getName()
                );

                userStatement.setString(
                        2,
                        faculty.getEmail()
                );

                userStatement.setInt(
                        3,
                        faculty.getUserId()
                );

                int userRows =
                        userStatement.executeUpdate();

                facultyStatement.setString(
                        1,
                        faculty.getEmployeeId()
                );

                facultyStatement.setString(
                        2,
                        faculty.getDepartment()
                );

                facultyStatement.setInt(
                        3,
                        faculty.getId()
                );

                int facultyRows =
                        facultyStatement.executeUpdate();

                if (userRows > 0 &&
                        facultyRows > 0) {

                    connection.commit();

                    return true;
                }

                connection.rollback();

                return false;

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Error updating faculty: " +
                                e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error: " +
                            e.getMessage()
            );

            return false;
        }
    }

    // -----------------------------
    // Delete Faculty
    // -----------------------------

    public boolean deleteFaculty(
            int facultyId) {

        String findUserSql = """
                SELECT user_id
                FROM faculty
                WHERE id = ?
                """;

        String deleteFacultySql = """
                DELETE FROM faculty
                WHERE id = ?
                """;

        String deleteUserSql = """
                DELETE FROM users
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int userId;

                // Find associated user
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     findUserSql
                             )) {

                    statement.setInt(
                            1,
                            facultyId
                    );

                    try (ResultSet resultSet =
                                 statement.executeQuery()) {

                        if (!resultSet.next()) {

                            connection.rollback();

                            return false;
                        }

                        userId =
                                resultSet.getInt(
                                        "user_id"
                                );
                    }
                }

                // Delete faculty
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     deleteFacultySql
                             )) {

                    statement.setInt(
                            1,
                            facultyId
                    );

                    int rows =
                            statement.executeUpdate();

                    if (rows == 0) {

                        connection.rollback();

                        return false;
                    }
                }

                // Delete associated user
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     deleteUserSql
                             )) {

                    statement.setInt(
                            1,
                            userId
                    );

                    statement.executeUpdate();
                }

                connection.commit();

                return true;

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Error deleting faculty: " +
                                e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error: " +
                            e.getMessage()
            );

            return false;
        }
    }

    // -----------------------------
    // Search Faculty
    // -----------------------------

    public List<Faculty> searchFaculty(
            String keyword) {

        List<Faculty> facultyList =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.id,
                    f.user_id,
                    u.name,
                    u.email,
                    f.employee_id,
                    f.department
                FROM faculty f
                INNER JOIN users u
                    ON f.user_id = u.id
                WHERE LOWER(u.name) LIKE ?
                   OR LOWER(u.email) LIKE ?
                   OR LOWER(f.employee_id) LIKE ?
                   OR LOWER(f.department) LIKE ?
                ORDER BY f.id
                """;

        String searchPattern =
                "%" + keyword.toLowerCase() + "%";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    searchPattern
            );

            statement.setString(
                    2,
                    searchPattern
            );

            statement.setString(
                    3,
                    searchPattern
            );

            statement.setString(
                    4,
                    searchPattern
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Faculty faculty =
                            new Faculty(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("user_id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("email"),
                                    resultSet.getString("employee_id"),
                                    resultSet.getString("department")
                            );

                    facultyList.add(faculty);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error searching faculty: " +
                            e.getMessage()
            );
        }

        return facultyList;
    }
}