package com.campusflow;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Student;
import com.campusflow.service.StudentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService =
            new StudentService();

    @Test
    void shouldRejectNullStudent() {

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(null)
        );
    }

    @Test
    void shouldRejectBlankName() {

        Student student = new Student(
                "",
                "student@example.com",
                "STU001",
                "CSE",
                2
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectBlankEmail() {

        Student student = new Student(
                "Test Student",
                "",
                "STU001",
                "CSE",
                2
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectInvalidEmail() {

        Student student = new Student(
                "Test Student",
                "invalid-email",
                "STU001",
                "CSE",
                2
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectBlankEnrollmentNumber() {

        Student student = new Student(
                "Test Student",
                "student@example.com",
                "",
                "CSE",
                2
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectBlankDepartment() {

        Student student = new Student(
                "Test Student",
                "student@example.com",
                "STU001",
                "",
                2
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectSemesterBelowOne() {

        Student student = new Student(
                "Test Student",
                "student@example.com",
                "STU001",
                "CSE",
                0
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectSemesterAboveEight() {

        Student student = new Student(
                "Test Student",
                "student@example.com",
                "STU001",
                "CSE",
                9
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void shouldRejectInvalidStudentId() {

        assertThrows(
                ValidationException.class,
                () -> studentService.getStudentById(0)
        );
    }

    @Test
    void shouldRejectInvalidDeleteId() {

        assertThrows(
                ValidationException.class,
                () -> studentService.deleteStudent(0)
        );
    }

    @Test
    void shouldRejectInvalidSearchInput() {

        // Blank search should be valid and return all students.
        assertDoesNotThrow(
                () -> studentService.searchStudents("")
        );
    }
}