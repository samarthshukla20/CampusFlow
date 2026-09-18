package com.campusflow.ui;

import com.campusflow.model.AcademicReportRow;
import com.campusflow.model.Student;
import com.campusflow.model.User;
import com.campusflow.service.AcademicReportService;
import com.campusflow.service.StudentService;

import javafx.beans.property.SimpleStringProperty;
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

import java.util.List;

public class AcademicReportView {

    private final Stage stage;
    private final User currentUser;

    private final AcademicReportService reportService =
            new AcademicReportService();

    private final StudentService studentService =
            new StudentService();

    private final ObservableList<AcademicReportRow> reportList =
            FXCollections.observableArrayList();

    private final ComboBox<Student> studentComboBox =
            new ComboBox<>();

    private final TableView<AcademicReportRow> table =
            new TableView<>();

    private final Label percentageLabel =
            new Label("Overall Percentage: -");

    private final Label gpaLabel =
            new Label("GPA: -");

    private final Label attendanceLabel =
            new Label("Average Attendance: -");

    private final Label statusLabel =
            new Label("Select a student to view the report.");

    public AcademicReportView(
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
                new Label("Academic Reports");

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
                    dashboardView.create(
                            currentUser
                    );

            Scene scene =
                    new Scene(
                            dashboard,
                            900,
                            600
                    );

            stage.setScene(scene);
        });

        // -----------------------------
        // Student Selection
        // -----------------------------

        studentComboBox.setPromptText(
                "Select Student"
        );

        studentComboBox.setPrefWidth(350);

        studentComboBox.setCellFactory(
                listView ->
                        new ListCell<>() {

                            @Override
                            protected void updateItem(
                                    Student student,
                                    boolean empty) {

                                super.updateItem(
                                        student,
                                        empty
                                );

                                if (empty ||
                                        student == null) {

                                    setText(null);

                                } else {

                                    setText(
                                            student.getEnrollmentNumber()
                                                    + " - "
                                                    + student.getName()
                                    );
                                }
                            }
                        }
        );

        studentComboBox.setButtonCell(
                new ListCell<>() {

                    @Override
                    protected void updateItem(
                            Student student,
                            boolean empty) {

                        super.updateItem(
                                student,
                                empty
                        );

                        if (empty ||
                                student == null) {

                            setText(null);

                        } else {

                            setText(
                                    student.getEnrollmentNumber()
                                            + " - "
                                            + student.getName()
                            );
                        }
                    }
                }
        );

        Button viewReportButton =
                new Button("View Report");

        HBox studentSelection =
                new HBox(10);

        studentSelection.setAlignment(
                Pos.CENTER_LEFT
        );

        studentSelection.getChildren().addAll(
                new Label("Student:"),
                studentComboBox,
                viewReportButton
        );

        // -----------------------------
        // Summary
        // -----------------------------

        percentageLabel.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );

        gpaLabel.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );

        attendanceLabel.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );

        HBox summary =
                new HBox(40);

        summary.setAlignment(
                Pos.CENTER_LEFT
        );

        summary.setPadding(
                new Insets(15, 0, 15, 0)
        );

        summary.getChildren().addAll(
                percentageLabel,
                gpaLabel,
                attendanceLabel
        );

        // -----------------------------
        // Table
        // -----------------------------

        TableColumn<AcademicReportRow, String>
                subjectCodeColumn =
                new TableColumn<>("Subject Code");

        subjectCodeColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue()
                                        .getSubjectCode()
                        )
        );

        TableColumn<AcademicReportRow, String>
                subjectNameColumn =
                new TableColumn<>("Subject");

        subjectNameColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue()
                                        .getSubjectName()
                        )
        );

        TableColumn<AcademicReportRow, String>
                marksColumn =
                new TableColumn<>("Marks");

        marksColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                String.format(
                                        "%.2f / %.2f",
                                        data.getValue()
                                                .getMarks(),
                                        data.getValue()
                                                .getMaxMarks()
                                )
                        )
        );

        TableColumn<AcademicReportRow, String>
                percentageColumn =
                new TableColumn<>("Percentage");

        percentageColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                String.format(
                                        "%.2f%%",
                                        calculatePercentage(
                                                data.getValue()
                                        )
                                )
                        )
        );

        TableColumn<AcademicReportRow, String>
                gradeColumn =
                new TableColumn<>("Grade");

        gradeColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue()
                                        .getLetterGrade()
                        )
        );

        TableColumn<AcademicReportRow, String>
                pointColumn =
                new TableColumn<>("Grade Point");

        pointColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                String.format(
                                        "%.2f",
                                        data.getValue()
                                                .getGradePoint()
                                )
                        )
        );

        TableColumn<AcademicReportRow, String>
                attendanceColumn =
                new TableColumn<>("Attendance");

        attendanceColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                String.format(
                                        "%.2f%%",
                                        data.getValue()
                                                .getAttendancePercentage()
                                )
                        )
        );

        table.getColumns().addAll(
                subjectCodeColumn,
                subjectNameColumn,
                marksColumn,
                percentageColumn,
                gradeColumn,
                pointColumn,
                attendanceColumn
        );

        table.setItems(
                reportList
        );

        table.setPlaceholder(
                new Label(
                        "No academic records found."
                )
        );

        // -----------------------------
        // Low Attendance Highlight
        // -----------------------------

        table.setRowFactory(
                tableView ->
                        new TableRow<>() {

                            @Override
                            protected void updateItem(
                                    AcademicReportRow row,
                                    boolean empty) {

                                super.updateItem(
                                        row,
                                        empty
                                );

                                if (empty ||
                                        row == null) {

                                    setStyle("");

                                } else if (
                                        reportService
                                                .hasLowAttendance(row)) {

                                    setStyle(
                                            "-fx-background-color: #ffe0e0;"
                                    );

                                } else {

                                    setStyle("");
                                }
                            }
                        }
        );

        // -----------------------------
        // View Report
        // -----------------------------

        viewReportButton.setOnAction(event -> {

            Student selectedStudent =
                    studentComboBox.getValue();

            if (selectedStudent == null) {

                showError(
                        "Please select a student."
                );

                return;
            }

            loadReport(
                    selectedStudent
            );
        });

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
                studentSelection,
                summary,
                statusLabel,
                table
        );

        root.setCenter(
                container
        );

        loadStudents();

        return root;
    }

    // -----------------------------
    // Load Students
    // -----------------------------

    private void loadStudents() {

        try {

            studentComboBox.setItems(
                    FXCollections.observableArrayList(
                            studentService.getAllStudents()
                    )
            );

        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }

    // -----------------------------
    // Load Report
    // -----------------------------

    private void loadReport(
            Student student) {

        try {

            List<AcademicReportRow> rows =
                    reportService.getReportForStudent(
                            student.getId()
                    );

            reportList.setAll(rows);

            if (rows.isEmpty()) {

                percentageLabel.setText(
                        "Overall Percentage: -"
                );

                gpaLabel.setText(
                        "GPA: -"
                );

                attendanceLabel.setText(
                        "Average Attendance: -"
                );

                statusLabel.setText(
                        "No academic records found for "
                                + student.getName()
                                + "."
                );

                return;
            }

            double percentage =
                    reportService
                            .calculateAveragePercentage(rows);

            double attendance =
                    reportService
                            .calculateAverageAttendance(rows);


            double gpa =
                    reportService.calculateGPA(rows);

            percentageLabel.setText(
                    String.format(
                            "Overall Percentage: %.2f%%",
                            percentage
                    )
            );

            gpaLabel.setText(
                    String.format(
                            "GPA: %.2f",
                            gpa
                    )
            );

            attendanceLabel.setText(
                    String.format(
                            "Average Attendance: %.2f%%",
                            attendance
                    )
            );

            statusLabel.setText(
                    "Academic report for "
                            + student.getName()
            );

        } catch (Exception e) {

            showError(
                    e.getMessage()
            );
        }
    }

    // -----------------------------
    // Calculate Percentage
    // -----------------------------

    private double calculatePercentage(
            AcademicReportRow row) {

        if (row.getMaxMarks() <= 0) {
            return 0.0;
        }

        return (
                row.getMarks()
                        / row.getMaxMarks()
        ) * 100.0;
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