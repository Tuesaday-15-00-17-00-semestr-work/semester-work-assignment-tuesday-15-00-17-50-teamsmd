package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        TableColumn<UserEntity, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(col -> new TableCell<UserEntity, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    UserEntity selectedUser = getTableView().getItems().get(getIndex());
                    deleteUser(selectedUser);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Add the columns to the table
        userTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn, deleteColumn);

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

    // Delete the user and update the table
    private void deleteUser(UserEntity user) {
        UserService userService = new UserService();
        boolean isDeleted = userService.deleteUser(user.getUserId());
        if (isDeleted) {
            // Remove the user from the TableView after deletion
            refreshUserTable();
            showSuccess("User deleted successfully.");
        } else {
            showError("Error deleting user.");
        }
    }

    // Refresh the user table to reflect the deletion
    private void refreshUserTable() {
        UserService userService = new UserService();
        List<UserEntity> users = userService.fetchAllUsers();
        if (users != null) {
            TableView<UserEntity> userTable = new TableView<>();
            userTable.getItems().clear();
            userTable.getItems().addAll(users);
        }
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

    private void loadRegistrationScreen(Stage stage) {
        RegistrationScreen registrationScreen = new RegistrationScreen(mainApp);
        registrationScreen.start(stage); // Navigate to the RegistrationScreen
    }
}
