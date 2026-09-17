package com.campusflow;

import com.campusflow.model.User;
import com.campusflow.security.AuthorizationService;
import com.campusflow.security.Role;
import com.campusflow.service.AuthenticationService;

public class Main {

    public static void main(String[] args) {

        AuthenticationService authenticationService =
                new AuthenticationService();

        AuthorizationService authorizationService =
                new AuthorizationService();

        System.out.println("=================================");
        System.out.println("       CAMPUSFLOW RBAC");
        System.out.println("=================================");

        try {

            User user = authenticationService.login(
                    "admin@campusflow.com",
                    "Admin@123"
            );

            System.out.println("\nLogged in as: "
                    + user.getName());

            System.out.println(
                    "Role: " + user.getRole()
            );

            System.out.println(
                    "\nCan manage students: "
                            + authorizationService.canManageStudents(user)
            );

            System.out.println(
                    "Can mark attendance: "
                            + authorizationService.canMarkAttendance(user)
            );

            System.out.println(
                    "Can manage grades: "
                            + authorizationService.canManageGrades(user)
            );

            System.out.println(
                    "Can view academic data: "
                            + authorizationService.canViewAcademicData(user)
            );

            System.out.println(
                    "\nIs ADMIN: "
                            + authorizationService.hasRole(
                            user,
                            Role.ADMIN
                    )
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Login failed: " + e.getMessage()
            );
        }
    }
}