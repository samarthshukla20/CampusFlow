package com.campusflow;

import com.campusflow.ui.CampusFlowApplicationHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class CampusFlowApplication extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle(
                "CampusFlow - Smart Campus Management System"
        );

        CampusFlowApplicationHelper helper =
                new CampusFlowApplicationHelper(stage);

        helper.showLogin();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}