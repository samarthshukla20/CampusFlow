package com.campusflow.service;

import com.campusflow.model.Subject;
import com.campusflow.repository.SubjectRepository;

import java.util.List;

public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService() {
        this.subjectRepository =
                new SubjectRepository();
    }

    public boolean addSubject(Subject subject) {

        validateSubject(subject);

        return subjectRepository.addSubject(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.getAllSubjects();
    }

    public Subject getSubjectById(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Subject ID must be greater than zero."
            );
        }

        return subjectRepository.getSubjectById(id);
    }

    private void validateSubject(Subject subject) {

        if (subject == null) {
            throw new IllegalArgumentException(
                    "Subject cannot be null."
            );
        }

        if (subject.getSubjectCode() == null ||
                subject.getSubjectCode().isBlank()) {

            throw new IllegalArgumentException(
                    "Subject code cannot be empty."
            );
        }

        if (subject.getSubjectName() == null ||
                subject.getSubjectName().isBlank()) {

            throw new IllegalArgumentException(
                    "Subject name cannot be empty."
            );
        }
    }
}