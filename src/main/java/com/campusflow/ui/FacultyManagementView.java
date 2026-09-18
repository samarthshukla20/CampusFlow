package com.campusflow.ui;

import com.campusflow.model.Faculty;
import com.campusflow.model.User;
import com.campusflow.service.FacultyService;

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

public class FacultyManagementView {

    private final Stage stage;
    private final User currentUser;

    private final FacultyService facultyService =
            new FacultyService();

    private final ObservableList<Faculty> facultyList =
            FXCollections.observableArrayList();

    private final TableView<Faculty> table =
            new TableView<>();

    private final TextField nameField =
            new TextField();

    private final TextField emailField =
            new TextField();

    private final TextField employeeIdField =
            new TextField();

    private final TextField departmentField =
            new TextField();

    private final TextField searchField =
            new TextField();

    public FacultyManagementView(
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
                new Label("Faculty Management");

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
        // Search
        // -----------------------------

        searchField.setPromptText(
                "Search by name, email, employee ID or department"
        );

        searchField.setPrefWidth(400);

        Button searchButton =
                new Button("Search");

        Button refreshButton =
                new Button("Refresh");

        HBox searchBar =
                new HBox(10);

        searchBar.setAlignment(
                Pos.CENTER_LEFT
        );

        searchBar.getChildren().addAll(
                searchField,
                searchButton,
                refreshButton
        );

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
                "Faculty name"
        );

        emailField.setPromptText(
                "Email"
        );

        employeeIdField.setPromptText(
                "Employee ID"
        );

        departmentField.setPromptText(
                "Department"
        );

        form.add(
                new Label("Name:"),
                0,
                0
        );

        form.add(
                nameField,
                1,
                0
        );

        form.add(
                new Label("Email:"),
                2,
                0
        );

        form.add(
                emailField,
                3,
                0
        );

        form.add(
                new Label("Employee ID:"),
                0,
                1
        );

        form.add(
                employeeIdField,
                1,
                1
        );

        form.add(
                new Label("Department:"),
                2,
                1
        );

        form.add(
                departmentField,
                3,
                1
        );

        // -----------------------------
        // Buttons
        // -----------------------------

        Button addButton =
                new Button("Add Faculty");

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
        // Table Columns
        // -----------------------------

        TableColumn<Faculty, Number> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        TableColumn<Faculty, String> employeeIdColumn =
                new TableColumn<>("Employee ID");

        employeeIdColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEmployeeId()
                        )
        );

        TableColumn<Faculty, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getName()
                        )
        );

        TableColumn<Faculty, String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getEmail()
                        )
        );

        TableColumn<Faculty, String> departmentColumn =
                new TableColumn<>("Department");

        departmentColumn.setCellValueFactory(
                data ->
                        new javafx.beans.property.SimpleStringProperty(
                                data.getValue().getDepartment()
                        )
        );

        table.getColumns().addAll(
                idColumn,
                employeeIdColumn,
                nameColumn,
                emailColumn,
                departmentColumn
        );

        table.setItems(
                facultyList
        );

        // -----------------------------
        // Search
        // -----------------------------

        searchButton.setOnAction(event -> {

            try {

                facultyList.setAll(
                        facultyService.searchFaculty(
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

            clearForm();

            loadFaculty();
        });

        // -----------------------------
        // Add Faculty
        // -----------------------------

        addButton.setOnAction(event -> {

            try {

                Faculty faculty =
                        new Faculty(
                                nameField.getText().trim(),
                                emailField.getText().trim(),
                                employeeIdField.getText().trim(),
                                departmentField.getText().trim()
                        );

                if (facultyService.addFaculty(
                        faculty)) {

                    showMessage(
                            "Success",
                            "Faculty added successfully."
                    );

                    clearForm();

                    loadFaculty();

                } else {

                    showError(
                            "Unable to add faculty."
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Update Faculty
        // -----------------------------

        updateButton.setOnAction(event -> {

            Faculty selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a faculty member to update."
                );

                return;
            }

            try {

                Faculty updatedFaculty =
                        new Faculty(
                                selected.getId(),
                                selected.getUserId(),
                                nameField.getText().trim(),
                                emailField.getText().trim(),
                                employeeIdField.getText().trim(),
                                departmentField.getText().trim()
                        );

                if (facultyService.updateFaculty(
                        updatedFaculty)) {

                    showMessage(
                            "Success",
                            "Faculty updated successfully."
                    );

                    clearForm();

                    loadFaculty();

                } else {

                    showError(
                            "Unable to update faculty."
                    );
                }

            } catch (Exception e) {

                showError(
                        e.getMessage()
                );
            }
        });

        // -----------------------------
        // Delete Faculty
        // -----------------------------

        deleteButton.setOnAction(event -> {

            Faculty selected =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (selected == null) {

                showError(
                        "Select a faculty member to delete."
                );

                return;
            }

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Delete Faculty"
            );

            confirmation.setHeaderText(
                    "Delete " +
                            selected.getName() +
                            "?"
            );

            confirmation.setContentText(
                    "The faculty member will be removed "
                            + "from the system. Subjects assigned "
                            + "to this faculty member will become "
                            + "unassigned."
            );

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                ButtonType.OK) {

                            try {

                                if (facultyService.deleteFaculty(
                                        selected.getId())) {

                                    showMessage(
                                            "Success",
                                            "Faculty deleted successfully."
                                    );

                                    clearForm();

                                    loadFaculty();

                                } else {

                                    showError(
                                            "Unable to delete faculty."
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

                                nameField.setText(
                                        newValue.getName()
                                );

                                emailField.setText(
                                        newValue.getEmail()
                                );

                                employeeIdField.setText(
                                        newValue.getEmployeeId()
                                );

                                departmentField.setText(
                                        newValue.getDepartment()
                                );
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

        root.setCenter(
                container
        );

        loadFaculty();

        return root;
    }

    // -----------------------------
    // Load Faculty
    // -----------------------------

    private void loadFaculty() {

        facultyList.setAll(
                facultyService.getAllFaculty()
        );
    }

    // -----------------------------
    // Clear Form
    // -----------------------------

    private void clearForm() {

        nameField.clear();
        emailField.clear();
        employeeIdField.clear();
        departmentField.clear();

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