package com.campusflow;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Subject;
import com.campusflow.service.SubjectService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectServiceTest {

    private final SubjectService subjectService =
            new SubjectService();

    @Test
    void addSubjectShouldRejectNullSubject() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.addSubject(null)
        );
    }

    @Test
    void addSubjectShouldRejectEmptyCode() {

        Subject subject =
                new Subject(
                        "",
                        "Java Programming",
                        null
                );

        assertThrows(
                ValidationException.class,
                () -> subjectService.addSubject(subject)
        );
    }

    @Test
    void addSubjectShouldRejectBlankCode() {

        Subject subject =
                new Subject(
                        "   ",
                        "Java Programming",
                        null
                );

        assertThrows(
                ValidationException.class,
                () -> subjectService.addSubject(subject)
        );
    }

    @Test
    void addSubjectShouldRejectEmptyName() {

        Subject subject =
                new Subject(
                        "CS101",
                        "",
                        null
                );

        assertThrows(
                ValidationException.class,
                () -> subjectService.addSubject(subject)
        );
    }

    @Test
    void addSubjectShouldRejectBlankName() {

        Subject subject =
                new Subject(
                        "CS101",
                        "   ",
                        null
                );

        assertThrows(
                ValidationException.class,
                () -> subjectService.addSubject(subject)
        );
    }

    @Test
    void getSubjectByIdShouldRejectInvalidId() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.getSubjectById(0)
        );
    }

    @Test
    void getSubjectByIdShouldRejectNegativeId() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.getSubjectById(-1)
        );
    }

    @Test
    void updateSubjectShouldRejectInvalidId() {

        Subject subject =
                new Subject(
                        0,
                        "CS101",
                        "Java Programming",
                        null
                );

        assertThrows(
                ValidationException.class,
                () -> subjectService.updateSubject(subject)
        );
    }

    @Test
    void updateSubjectShouldRejectNullSubject() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.updateSubject(null)
        );
    }

    @Test
    void deleteSubjectShouldRejectInvalidId() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.deleteSubject(0)
        );
    }

    @Test
    void deleteSubjectShouldRejectNegativeId() {

        assertThrows(
                ValidationException.class,
                () -> subjectService.deleteSubject(-1)
        );
    }
}