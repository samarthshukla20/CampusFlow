package com.campusflow.ui;

import com.campusflow.model.Grade;
import com.campusflow.model.Student;
import com.campusflow.model.Subject;
import com.campusflow.model.User;
import com.campusflow.service.GradeService;
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

import java.util.List;

public class GradeManagementView {

    private final Stage stage;
    private final User currentUser;

    private final GradeService gradeService =
            new GradeService();

    private final StudentService studentService =
            new StudentService();

    private final SubjectService subjectService =
            new SubjectService();

    private final ObservableList<Grade> gradeList =
            FXCollections.observableArrayList();

    private final TableView<Grade> table =
            new TableView<>();

    private final ComboBox<Student> studentComboBox =
            new ComboBox<>();

    private final ComboBox<Subject> subjectComboBox =
            new ComboBox<>();

    private final TextField marksField =
            new TextField();

    private final TextField maxMarksField =
            new TextField();

    private final TextField creditsField =
            new TextField();

    private final Label percentageLabel =
            new Label("Percentage: --");

    private final Label gradeLabel =
            new Label("Grade: --");

    private final Label gpaLabel =
            new Label("GPA: --");

    public GradeManagementView(
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
                new Label("Grade Management");

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

        studentComboBox.setPrefWidth(220);

        subjectComboBox.setPromptText(
                "Select subject"
        );

        subjectComboBox.setPrefWidth(220);

        marksField.setPromptText(
                "Marks obtained"
        );

        maxMarksField.setPromptText(
                "Maximum marks"
        );

        creditsField.setPromptText(
                "Credits"
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
                new Label("Marks:"),
                0,
                1
        );

        form.add(
                marksField,
                1,
                1
        );

        form.add(
                new Label("Max Marks:"),
                2,
                1
        );

        form.add(
                maxMarksField,
                3,
                1
        );

        form.add(
                new Label("Credits:"),
                0,
                2
        );

        form.add(
                creditsField,
                1,
                2
        );

        // -----------------------------
        // Buttons
        // -----------------------------

        Button addButton =
                new Button("Add Grade");

        Button updateButton =
                new Button("Update");

        Button deleteButton =
                new Button("Delete");

        Button viewButton =
                new Button("View Grades");

        Button clearButton =
                new Button("Clear");

        HBox buttons =
                new HBox(10);

        buttons.setAlignment(
                Pos.CENTER_LEFT
        );

        buttons.getChildren().addAll(
                addButton,
                updateButton,
                deleteButton,
                viewButton,
                clearButton
        );

        // -----------------------------
        // Table
        // -----------------------------

        TableColumn<Grade, Number> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        TableColumn<Grade, Number> studentColumn =
                new TableColumn<>("Student ID");

        studentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getStudentId()
                        )
        );

        TableColumn<Grade, Number> subjectColumn =
                new TableColumn<>("Subject ID");

        subjectColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getSubjectId()
                        )
        );

        TableColumn<Grade, Number> marksColumn =
                new TableColumn<>("Marks");

        marksColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleDoubleProperty(
                                data.getValue().getMarks()
                        )
        );

        TableColumn<Grade, Number> maxMarksColumn =
                new TableColumn<>("Max Marks");

        maxMarksColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleDoubleProperty(
                                data.getValue().getMaxMarks()
                        )
        );

        TableColumn<Grade, Number> creditsColumn =
                new TableColumn<>("Credits");

        creditsColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getCredits()
                        )
        );

        table.getColumns().addAll(
                idColumn,
                studentColumn,
                subjectColumn,
                marksColumn,
                maxMarksColumn,
                creditsColumn
        );

        table.setItems(
                gradeList
        );

        // -----------------------------
        // Add Grade
        // -----------------------------

        addButton.setOnAction(event -> {

            try {

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

                double marks =
                        Double.parseDouble(
                                marksField.getText()
                        );

                double maxMarks =
                        Double.parseDouble(
                                maxMarksField.getText()
                        );

                int credits =
                        Integer.parseInt(
                                creditsField.getText()
                        );

                Grade grade =
                        new Grade(
                                student.getId(),
                                subject.getId(),
                                marks,
                                maxMarks,
                                credits
                        );

                if (gradeService.addGrade(
                        grade)) {

                    showMessage(
                            "Success",
                            "Grade added successfully."
                    );

                    clearForm();

                    loadGrades(
                            student.getId()
                    );

                    updateGPA(
                            student.getId()
                    );
                }

            } catch (NumberFormatException e) {

                showError(
                        "Marks, maximum marks and credits " +
                                "must contain valid numbers."
                );

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Update Grade
        // -----------------------------

        updateButton.setOnAction(event -> {

            Grade selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a grade to update."
                );

                return;
            }

            try {

                double marks =
                        Double.parseDouble(
                                marksField.getText()
                        );

                double maxMarks =
                        Double.parseDouble(
                                maxMarksField.getText()
                        );

                int credits =
                        Integer.parseInt(
                                creditsField.getText()
                        );

                Grade updatedGrade =
                        new Grade(
                                selected.getId(),
                                selected.getStudentId(),
                                selected.getSubjectId(),
                                marks,
                                maxMarks,
                                credits
                        );

                if (gradeService.updateGrade(
                        updatedGrade)) {

                    showMessage(
                            "Success",
                            "Grade updated successfully."
                    );

                    int studentId =
                            selected.getStudentId();

                    clearForm();

                    loadGrades(
                            studentId
                    );

                    updateGPA(
                            studentId
                    );
                }

            } catch (NumberFormatException e) {

                showError(
                        "Marks, maximum marks and credits " +
                                "must contain valid numbers."
                );

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Delete Grade
        // -----------------------------

        deleteButton.setOnAction(event -> {

            Grade selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a grade to delete."
                );

                return;
            }

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Delete Grade"
            );

            confirmation.setHeaderText(
                    "Delete this grade?"
            );

            confirmation.setContentText(
                    "This action cannot be undone."
            );

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                ButtonType.OK) {

                            try {

                                int studentId =
                                        selected.getStudentId();

                                if (gradeService.deleteGrade(
                                        studentId,
                                        selected.getSubjectId())) {

                                    showMessage(
                                            "Success",
                                            "Grade deleted successfully."
                                    );

                                    clearForm();

                                    loadGrades(
                                            studentId
                                    );

                                    updateGPA(
                                            studentId
                                    );
                                }

                            } catch (Exception e) {

                                showError(
                                        e.getMessage()
                                );
                            }
                        }
                    });
        });

        // -----------------------------
        // View Grades
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

            loadGrades(
                    student.getId()
            );

            updateGPA(
                    student.getId()
            );
        });

        // -----------------------------
        // Clear
        // -----------------------------

        clearButton.setOnAction(
                event -> clearForm()
        );

        // -----------------------------
        // Table Selection
        // -----------------------------

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {

                            if (newValue != null) {

                                studentComboBox.setDisable(
                                        true
                                );

                                subjectComboBox.setDisable(
                                        true
                                );

                                marksField.setText(
                                        String.valueOf(
                                                newValue.getMarks()
                                        )
                                );

                                maxMarksField.setText(
                                        String.valueOf(
                                                newValue.getMaxMarks()
                                        )
                                );

                                creditsField.setText(
                                        String.valueOf(
                                                newValue.getCredits()
                                        )
                                );

                                updateGradeInformation(
                                        newValue
                                );
                            }
                        }
                );

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

        HBox informationBox =
                new HBox(
                        25,
                        percentageLabel,
                        gradeLabel,
                        gpaLabel
                );

        informationBox.setAlignment(
                Pos.CENTER_LEFT
        );

        container.getChildren().addAll(
                header,
                form,
                buttons,
                informationBox,
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
    // Load Grades
    // -----------------------------

    private void loadGrades(
            int studentId) {

        List<Grade> grades =
                gradeService.getGradesForStudent(
                        studentId
                );

        gradeList.setAll(
                grades
        );
    }

    // -----------------------------
    // Update Grade Information
    // -----------------------------

    private void updateGradeInformation(
            Grade grade) {

        double percentage =
                gradeService.calculatePercentage(
                        grade
                );

        String letterGrade =
                gradeService.calculateLetterGrade(
                        grade
                );

        percentageLabel.setText(
                String.format(
                        "Percentage: %.2f%%",
                        percentage
                )
        );

        gradeLabel.setText(
                "Grade: " + letterGrade
        );
    }

    // -----------------------------
    // Update GPA
    // -----------------------------

    private void updateGPA(
            int studentId) {

        double gpa =
                gradeService.calculateGPA(
                        studentId
                );

        gpaLabel.setText(
                String.format(
                        "GPA: %.2f",
                        gpa
                )
        );
    }

    // -----------------------------
    // Clear Form
    // -----------------------------

    private void clearForm() {

        marksField.clear();
        maxMarksField.clear();
        creditsField.clear();

        studentComboBox.setDisable(
                false
        );

        subjectComboBox.setDisable(
                false
        );

        percentageLabel.setText(
                "Percentage: --"
        );

        gradeLabel.setText(
                "Grade: --"
        );

        table.getSelectionModel()
                .clearSelection();
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