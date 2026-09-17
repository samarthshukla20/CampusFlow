package com.campusflow.security;

import com.campusflow.model.User;

public class AuthorizationService {

    // -----------------------------
    // Check Role
    // -----------------------------
    public boolean hasRole(User user, Role requiredRole) {

        if (user == null || requiredRole == null) {
            return false;
        }

        return user.getRole().equalsIgnoreCase(
                requiredRole.name()
        );
    }

    // -----------------------------
    // Admin Permission
    // -----------------------------
    public boolean canManageStudents(User user) {

        return hasRole(user, Role.ADMIN);
    }

    // -----------------------------
    // Faculty Permission
    // -----------------------------
    public boolean canMarkAttendance(User user) {

        return hasRole(user, Role.ADMIN)
                || hasRole(user, Role.FACULTY);
    }

    // -----------------------------
    // Grade Management Permission
    // -----------------------------
    public boolean canManageGrades(User user) {

        return hasRole(user, Role.ADMIN)
                || hasRole(user, Role.FACULTY);
    }

    // -----------------------------
    // Student View Permission
    // -----------------------------
    public boolean canViewAcademicData(User user) {

        return user != null;
    }
}