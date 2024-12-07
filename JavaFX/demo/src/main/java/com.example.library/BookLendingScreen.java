package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookLendingScreen {

    private MainApp mainApp;  // Reference to MainApp
    private BookService bookService = new BookService(); // Use BookService for API calls

    public BookLendingScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Available Books for Lending");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> bookListView = new ListView<>();
        Button borrowButton = new Button("Borrow");
        Button backButton = new Button("Back");
        Button addBookButton = new Button("Add New Book");

        // Fetch available books
        fetchAvailableBooks(bookListView);

        // Borrow action
        borrowButton.setOnAction(e -> {
            String selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = bookService.borrowBook(selectedBook);
                if (success) {
                    bookListView.getItems().remove(selectedBook);
                    showSuccess("You have borrowed \"" + selectedBook + "\"");
                } else {
                    showError("Failed to borrow book. Please try again.");
                }
            } else {
                showError("Please select a book to borrow!");
            }
        });

        // Back action
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage));

        // Add new book action
        addBookButton.setOnAction(e -> showAddBookPopup(stage));

        layout.getChildren().addAll(titleLabel, bookListView, borrowButton, addBookButton, backButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    // Fetch available books from backend
    private void fetchAvailableBooks(ListView<String> bookListView) {
        String response = bookService.fetchAllBooks();
        if (!response.equals("[]")) {
            String[] books = response.replace("[", "").replace("]", "").replace("\"", "").split(",");
            bookListView.getItems().addAll(books);
        } else {
            showError("Failed to fetch books or no books available.");
        }
    }

    // Show error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show success message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAddBookPopup(Stage stage) {
        VBox addBookLayout = new VBox(10);
        addBookLayout.setStyle("-fx-padding: 20;");
        addBookLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add New Book");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Book Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter book title");

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author's name");

        Button submitButton = new Button("Add Book");
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            if (title.isEmpty() || author.isEmpty()) {
                showError("All fields must be filled!");
            } else {
                // Automatically set ISBN to 0 and available copies to 1
                boolean success = bookService.addBook(title, author);

                if (success) {
                    showSuccess("Book added successfully!");
                    stage.close();  // Close the add book popup
                } else {
                    showError("Failed to add book. Please try again.");
                }
            }
        });

        addBookLayout.getChildren().addAll(titleLabel, nameLabel, titleField, authorLabel, authorField, submitButton);

        // Create a scene and show the popup window
        Scene addBookScene = new Scene(addBookLayout, 300, 250);
        Stage addBookStage = new Stage();
        addBookStage.setScene(addBookScene);
        addBookStage.setTitle("Add New Book");
        addBookStage.show();
    }
}
