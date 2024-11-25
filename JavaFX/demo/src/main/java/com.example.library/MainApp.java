package com.example.library;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    private BorderPane borderPane = new BorderPane();  // Main layout for all screens
    private TransactionManager transactionManager = new TransactionManager(); // Manage transactions
    private boolean isMenuCollapsed = false;  // Track the state of the menu (collapsed or expanded)

    @Override
    public void start(Stage stage) {
        // Create the side menu (navigation bar)
        VBox sideMenu = createSideMenu(stage);
        borderPane.setLeft(sideMenu);

        // Initially load the home screen
        loadHomeScreen(stage);

        // Set up the scene
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Library Management System");
        stage.show();
    }

    private VBox createSideMenu(Stage stage) {
        VBox sideMenu = new VBox(15);
        sideMenu.setStyle("-fx-padding: 20px; -fx-background-color: lightgray;");
        sideMenu.setPrefWidth(isMenuCollapsed ? 60 : 300); // Adjust side menu width based on collapse state
        sideMenu.setMaxWidth(250);

        // Toggle button to collapse/expand the side menu
        ToggleButton toggleButton = new ToggleButton("☰");  // Hamburger menu icon
        toggleButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent;");
        toggleButton.setOnAction(e -> toggleMenu(sideMenu));

        // Create buttons with labels (for text control)
        Button homeButton = createMenuButton("Home");
        Button loginButton = createMenuButton("Login");
        Button registerButton = createMenuButton("Register");
        Button transactionButton = createMenuButton("Transaction History");
        Button settingsButton = createMenuButton("Account Settings");
        Button bookLendingButton = createMenuButton("Library");
        Button libraryButton = createMenuButton("My Books");

        // Button actions
        homeButton.setOnAction(e -> loadHomeScreen(stage));
        loginButton.setOnAction(e -> loadLoginScreen(stage));
        registerButton.setOnAction(e -> loadRegistrationScreen(stage));
        transactionButton.setOnAction(e -> loadTransactionHistoryScreen(stage));
        settingsButton.setOnAction(e -> loadAccountSettingsScreen(stage));
        bookLendingButton.setOnAction(e -> loadBookLendingScreen(stage));
        libraryButton.setOnAction(e -> loadLibraryScreen(stage));

        // Add the toggle button at the top and buttons to the menu
        sideMenu.getChildren().addAll(toggleButton, homeButton, loginButton, registerButton, transactionButton, settingsButton, bookLendingButton, libraryButton);

        // Create the Logout button with confirmation dialog (styled like other buttons)
        Button logoutButton = createMenuButton("Logout");
        logoutButton.setOnAction(e -> {
            // Logout confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Logout");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    loadHomeScreen(stage);  // Navigate back to the home screen or login page
                }
            });
        });

        // Create the Close button with confirmation dialog (styled like other buttons)
        Button closeButton = createMenuButton("Close");
        closeButton.setOnAction(e -> {
            // Close confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    stage.close();  // Close the application if confirmed
                }
            });
        });

        // Create an HBox for the Close button, aligned to the bottom-left corner
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align it to the bottom-left corner

        // Create a region to take up remaining space and push the close button down
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);  // Make sure the spacer takes up the remaining space

        // Add the spacer, logout button, and close button
        sideMenu.getChildren().addAll(spacer, logoutButton, closeButtonBox);

        return sideMenu;
    }

    // Create a smaller button with a label to manage the text visibility when collapsed
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);  // Smaller width
        button.setStyle("-fx-font-size: 12px; -fx-padding: 8px 15px; -fx-background-color: lightblue; -fx-text-fill: black;");  // Smaller padding and font size
        return button;
    }

    // Toggle the side menu between collapsed and expanded states
    private void toggleMenu(VBox sideMenu) {
        isMenuCollapsed = !isMenuCollapsed;
        double targetWidth = isMenuCollapsed ? 60 : 200; // Target width for collapsed/expanded state

        // Animation for smooth transition
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300), e -> sideMenu.setPrefWidth(targetWidth))
        );
        timeline.play();

        // Hide or show button labels when collapsing/expanding the menu
        for (javafx.scene.Node node : sideMenu.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Label label = (Label) button.getGraphic();  // Get the label inside the button
                if (label != null) {
                    label.setVisible(!isMenuCollapsed);  // Hide text when menu is collapsed
                }
            }
        }
    }


    // Home screen
    public void loadHomeScreen(Stage stage) {
        Label homeLabel = new Label("Welcome to the Library Management System!");
        homeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(homeLabel);
        borderPane.setCenter(contentPane);
    }

    private void loadLoginScreen(Stage stage) {
        LoginScreen loginScreen = new LoginScreen(this);
        loginScreen.start(stage);
    }

    private void loadRegistrationScreen(Stage stage) {
        RegistrationScreen registrationScreen = new RegistrationScreen(this);
        registrationScreen.start(stage);
    }

    private void loadBookLendingScreen(Stage stage) {
        BookLendingScreen bookLendingScreen = new BookLendingScreen(this);
        bookLendingScreen.start(stage);
    }

    private void loadTransactionHistoryScreen(Stage stage) {
        TransactionHistoryScreen transactionHistoryScreen = new TransactionHistoryScreen(this);
        transactionHistoryScreen.start(stage);
    }

    private void loadLibraryScreen(Stage stage) {
        LibraryScreen libraryScreen = new LibraryScreen(this);
        libraryScreen.start(stage);
    }

    private void loadAccountSettingsScreen(Stage stage) {
        Label settingsLabel = new Label("Account Settings Screen");
        settingsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(settingsLabel);
        borderPane.setCenter(contentPane);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
