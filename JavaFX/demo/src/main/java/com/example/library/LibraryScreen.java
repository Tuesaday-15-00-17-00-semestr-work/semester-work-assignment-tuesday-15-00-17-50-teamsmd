package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LibraryScreen {

    private MainApp mainApp;
    private BookService bookService = new BookService(); // Use BookService for API calls
    private ObservableList<BookEntity> borrowedBooksData = FXCollections.observableArrayList();

    public LibraryScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Your Borrowed Books");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<BookEntity> borrowedBooksTable = new TableView<>();
        borrowedBooksTable.setItems(borrowedBooksData);

        // Create columns for the table
        TableColumn<BookEntity, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BookEntity, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookEntity, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        borrowedBooksTable.getColumns().addAll(idColumn, titleColumn, authorColumn);

        // Refresh borrowed books
        refreshBorrowedBooks();

        Button refreshButton = new Button("Refresh");
        Button returnButton = new Button("Return");

        // Refresh action
        refreshButton.setOnAction(e -> refreshBorrowedBooks());

        // Return action
        returnButton.setOnAction(e -> {
            BookEntity selectedBook = borrowedBooksTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = bookService.returnBook(selectedBook.getId());
                if (success) {
                    borrowedBooksData.remove(selectedBook);
                    borrowedBooksTable.refresh();
                    showSuccess("You have returned \"" + selectedBook.getTitle() + "\"");
                } else {
                    showError("Failed to return book. Please try again.");
                }
            } else {
                showError("Please select a book to return!");
            }
        });

        layout.getChildren().addAll(titleLabel, borrowedBooksTable, refreshButton, returnButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    private void refreshBorrowedBooks() {
        System.out.println("Refreshing borrowed books...");
        int userId = 1;  // Replace with actual user ID from session or login logic
        List<BookEntity> books = bookService.fetchBorrowedBooks(userId);  // Pass userId here

        System.out.println("Fetched borrowed books: " + books);

        borrowedBooksData.setAll(books);
        System.out.println("Updated borrowed books list in table: " + borrowedBooksData);
    }


    public void addBorrowedBook(BookEntity book) {
        System.out.println("Adding borrowed book to My Books: " + book);
        borrowedBooksData.add(book);
        System.out.println("Borrowed books list now: " + borrowedBooksData);
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
