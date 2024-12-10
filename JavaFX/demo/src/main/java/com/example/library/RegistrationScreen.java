package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RegistrationScreen {

    private MainApp mainApp; // Reference to MainApp
    private AuthService authService;  // Authentication service

    // Constructor
    public RegistrationScreen(MainApp mainApp) {
        this.mainApp = mainApp;
        this.authService = new AuthService();  // Initialize the AuthService
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

        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(200); // Set preferred width
        confirmPasswordField.setMaxWidth(200);
        confirmPasswordField.setPromptText("Confirm Password");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPrefWidth(200); // Set preferred width
        emailField.setMaxWidth(200);
        emailField.setPromptText("Email");

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = emailField.getText();

            // Validate input
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
                showError("All fields must be filled!");
            } else if (!password.equals(confirmPassword)) {
                showError("Passwords do not match!");
            } else if (!email.contains("@") || !email.contains(".")) {
                showError("Invalid email format.");
            } else {
                // Call the registration method with role set to 1 (user)
                String result = authService.register(username, password, username, email);
                showSuccess(result);  // Show success message from the response
                mainApp.loadHomeScreen(stage);  // Load home screen after successful registration
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage)); // Navigate back to Home screen

        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,
                confirmPasswordLabel, confirmPasswordField, emailLabel, emailField, registerButton, backButton);

        // Set the content in the BorderPane center
        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(layout);

        // Set content in the main window by accessing mainApp.getBorderPane()
        mainApp.getBorderPane().setCenter(contentPane); // Now, it should work as expected
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