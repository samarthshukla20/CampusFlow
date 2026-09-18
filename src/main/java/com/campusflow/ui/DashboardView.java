package com.campusflow.ui;

import com.campusflow.model.User;
import com.campusflow.service.AuthorizationService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    private final AuthorizationService authorizationService =
            new AuthorizationService();

    private final Stage stage;

    public DashboardView(Stage stage) {
        this.stage = stage;
    }

    public VBox create(User user) {

        VBox root =
                new VBox(15);

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(40)
        );

        // -----------------------------
        // Title
        // -----------------------------

        Label title =
                new Label("CampusFlow");

        title.setStyle(
                "-fx-font-size: 28px; " +
                        "-fx-font-weight: bold;"
        );

        // -----------------------------
        // Welcome Message
        // -----------------------------

        Label welcome =
                new Label(
                        "Welcome, " +
                                user.getName()
                );

        Label role =
                new Label(
                        "Role: " +
                                user.getRole()
                );

        // -----------------------------
        // Management Buttons
        // -----------------------------

        Button studentButton =
                new Button(
                        "Student Management"
                );

        Button facultyButton =
                new Button(
                        "Faculty Management"
                );

        Button subjectButton =
                new Button(
                        "Subject Management"
                );

        Button attendanceButton =
                new Button(
                        "Attendance Management"
                );

        Button gradeButton =
                new Button(
                        "Grade Management"
                );

        Button reportButton =
                new Button(
                        "Academic Reports"
                );

        Button logoutButton =
                new Button(
                        "Logout"
                );

        // -----------------------------
        // Authorization
        // -----------------------------

        studentButton.setDisable(
                !authorizationService
                        .canManageStudents(user)
        );

        facultyButton.setDisable(
                !authorizationService
                        .canManageFaculty(user)
        );

        subjectButton.setDisable(
                !authorizationService
                        .canManageStudents(user)
        );

        attendanceButton.setDisable(
                !authorizationService
                        .canMarkAttendance(user)
        );

        gradeButton.setDisable(
                !authorizationService
                        .canManageGrades(user)
        );

        // -----------------------------
        // Student Management
        // -----------------------------

        studentButton.setOnAction(event -> {

            StudentManagementView studentView =
                    new StudentManagementView(
                            stage,
                            user
                    );

            BorderPane studentScreen =
                    studentView.create();

            Scene scene =
                    new Scene(
                            studentScreen,
                            1100,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Faculty Management
        // -----------------------------

        facultyButton.setOnAction(event -> {

            FacultyManagementView facultyView =
                    new FacultyManagementView(
                            stage,
                            user
                    );

            BorderPane facultyScreen =
                    facultyView.create();

            Scene scene =
                    new Scene(
                            facultyScreen,
                            1100,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Subject Management
        // -----------------------------

        subjectButton.setOnAction(event -> {

            SubjectManagementView subjectView =
                    new SubjectManagementView(
                            stage,
                            user
                    );

            BorderPane subjectScreen =
                    subjectView.create();

            Scene scene =
                    new Scene(
                            subjectScreen,
                            1100,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Attendance Management
        // -----------------------------

        attendanceButton.setOnAction(event -> {

            AttendanceManagementView attendanceView =
                    new AttendanceManagementView(
                            stage,
                            user
                    );

            BorderPane attendanceScreen =
                    attendanceView.create();

            Scene scene =
                    new Scene(
                            attendanceScreen,
                            1100,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Grade Management
        // -----------------------------

        gradeButton.setOnAction(event -> {

            GradeManagementView gradeView =
                    new GradeManagementView(
                            stage,
                            user
                    );

            BorderPane gradeScreen =
                    gradeView.create();

            Scene scene =
                    new Scene(
                            gradeScreen,
                            1100,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Academic Reports
        // -----------------------------

        reportButton.setOnAction(event -> {

            AcademicReportView reportView =
                    new AcademicReportView(
                            stage,
                            user
                    );

            BorderPane reportScreen =
                    reportView.create();

            Scene scene =
                    new Scene(
                            reportScreen,
                            1200,
                            700
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Logout
        // -----------------------------

        logoutButton.setOnAction(event -> {

            new CampusFlowApplicationHelper(stage)
                    .showLogin();
        });

        // -----------------------------
        // Add Components
        // -----------------------------

        root.getChildren().addAll(
                title,
                welcome,
                role,
                studentButton,
                facultyButton,
                subjectButton,
                attendanceButton,
                gradeButton,
                reportButton,
                logoutButton
        );

        return root;
    }
}