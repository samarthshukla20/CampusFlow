package com.campusflow;

import com.campusflow.model.User;
import com.campusflow.security.AuthorizationService;
import com.campusflow.security.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationServiceTest {

    private final AuthorizationService authorizationService =
            new AuthorizationService();

    private final User admin = new User(
            1,
            "Admin",
            "admin@test.com",
            "hashed-password",
            "ADMIN"
    );

    private final User faculty = new User(
            2,
            "Faculty",
            "faculty@test.com",
            "hashed-password",
            "FACULTY"
    );

    private final User student = new User(
            3,
            "Student",
            "student@test.com",
            "hashed-password",
            "STUDENT"
    );

    @Test
    void adminShouldManageStudents() {

        assertTrue(
                authorizationService.canManageStudents(admin)
        );
    }

    @Test
    void facultyShouldNotManageStudents() {

        assertFalse(
                authorizationService.canManageStudents(faculty)
        );
    }

    @Test
    void studentShouldNotManageStudents() {

        assertFalse(
                authorizationService.canManageStudents(student)
        );
    }

    @Test
    void adminShouldMarkAttendance() {

        assertTrue(
                authorizationService.canMarkAttendance(admin)
        );
    }

    @Test
    void facultyShouldMarkAttendance() {

        assertTrue(
                authorizationService.canMarkAttendance(faculty)
        );
    }

    @Test
    void studentShouldNotMarkAttendance() {

        assertFalse(
                authorizationService.canMarkAttendance(student)
        );
    }

    @Test
    void adminShouldManageGrades() {

        assertTrue(
                authorizationService.canManageGrades(admin)
        );
    }

    @Test
    void facultyShouldManageGrades() {

        assertTrue(
                authorizationService.canManageGrades(faculty)
        );
    }

    @Test
    void studentShouldNotManageGrades() {

        assertFalse(
                authorizationService.canManageGrades(student)
        );
    }

    @Test
    void allAuthenticatedUsersShouldViewAcademicData() {

        assertTrue(
                authorizationService.canViewAcademicData(admin)
        );

        assertTrue(
                authorizationService.canViewAcademicData(faculty)
        );

        assertTrue(
                authorizationService.canViewAcademicData(student)
        );
    }

    @Test
    void nullUserShouldHaveNoPermissions() {

        assertFalse(
                authorizationService.canManageStudents(null)
        );

        assertFalse(
                authorizationService.canMarkAttendance(null)
        );

        assertFalse(
                authorizationService.canManageGrades(null)
        );

        assertFalse(
                authorizationService.canViewAcademicData(null)
        );
    }

    @Test
    void shouldCorrectlyCheckRole() {

        assertTrue(
                authorizationService.hasRole(
                        admin,
                        Role.ADMIN
                )
        );

        assertTrue(
                authorizationService.hasRole(
                        faculty,
                        Role.FACULTY
                )
        );

        assertTrue(
                authorizationService.hasRole(
                        student,
                        Role.STUDENT
                )
        );
    }

    @Test
    void shouldRejectIncorrectRole() {

        assertFalse(
                authorizationService.hasRole(
                        student,
                        Role.ADMIN
                )
        );

        assertFalse(
                authorizationService.hasRole(
                        faculty,
                        Role.STUDENT
                )
        );
    }
}