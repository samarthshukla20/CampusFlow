package com.campusflow.service;

import com.campusflow.model.Student;
import com.campusflow.repository.DatabaseConnection;
import com.campusflow.repository.StudentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.campusflow.exception.ValidationException;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService() {
        this.studentRepository = new StudentRepository();
    }

    public boolean addStudent(Student student) {

        validateStudent(student);

        return studentRepository.addStudent(student);
    }

    private void validateStudent(Student student) {

        if (student == null) {
            throw new ValidationException(
                    "Student cannot be null."
            );
        }

        if (student.getName() == null ||
                student.getName().isBlank()) {

            throw new ValidationException(
                    "Student name cannot be empty."
            );
        }

        if (student.getEmail() == null ||
                student.getEmail().isBlank()) {

            throw new ValidationException(
                    "Student email cannot be empty."
            );
        }

        if (!student.getEmail().contains("@")) {

            throw new ValidationException(
                    "Invalid email address."
            );
        }

        if (student.getEnrollmentNumber() == null ||
                student.getEnrollmentNumber().isBlank()) {

            throw new ValidationException(
                    "Enrollment number cannot be empty."
            );
        }

        if (student.getDepartment() == null ||
                student.getDepartment().isBlank()) {

            throw new ValidationException(
                    "Department cannot be empty."
            );
        }

        if (student.getSemester() < 1 ||
                student.getSemester() > 8) {

            throw new ValidationException(
                    "Semester must be between 1 and 8."
            );
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public Student getStudentById(int id) {

        if (id <= 0) {
            throw new ValidationException(
                    "Student ID must be greater than zero."
            );
        }

        return studentRepository.getStudentById(id);
    }

    public boolean updateStudent(Student student) {

        validateStudent(student);

        if (student.getId() <= 0) {
            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        if (student.getUserId() <= 0) {
            throw new ValidationException(
                    "Invalid user ID."
            );
        }

        return studentRepository.updateStudent(student);
    }

    public List<Student> searchStudents(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return getAllStudents();
        }

        return studentRepository.searchStudents(
                keyword.trim()
        );
    }
}