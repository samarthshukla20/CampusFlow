package com.campusflow.ui;

import com.campusflow.model.Faculty;
import com.campusflow.model.Subject;
import com.campusflow.model.User;
import com.campusflow.service.FacultyService;
import com.campusflow.service.SubjectService;

import javafx.beans.property.SimpleIntegerProperty;
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

public class SubjectManagementView {

    private final Stage stage;
    private final User currentUser;

    private final SubjectService subjectService =
            new SubjectService();

    private final FacultyService facultyService =
            new FacultyService();

    private final ObservableList<Subject> subjectList =
            FXCollections.observableArrayList();

    private final ObservableList<Faculty> facultyList =
            FXCollections.observableArrayList();

    private final TableView<Subject> table =
            new TableView<>();

    private final TextField codeField =
            new TextField();

    private final TextField nameField =
            new TextField();

    private final ComboBox<Faculty> facultyComboBox =
            new ComboBox<>();

    public SubjectManagementView(
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
                new Label("Subject Management");

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

        codeField.setPromptText(
                "Subject code"
        );

        nameField.setPromptText(
                "Subject name"
        );

        facultyComboBox.setPromptText(
                "Select faculty"
        );

        facultyComboBox.setPrefWidth(220);

        form.add(
                new Label("Subject Code:"),
                0,
                0
        );

        form.add(
                codeField,
                1,
                0
        );

        form.add(
                new Label("Subject Name:"),
                2,
                0
        );

        form.add(
                nameField,
                3,
                0
        );

        form.add(
                new Label("Faculty:"),
                0,
                1
        );

        form.add(
                facultyComboBox,
                1,
                1
        );

        // -----------------------------
        // Buttons
        // -----------------------------

        Button addButton =
                new Button("Add Subject");

        Button updateButton =
                new Button("Update");

        Button deleteButton =
                new Button("Delete");

        Button refreshButton =
                new Button("Refresh");

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
                refreshButton,
                clearButton
        );

        // -----------------------------
        // Table Columns
        // -----------------------------

        TableColumn<Subject, Number> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        TableColumn<Subject, String> codeColumn =
                new TableColumn<>("Subject Code");

        codeColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getSubjectCode()
                        )
        );

        TableColumn<Subject, String> nameColumn =
                new TableColumn<>("Subject Name");

        nameColumn.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getSubjectName()
                        )
        );

        TableColumn<Subject, String> facultyColumn =
                new TableColumn<>("Faculty");

        facultyColumn.setCellValueFactory(
                data -> {

                    Integer facultyId =
                            data.getValue().getFacultyId();

                    if (facultyId == null) {

                        return new SimpleStringProperty(
                                "Not Assigned"
                        );
                    }

                    Faculty faculty =
                            findFacultyById(facultyId);

                    if (faculty == null) {

                        return new SimpleStringProperty(
                                "Faculty #" + facultyId
                        );
                    }

                    return new SimpleStringProperty(
                            faculty.getEmployeeId()
                                    + " - "
                                    + faculty.getName()
                    );
                }
        );

        table.getColumns().addAll(
                idColumn,
                codeColumn,
                nameColumn,
                facultyColumn
        );

        table.setItems(
                subjectList
        );

        // -----------------------------
        // Add Subject
        // -----------------------------

        addButton.setOnAction(event -> {

            try {

                Faculty selectedFaculty =
                        facultyComboBox.getValue();

                Integer facultyId =
                        selectedFaculty == null
                                ? null
                                : selectedFaculty.getId();

                Subject subject =
                        new Subject(
                                codeField.getText().trim(),
                                nameField.getText().trim(),
                                facultyId
                        );

                if (subjectService.addSubject(
                        subject)) {

                    showMessage(
                            "Success",
                            "Subject added successfully."
                    );

                    clearForm();
                    loadSubjects();

                } else {

                    showError(
                            "Unable to add subject."
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Update Subject
        // -----------------------------

        updateButton.setOnAction(event -> {

            Subject selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a subject to update."
                );

                return;
            }

            try {

                Faculty selectedFaculty =
                        facultyComboBox.getValue();

                Integer facultyId =
                        selectedFaculty == null
                                ? null
                                : selectedFaculty.getId();

                Subject updatedSubject =
                        new Subject(
                                selected.getId(),
                                codeField.getText().trim(),
                                nameField.getText().trim(),
                                facultyId
                        );

                if (subjectService.updateSubject(
                        updatedSubject)) {

                    showMessage(
                            "Success",
                            "Subject updated successfully."
                    );

                    clearForm();
                    loadSubjects();

                } else {

                    showError(
                            "Unable to update subject."
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Delete Subject
        // -----------------------------

        deleteButton.setOnAction(event -> {

            Subject selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a subject to delete."
                );

                return;
            }

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Delete Subject"
            );

            confirmation.setHeaderText(
                    "Delete " +
                            selected.getSubjectName() +
                            "?"
            );

            confirmation.setContentText(
                    "This will also remove attendance and "
                            + "grade records associated with this subject."
            );

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                ButtonType.OK) {

                            try {

                                if (subjectService.deleteSubject(
                                        selected.getId())) {

                                    showMessage(
                                            "Success",
                                            "Subject deleted successfully."
                                    );

                                    clearForm();
                                    loadSubjects();

                                } else {

                                    showError(
                                            "Unable to delete subject."
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
        // Refresh
        // -----------------------------

        refreshButton.setOnAction(event -> {

            clearForm();

            loadFaculty();
            loadSubjects();
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

                                codeField.setText(
                                        newValue.getSubjectCode()
                                );

                                nameField.setText(
                                        newValue.getSubjectName()
                                );

                                Integer facultyId =
                                        newValue.getFacultyId();

                                if (facultyId == null) {

                                    facultyComboBox
                                            .getSelectionModel()
                                            .clearSelection();

                                } else {

                                    Faculty faculty =
                                            findFacultyById(
                                                    facultyId
                                            );

                                    facultyComboBox.setValue(
                                            faculty
                                    );
                                }
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
                form,
                buttons,
                table
        );

        root.setCenter(
                container
        );

        loadFaculty();
        loadSubjects();

        return root;
    }

    // -----------------------------
    // Load Faculty
    // -----------------------------

    private void loadFaculty() {

        facultyList.setAll(
                facultyService.getAllFaculty()
        );

        facultyComboBox.setItems(
                facultyList
        );

        facultyComboBox.setCellFactory(
                listView ->
                        new ListCell<>() {

                            @Override
                            protected void updateItem(
                                    Faculty faculty,
                                    boolean empty) {

                                super.updateItem(
                                        faculty,
                                        empty
                                );

                                if (empty ||
                                        faculty == null) {

                                    setText(null);

                                } else {

                                    setText(
                                            faculty.getEmployeeId()
                                                    + " - "
                                                    + faculty.getName()
                                    );
                                }
                            }
                        }
        );

        facultyComboBox.setButtonCell(
                new ListCell<>() {

                    @Override
                    protected void updateItem(
                            Faculty faculty,
                            boolean empty) {

                        super.updateItem(
                                faculty,
                                empty
                        );

                        if (empty ||
                                faculty == null) {

                            setText(null);

                        } else {

                            setText(
                                    faculty.getEmployeeId()
                                            + " - "
                                            + faculty.getName()
                            );
                        }
                    }
                }
        );
    }

    // -----------------------------
    // Load Subjects
    // -----------------------------

    private void loadSubjects() {

        subjectList.setAll(
                subjectService.getAllSubjects()
        );

        table.refresh();
    }

    // -----------------------------
    // Find Faculty
    // -----------------------------

    private Faculty findFacultyById(
            int facultyId) {

        for (Faculty faculty : facultyList) {

            if (faculty.getId() == facultyId) {

                return faculty;
            }
        }

        return null;
    }

    // -----------------------------
    // Clear Form
    // -----------------------------

    private void clearForm() {

        codeField.clear();
        nameField.clear();

        facultyComboBox
                .getSelectionModel()
                .clearSelection();

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