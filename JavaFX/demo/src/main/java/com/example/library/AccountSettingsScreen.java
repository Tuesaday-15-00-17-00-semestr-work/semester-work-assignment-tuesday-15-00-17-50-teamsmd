package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AccountSettingsScreen {

    private MainApp mainApp;

    public AccountSettingsScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        // Create a table to display users
        TableView<UserEntity> userTable = new TableView<>();

        // Create columns for the table
        TableColumn<UserEntity, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserEntity, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<UserEntity, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add the columns to the table
        userTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn);

        // Fetch all users from the backend
        UserService userService = new UserService();
        List<UserEntity> users = userService.fetchAllUsers();

        // Add the fetched users to the table
        if (users != null && !users.isEmpty()) {
            userTable.getItems().addAll(users);
        } else {
            showError("No users found.");
        }

        // Create the Add New User button (only visible for admin)
        Button addNewUserButton = new Button("Add New User");
        addNewUserButton.setOnAction(e -> loadRegistrationScreen(stage));

        // Create the Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage));

        // Create a layout and add the table and buttons
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(userTable, addNewUserButton, backButton);

        // Set the content in the BorderPane center
        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(layout);

        // Set content in the main window
        mainApp.getBorderPane().setCenter(contentPane);
    }

    private void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    private boolean checkIfAdmin() {
        // Implement logic to check if the logged-in user is an admin
        String userRole = getCurrentUserRole(); // Placeholder method
        return userRole.equals("Admin");
    }

    private String getCurrentUserRole() {
        // Logic to retrieve the current user's role from session/JWT
        return "Admin"; // Placeholder for example
    }

    private void loadRegistrationScreen(Stage stage) {
        RegistrationScreen registrationScreen = new RegistrationScreen(mainApp);
        registrationScreen.start(stage); // Navigate to the RegistrationScreen
    }
}
