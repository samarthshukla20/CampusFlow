package com.campusflow.ui;

import com.campusflow.model.Attendance;
import com.campusflow.model.Student;
import com.campusflow.model.Subject;
import com.campusflow.model.User;
import com.campusflow.service.AttendanceService;
import com.campusflow.service.StudentService;
import com.campusflow.service.SubjectService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AttendanceManagementView {

    private final Stage stage;
    private final User currentUser;

    private final AttendanceService attendanceService =
            new AttendanceService();

    private final StudentService studentService =
            new StudentService();

    private final SubjectService subjectService =
            new SubjectService();

    private final ObservableList<Attendance> attendanceList =
            FXCollections.observableArrayList();

    private final TableView<Attendance> table =
            new TableView<>();

    private final ComboBox<Student> studentComboBox =
            new ComboBox<>();

    private final ComboBox<Subject> subjectComboBox =
            new ComboBox<>();

    private final DatePicker datePicker =
            new DatePicker();

    private final ComboBox<String> statusComboBox =
            new ComboBox<>();

    private final Label percentageLabel =
            new Label("Attendance: --");

    public AttendanceManagementView(
            Stage stage,
            User currentUser) {

        this.stage = stage;
        this.currentUser = currentUser;
    }

    public BorderPane create() {

        BorderPane root =
                new BorderPane();

        root.setPadding(
                new Insets(20)
        );

        // -----------------------------
        // Header
        // -----------------------------

        Button backButton =
                new Button("← Back to Dashboard");

        Label title =
                new Label("Attendance Management");

        title.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;"
        );

        HBox header =
                new HBox(15);

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.getChildren().addAll(
                backButton,
                title
        );

        backButton.setOnAction(event -> {

            DashboardView dashboardView =
                    new DashboardView(stage);

            VBox dashboard =
                    dashboardView.create(currentUser);

            Scene scene =
                    new Scene(
                            dashboard,
                            900,
                            600
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Form
        // -----------------------------

        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.setPadding(
                new Insets(20, 0, 20, 0)
        );

        studentComboBox.setPromptText(
                "Select student"
        );

        studentComboBox.setPrefWidth(250);

        subjectComboBox.setPromptText(
                "Select subject"
        );

        subjectComboBox.setPrefWidth(250);

        datePicker.setValue(
                LocalDate.now()
        );

        statusComboBox.setItems(
                FXCollections.observableArrayList(
                        "PRESENT",
                        "ABSENT"
                )
        );

        statusComboBox.setValue(
                "PRESENT"
        );

        form.add(
                new Label("Student:"),
                0,
                0
        );

        form.add(
                studentComboBox,
                1,
                0
        );

        form.add(
                new Label("Subject:"),
                2,
                0
        );

        form.add(
                subjectComboBox,
                3,
                0
        );

        form.add(
                new Label("Date:"),
                0,
                1
        );

        form.add(
                datePicker,
                1,
                1
        );

        form.add(
                new Label("Status:"),
                2,
                1
        );

        form.add(
                statusComboBox,
                3,
                1
        );

        // -----------------------------
        // Buttons
        // -----------------------------

        Button markButton =
                new Button("Mark Attendance");

        Button viewButton =
                new Button("View Attendance");

        Button percentageButton =
                new Button("Calculate Percentage");

        HBox buttons =
                new HBox(10);

        buttons.setAlignment(
                Pos.CENTER_LEFT
        );

        buttons.getChildren().addAll(
                markButton,
                viewButton,
                percentageButton
        );

        // -----------------------------
        // Table
        // -----------------------------

        TableColumn<Attendance, Number> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        TableColumn<Attendance, Number> studentColumn =
                new TableColumn<>("Student ID");

        studentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getStudentId()
                        )
        );

        TableColumn<Attendance, Number> subjectColumn =
                new TableColumn<>("Subject ID");

        subjectColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getSubjectId()
                        )
        );

        TableColumn<Attendance, String> dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue()
                                        .getAttendanceDate()
                                        .toString()
                        )
        );

        TableColumn<Attendance, String> statusColumn =
                new TableColumn<>("Status");

        statusColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getStatus()
                        )
        );

        table.getColumns().addAll(
                idColumn,
                studentColumn,
                subjectColumn,
                dateColumn,
                statusColumn
        );

        table.setItems(
                attendanceList
        );

        // -----------------------------
        // Mark Attendance
        // -----------------------------

        markButton.setOnAction(event -> {

            try {

                Student student =
                        studentComboBox.getValue();

                Subject subject =
                        subjectComboBox.getValue();

                LocalDate date =
                        datePicker.getValue();

                String status =
                        statusComboBox.getValue();

                if (student == null) {
                    showError(
                            "Please select a student."
                    );
                    return;
                }

                if (subject == null) {
                    showError(
                            "Please select a subject."
                    );
                    return;
                }

                if (date == null) {
                    showError(
                            "Please select a date."
                    );
                    return;
                }

                Attendance attendance =
                        new Attendance(
                                student.getId(),
                                subject.getId(),
                                date,
                                status
                        );

                if (attendanceService.markAttendance(
                        attendance)) {

                    showMessage(
                            "Success",
                            "Attendance marked successfully."
                    );

                    loadAttendance(
                            student.getId()
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // View Attendance
        // -----------------------------

        viewButton.setOnAction(event -> {

            Student student =
                    studentComboBox.getValue();

            if (student == null) {

                showError(
                        "Please select a student."
                );

                return;
            }

            loadAttendance(
                    student.getId()
            );
        });

        // -----------------------------
        // Calculate Percentage
        // -----------------------------

        percentageButton.setOnAction(event -> {

            Student student =
                    studentComboBox.getValue();

            Subject subject =
                    subjectComboBox.getValue();

            if (student == null) {

                showError(
                        "Please select a student."
                );

                return;
            }

            if (subject == null) {

                showError(
                        "Please select a subject."
                );

                return;
            }

            try {

                double percentage =
                        attendanceService
                                .getAttendancePercentage(
                                        student.getId(),
                                        subject.getId()
                                );

                percentageLabel.setText(
                        String.format(
                                "Attendance: %.2f%%",
                                percentage
                        )
                );

                if (attendanceService.isLowAttendance(
                        student.getId(),
                        subject.getId())) {

                    percentageLabel.setText(
                            String.format(
                                    "Attendance: %.2f%%  ⚠ Low Attendance",
                                    percentage
                            )
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Load Students & Subjects
        // -----------------------------

        loadStudents();

        loadSubjects();

        // -----------------------------
        // Main Layout
        // -----------------------------

        VBox container =
                new VBox(15);

        container.setPadding(
                new Insets(10)
        );

        container.getChildren().addAll(
                header,
                form,
                buttons,
                percentageLabel,
                table
        );

        root.setCenter(
                container
        );

        return root;
    }

    // -----------------------------
    // Load Students
    // -----------------------------

    private void loadStudents() {

        studentComboBox.setItems(
                FXCollections.observableArrayList(
                        studentService.getAllStudents()
                )
        );
    }

    // -----------------------------
    // Load Subjects
    // -----------------------------

    private void loadSubjects() {

        subjectComboBox.setItems(
                FXCollections.observableArrayList(
                        subjectService.getAllSubjects()
                )
        );
    }

    // -----------------------------
    // Load Attendance
    // -----------------------------

    private void loadAttendance(
            int studentId) {

        List<Attendance> records =
                attendanceService
                        .getAttendanceForStudent(
                                studentId
                        );

        attendanceList.setAll(
                records
        );
    }

    // -----------------------------
    // Information Dialog
    // -----------------------------

    private void showMessage(
            String title,
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // -----------------------------
    // Error Dialog
    // -----------------------------

    private void showError(
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}