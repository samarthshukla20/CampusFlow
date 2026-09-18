package com.campusflow.service;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Subject;
import com.campusflow.repository.SubjectRepository;

import java.util.List;

public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService() {
        this.subjectRepository =
                new SubjectRepository();
    }

    // -----------------------------
    // Add Subject
    // -----------------------------

    public boolean addSubject(Subject subject) {

        validateSubject(subject);

        return subjectRepository.addSubject(subject);
    }

    // -----------------------------
    // Get All Subjects
    // -----------------------------

    public List<Subject> getAllSubjects() {

        return subjectRepository.getAllSubjects();
    }

    // -----------------------------
    // Get Subject By ID
    // -----------------------------

    public Subject getSubjectById(int id) {

        if (id <= 0) {

            throw new ValidationException(
                    "Subject ID must be greater than zero."
            );
        }

        return subjectRepository.getSubjectById(id);
    }

    // -----------------------------
    // Update Subject
    // -----------------------------

    public boolean updateSubject(
            Subject subject) {

        validateSubject(subject);

        if (subject.getId() <= 0) {

            throw new ValidationException(
                    "Invalid subject ID."
            );
        }

        return subjectRepository.updateSubject(
                subject
        );
    }

    // -----------------------------
    // Delete Subject
    // -----------------------------

    public boolean deleteSubject(
            int subjectId) {

        if (subjectId <= 0) {

            throw new ValidationException(
                    "Invalid subject ID."
            );
        }

        return subjectRepository.deleteSubject(
                subjectId
        );
    }

    // -----------------------------
    // Validation
    // -----------------------------

    private void validateSubject(
            Subject subject) {

        if (subject == null) {

            throw new ValidationException(
                    "Subject cannot be null."
            );
        }

        if (subject.getSubjectCode() == null ||
                subject.getSubjectCode().isBlank()) {

            throw new ValidationException(
                    "Subject code cannot be empty."
            );
        }

        if (subject.getSubjectName() == null ||
                subject.getSubjectName().isBlank()) {

            throw new ValidationException(
                    "Subject name cannot be empty."
            );
        }
    }
}