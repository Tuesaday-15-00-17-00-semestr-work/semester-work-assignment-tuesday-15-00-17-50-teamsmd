package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {

    private MainApp mainApp; // Reference to MainApp
    private AuthService authService = new AuthService(); // Reference to AuthService

    // Constructor
    public LoginScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    public void start(Stage stage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER); // Center all elements

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(200); // Set preferred width
        usernameField.setMaxWidth(200);
        usernameField.setPromptText("Username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200); // Set preferred width
        passwordField.setMaxWidth(200);
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Call AuthService to perform login
            String result = authService.login(username, password);
            if (result.equals("User logged in!")) {
                showSuccess(result);
                mainApp.loadHomeScreen(stage);  // Load home screen on successful login
            } else {
                showError(result);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage)); // Navigate back to Home screen

        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);

        // Set the content in the BorderPane center
        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(layout);

        // Set content in the main window
        mainApp.getBorderPane().setCenter(contentPane);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}