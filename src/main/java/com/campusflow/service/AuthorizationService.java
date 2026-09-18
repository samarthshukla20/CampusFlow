package com.campusflow.service;

import com.campusflow.model.User;
import com.campusflow.security.Role;

public class AuthorizationService {

    // -----------------------------
    // Check whether user has role
    // -----------------------------
    public boolean hasRole(User user, Role role) {

        if (user == null || role == null) {
            return false;
        }

        return user.getRole() != null &&
                user.getRole().equalsIgnoreCase(
                        role.name()
                );
    }

    // -----------------------------
    // Student Management
    // ADMIN only
    // -----------------------------
    public boolean canManageStudents(User user) {

        return hasRole(
                user,
                Role.ADMIN
        );
    }

    // -----------------------------
    // Faculty Management
    // ADMIN only
    // -----------------------------
    public boolean canManageFaculty(User user) {

        return hasRole(
                user,
                Role.ADMIN
        );
    }

    // -----------------------------
    // Attendance Management
    // ADMIN + FACULTY
    // -----------------------------
    public boolean canMarkAttendance(User user) {

        return hasRole(user, Role.ADMIN) ||
                hasRole(user, Role.FACULTY);
    }

    // -----------------------------
    // Grade Management
    // ADMIN + FACULTY
    // -----------------------------
    public boolean canManageGrades(User user) {

        return hasRole(user, Role.ADMIN) ||
                hasRole(user, Role.FACULTY);
    }

    // -----------------------------
    // Academic Data
    // All authenticated users
    // -----------------------------
    public boolean canViewAcademicData(User user) {

        return user != null;
    }
}