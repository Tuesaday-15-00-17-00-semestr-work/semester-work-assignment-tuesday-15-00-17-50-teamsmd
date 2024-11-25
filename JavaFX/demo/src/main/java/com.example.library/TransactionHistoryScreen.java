package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionHistoryScreen {

    private MainApp mainApp; // Reference to MainApp

    // Constructor
    public TransactionHistoryScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ListView to display the transactions
        ListView<String> transactionListView = new ListView<>();

        // Get the transactions from the TransactionManager
        for (Transaction transaction : mainApp.getTransactionManager().getTransactions()) {
            String transactionDetails = transaction.getAction() + " - " + transaction.getBookTitle() + " on " + transaction.getDate();
            transactionListView.getItems().add(transactionDetails);
        }

        // Back button to return to the Home screen
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage));

        layout.getChildren().addAll(titleLabel, transactionListView, backButton);

        // Set content in the main window by accessing `borderPane` from `mainApp`
        mainApp.getBorderPane().setCenter(layout);
    }
}
