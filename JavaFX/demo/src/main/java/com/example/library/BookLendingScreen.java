package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class BookLendingScreen {

    private MainApp mainApp;
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

        TableView<BookEntity> bookTable = new TableView<>();

        // Create columns for the table
        TableColumn<BookEntity, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BookEntity, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookEntity, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookEntity, Integer> availableCopiesColumn = new TableColumn<>("Available Copies");
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        bookTable.getColumns().addAll(idColumn, titleColumn, authorColumn, availableCopiesColumn);

        // Fetch available books
        fetchAvailableBooks(bookTable);

        Button borrowButton = new Button("Borrow");
        Button refreshButton = new Button("Refresh");
        Button addBookButton = new Button("Add New Book");

        // Borrow action
        borrowButton.setOnAction(e -> {
            BookEntity selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                int userId = 1; // Replace with actual user ID from login or session

                boolean success = bookService.borrowBook(userId, selectedBook.getId());
                if (success) {
                    selectedBook.setAvailableCopies(selectedBook.getAvailableCopies() - 1);
                    bookTable.refresh();
                    showSuccess("You have borrowed \"" + selectedBook.getTitle() + "\"");
                } else {
                    showError("Failed to borrow book. Please try again.");
                }
            } else {
                showError("Please select a book to borrow!");
            }
        });

        // Refresh action
        refreshButton.setOnAction(e -> fetchAvailableBooks(bookTable));

        // Add new book action
        addBookButton.setOnAction(e -> showAddBookPopup(stage));

        layout.getChildren().addAll(titleLabel, bookTable, borrowButton, refreshButton, addBookButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    private void fetchAvailableBooks(TableView<BookEntity> bookTable) {
        List<BookEntity> books = bookService.fetchAllBooks();
        ObservableList<BookEntity> bookData = FXCollections.observableArrayList(books);
        bookTable.setItems(bookData);
    }

    private void showAddBookPopup(Stage stage) {
        AddBookPopup addBookPopup = new AddBookPopup(mainApp);
        addBookPopup.showPopup(stage);
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