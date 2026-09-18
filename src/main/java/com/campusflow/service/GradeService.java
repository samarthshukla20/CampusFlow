package com.campusflow.service;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Grade;
import com.campusflow.repository.GradeRepository;

import java.util.List;

public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService() {
        this.gradeRepository =
                new GradeRepository();
    }

    // -----------------------------
    // Add Grade
    // -----------------------------

    public boolean addGrade(Grade grade) {

        validateGrade(grade);

        return gradeRepository.addGrade(grade);
    }

    // -----------------------------
    // Get All Grades For Student
    // -----------------------------

    public List<Grade> getGradesForStudent(
            int studentId) {

        if (studentId <= 0) {

            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        return gradeRepository.getGradesForStudent(
                studentId
        );
    }

    // -----------------------------
    // Get Specific Grade
    // -----------------------------

    public Grade getGrade(
            int studentId,
            int subjectId) {

        if (studentId <= 0) {

            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        if (subjectId <= 0) {

            throw new ValidationException(
                    "Invalid subject ID."
            );
        }

        return gradeRepository.getGrade(
                studentId,
                subjectId
        );
    }

    // -----------------------------
    // Update Grade
    // -----------------------------

    public boolean updateGrade(
            Grade grade) {

        validateGrade(grade);

        if (grade.getId() <= 0) {

            throw new ValidationException(
                    "Invalid grade ID."
            );
        }

        return gradeRepository.updateGrade(
                grade
        );
    }

    // -----------------------------
    // Delete Grade
    // -----------------------------

    public boolean deleteGrade(
            int studentId,
            int subjectId) {

        if (studentId <= 0) {

            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        if (subjectId <= 0) {

            throw new ValidationException(
                    "Invalid subject ID."
            );
        }

        return gradeRepository.deleteGrade(
                studentId,
                subjectId
        );
    }

    // -----------------------------
    // Calculate Percentage
    // -----------------------------

    public double calculatePercentage(
            Grade grade) {

        validateGrade(grade);

        return (
                grade.getMarks()
                        / grade.getMaxMarks()
        ) * 100.0;
    }

    // -----------------------------
    // Calculate Letter Grade
    // -----------------------------

    public String calculateLetterGrade(
            Grade grade) {

        double percentage =
                calculatePercentage(grade);

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

    // -----------------------------
    // Calculate Grade Point
    // -----------------------------

    public double calculateGradePoint(
            Grade grade) {

        double percentage =
                calculatePercentage(grade);

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

    // -----------------------------
    // Calculate GPA
    // -----------------------------

    public double calculateGPA(
            int studentId) {

        List<Grade> grades =
                getGradesForStudent(
                        studentId
                );

        if (grades.isEmpty()) {
            return 0.0;
        }

        double totalWeightedPoints =
                0.0;

        int totalCredits = 0;

        for (Grade grade : grades) {

            double gradePoint =
                    calculateGradePoint(
                            grade
                    );

            totalWeightedPoints +=
                    gradePoint
                            * grade.getCredits();

            totalCredits +=
                    grade.getCredits();
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalWeightedPoints
                / totalCredits;
    }

    // -----------------------------
    // Validate Grade
    // -----------------------------

    private void validateGrade(
            Grade grade) {

        if (grade == null) {

            throw new ValidationException(
                    "Grade cannot be null."
            );
        }

        if (grade.getStudentId() <= 0) {

            throw new ValidationException(
                    "Invalid student ID."
            );
        }

        if (grade.getSubjectId() <= 0) {

            throw new ValidationException(
                    "Invalid subject ID."
            );
        }

        if (grade.getMarks() < 0) {

            throw new ValidationException(
                    "Marks cannot be negative."
            );
        }

        if (grade.getMaxMarks() <= 0) {

            throw new ValidationException(
                    "Maximum marks must be greater than zero."
            );
        }

        if (grade.getMarks()
                > grade.getMaxMarks()) {

            throw new ValidationException(
                    "Marks cannot exceed maximum marks."
            );
        }

        if (grade.getCredits() <= 0) {

            throw new ValidationException(
                    "Credits must be greater than zero."
            );
        }
    }
}