package com.example.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeScreen extends Application {

    private MainApp mainApp;  // Reference to MainApp

    public HomeScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void start(Stage stage) {
        Label homeLabel = new Label("Welcome to the Library Management System!");
        homeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(homeLabel);
        mainApp.getBorderPane().setCenter(contentPane);

        Scene scene = new Scene(contentPane, 800, 600);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
