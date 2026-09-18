package com.campusflow.ui;

import com.campusflow.model.Student;
import com.campusflow.model.User;
import com.campusflow.service.StudentService;

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

public class StudentManagementView {

    private final StudentService studentService =
            new StudentService();

    private final ObservableList<Student> studentList =
            FXCollections.observableArrayList();

    private final TableView<Student> table =
            new TableView<>();

    private final TextField nameField =
            new TextField();

    private final TextField emailField =
            new TextField();

    private final TextField enrollmentField =
            new TextField();

    private final TextField departmentField =
            new TextField();

    private final TextField semesterField =
            new TextField();

    private final TextField searchField =
            new TextField();

    // Used for navigation back to dashboard
    private final Stage stage;
    private final User currentUser;

    public StudentManagementView(Stage stage, User currentUser) {
        this.stage = stage;
        this.currentUser = currentUser;
    }

    public BorderPane create() {

        BorderPane root = new BorderPane();

        root.setPadding(new Insets(20));

        // -----------------------------
        // Title
        // -----------------------------
        Label title =
                new Label("Student Management");

        title.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;"
        );

        // -----------------------------
        // Back Button
        // -----------------------------
        Button backButton =
                new Button("← Back to Dashboard");

        backButton.setOnAction(event -> {

            DashboardView dashboardView =
                    new DashboardView(stage);

            VBox dashboard =
                    dashboardView.create(currentUser);

            Scene scene =
                    new Scene(dashboard, 900, 600);

            stage.setScene(scene);
        });

        HBox header =
                new HBox(15);

        header.setAlignment(Pos.CENTER_LEFT);

        header.getChildren().addAll(
                backButton,
                title
        );

        // -----------------------------
        // Search Bar
        // -----------------------------
        searchField.setPromptText(
                "Search by name, email, enrollment or department"
        );

        searchField.setPrefWidth(400);

        Button searchButton =
                new Button("Search");

        Button refreshButton =
                new Button("Refresh");

        HBox searchBar =
                new HBox(10);

        searchBar.setAlignment(Pos.CENTER_LEFT);

        searchBar.getChildren().addAll(
                searchField,
                searchButton,
                refreshButton
        );

        // -----------------------------
        // Table columns
        // -----------------------------
        TableColumn<Student, Number> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        TableColumn<Student, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getName()
                        )
        );

        TableColumn<Student, String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEmail()
                        )
        );

        TableColumn<Student, String> enrollmentColumn =
                new TableColumn<>("Enrollment");

        enrollmentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEnrollmentNumber()
                        )
        );

        TableColumn<Student, String> departmentColumn =
                new TableColumn<>("Department");

        departmentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getDepartment()
                        )
        );

        TableColumn<Student, Number> semesterColumn =
                new TableColumn<>("Semester");

        semesterColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getSemester()
                        )
        );

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                emailColumn,
                enrollmentColumn,
                departmentColumn,
                semesterColumn
        );

        table.setItems(studentList);

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

        nameField.setPromptText(
                "Student name"
        );

        emailField.setPromptText(
                "Email"
        );

        enrollmentField.setPromptText(
                "Enrollment number"
        );

        departmentField.setPromptText(
                "Department"
        );

        semesterField.setPromptText(
                "Semester"
        );

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(new Label("Email:"), 2, 0);
        form.add(emailField, 3, 0);

        form.add(new Label("Enrollment:"), 0, 1);
        form.add(enrollmentField, 1, 1);

        form.add(new Label("Department:"), 2, 1);
        form.add(departmentField, 3, 1);

        form.add(new Label("Semester:"), 0, 2);
        form.add(semesterField, 1, 2);

        // -----------------------------
        // Buttons
        // -----------------------------
        Button addButton =
                new Button("Add Student");

        Button updateButton =
                new Button("Update");

        Button deleteButton =
                new Button("Delete");

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
                clearButton
        );

        // -----------------------------
        // Search
        // -----------------------------
        searchButton.setOnAction(event -> {

            try {

                studentList.setAll(
                        studentService.searchStudents(
                                searchField.getText()
                        )
                );

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Refresh
        // -----------------------------
        refreshButton.setOnAction(event -> {

            searchField.clear();

            loadStudents();
        });

        // -----------------------------
        // Add Student
        // -----------------------------
        addButton.setOnAction(event -> {

            try {

                Student student =
                        createStudentFromForm();

                if (studentService.addStudent(
                        student)) {

                    showMessage(
                            "Success",
                            "Student added successfully."
                    );

                    clearForm();

                    loadStudents();
                }

            } catch (NumberFormatException e) {

                showError(
                        "Semester must be a number."
                );

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Update Student
        // -----------------------------
        updateButton.setOnAction(event -> {

            Student selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a student to update."
                );

                return;
            }

            try {

                Student updatedStudent =
                        new Student(
                                selected.getId(),
                                selected.getUserId(),
                                nameField.getText(),
                                emailField.getText(),
                                enrollmentField.getText(),
                                departmentField.getText(),
                                Integer.parseInt(
                                        semesterField.getText()
                                )
                        );

                if (studentService.updateStudent(
                        updatedStudent)) {

                    showMessage(
                            "Success",
                            "Student updated successfully."
                    );

                    clearForm();

                    loadStudents();
                }

            } catch (NumberFormatException e) {

                showError(
                        "Semester must be a number."
                );

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Delete Student
        // -----------------------------
        deleteButton.setOnAction(event -> {

            Student selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a student to delete."
                );

                return;
            }

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Delete Student"
            );

            confirmation.setHeaderText(
                    "Delete " +
                            selected.getName() +
                            "?"
            );

            confirmation.setContentText(
                    "This action cannot be undone."
            );

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                ButtonType.OK) {

                            try {

                                if (studentService.deleteStudent(
                                        selected.getId())) {

                                    showMessage(
                                            "Success",
                                            "Student deleted successfully."
                                    );

                                    clearForm();

                                    loadStudents();
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
        // Clear
        // -----------------------------
        clearButton.setOnAction(
                event -> clearForm()
        );

        // -----------------------------
        // Select table row
        // -----------------------------
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {

                            if (newValue != null) {

                                fillForm(newValue);
                            }
                        }
                );

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
                searchBar,
                form,
                buttons,
                table
        );

        root.setCenter(container);

        loadStudents();

        return root;
    }

    // -----------------------------
    // Create Student
    // -----------------------------
    private Student createStudentFromForm() {

        int semester =
                Integer.parseInt(
                        semesterField.getText()
                );

        return new Student(
                nameField.getText(),
                emailField.getText(),
                enrollmentField.getText(),
                departmentField.getText(),
                semester
        );
    }

    // -----------------------------
    // Fill Form
    // -----------------------------
    private void fillForm(
            Student student) {

        nameField.setText(
                student.getName()
        );

        emailField.setText(
                student.getEmail()
        );

        enrollmentField.setText(
                student.getEnrollmentNumber()
        );

        departmentField.setText(
                student.getDepartment()
        );

        semesterField.setText(
                String.valueOf(
                        student.getSemester()
                )
        );
    }

    // -----------------------------
    // Clear Form
    // -----------------------------
    private void clearForm() {

        nameField.clear();
        emailField.clear();
        enrollmentField.clear();
        departmentField.clear();
        semesterField.clear();

        table.getSelectionModel()
                .clearSelection();
    }

    // -----------------------------
    // Load Students
    // -----------------------------
    private void loadStudents() {

        studentList.setAll(
                studentService.getAllStudents()
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