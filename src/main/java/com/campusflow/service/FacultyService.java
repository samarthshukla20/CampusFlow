package com.campusflow.service;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Faculty;
import com.campusflow.repository.FacultyRepository;

import java.util.List;

public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService() {
        this.facultyRepository =
                new FacultyRepository();
    }

    // -----------------------------
    // Add Faculty
    // -----------------------------

    public boolean addFaculty(Faculty faculty) {

        validateFaculty(faculty);

        return facultyRepository.addFaculty(
                faculty
        );
    }

    // -----------------------------
    // Get All Faculty
    // -----------------------------

    public List<Faculty> getAllFaculty() {

        return facultyRepository.getAllFaculty();
    }

    // -----------------------------
    // Get Faculty By ID
    // -----------------------------

    public Faculty getFacultyById(int id) {

        if (id <= 0) {

            throw new ValidationException(
                    "Faculty ID must be greater than zero."
            );
        }

        return facultyRepository.getFacultyById(
                id
        );
    }

    // -----------------------------
    // Update Faculty
    // -----------------------------

    public boolean updateFaculty(
            Faculty faculty) {

        validateFaculty(faculty);

        if (faculty.getId() <= 0) {

            throw new ValidationException(
                    "Invalid faculty ID."
            );
        }

        if (faculty.getUserId() <= 0) {

            throw new ValidationException(
                    "Invalid user ID."
            );
        }

        return facultyRepository.updateFaculty(
                faculty
        );
    }

    // -----------------------------
    // Delete Faculty
    // -----------------------------

    public boolean deleteFaculty(
            int facultyId) {

        if (facultyId <= 0) {

            throw new ValidationException(
                    "Invalid faculty ID."
            );
        }

        return facultyRepository.deleteFaculty(
                facultyId
        );
    }

    // -----------------------------
    // Search Faculty
    // -----------------------------

    public List<Faculty> searchFaculty(
            String keyword) {

        if (keyword == null ||
                keyword.isBlank()) {

            return getAllFaculty();
        }

        return facultyRepository.searchFaculty(
                keyword.trim()
        );
    }

    // -----------------------------
    // Validation
    // -----------------------------

    private void validateFaculty(
            Faculty faculty) {

        if (faculty == null) {

            throw new ValidationException(
                    "Faculty cannot be null."
            );
        }

        if (faculty.getName() == null ||
                faculty.getName().isBlank()) {

            throw new ValidationException(
                    "Faculty name cannot be empty."
            );
        }

        if (faculty.getEmail() == null ||
                faculty.getEmail().isBlank()) {

            throw new ValidationException(
                    "Faculty email cannot be empty."
            );
        }

        if (!faculty.getEmail().contains("@")) {

            throw new ValidationException(
                    "Invalid email address."
            );
        }

        if (faculty.getEmployeeId() == null ||
                faculty.getEmployeeId().isBlank()) {

            throw new ValidationException(
                    "Employee ID cannot be empty."
            );
        }

        if (faculty.getDepartment() == null ||
                faculty.getDepartment().isBlank()) {

            throw new ValidationException(
                    "Department cannot be empty."
            );
        }
    }
}