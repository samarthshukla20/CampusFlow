package com.campusflow.ui;

import com.campusflow.model.User;
import com.campusflow.service.AuthenticationService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CampusFlowApplicationHelper {

    private final Stage stage;

    private final AuthenticationService authenticationService =
            new AuthenticationService();

    public CampusFlowApplicationHelper(Stage stage) {
        this.stage = stage;
    }

    public void showLogin() {

        Label title =
                new Label("CampusFlow");

        title.setStyle(
                "-fx-font-size: 28px; " +
                        "-fx-font-weight: bold;"
        );

        Label subtitle =
                new Label(
                        "Smart Campus Management System"
                );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Enter your email"
        );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Enter your password"
        );

        Button loginButton =
                new Button("Login");

        Label messageLabel =
                new Label();

        loginButton.setOnAction(event -> {

            try {

                User user =
                        authenticationService.login(
                                emailField.getText(),
                                passwordField.getText()
                        );

                showDashboard(user);

            } catch (Exception e) {

                messageLabel.setText(
                        "Login failed: " +
                                e.getMessage()
                );
            }
        });

        VBox root =
                new VBox(10);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        root.getChildren().addAll(
                title,
                subtitle,
                emailField,
                passwordField,
                loginButton,
                messageLabel
        );

        Scene scene =
                new Scene(
                        root,
                        900,
                        600
                );

        stage.setScene(scene);
    }

    private void showDashboard(User user) {

        DashboardView dashboardView =
                new DashboardView(stage);

        VBox dashboard =
                dashboardView.create(user);

        Scene scene =
                new Scene(
                        dashboard,
                        900,
                        600
                );

        stage.setScene(scene);
    }
}