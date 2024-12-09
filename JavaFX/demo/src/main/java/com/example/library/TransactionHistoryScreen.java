package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class TransactionHistoryScreen {

    private MainApp mainApp;
    private TransactionService transactionService = new TransactionService(); // Use TransactionService for API calls

    public TransactionHistoryScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }



    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> transactionListView = new ListView<>();
        fetchTransactions(transactionListView);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage));

        layout.getChildren().addAll(titleLabel, transactionListView, backButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    private void fetchTransactions(ListView<String> transactionListView) {
        String response = transactionService.fetchAllTransactions();
        if (!response.equals("[]")) {
            String[] transactions = response.replace("[", "").replace("]", "").replace("\"", "").split(",");
            transactionListView.getItems().addAll(Arrays.asList(transactions));
        } else {
            showError("Failed to fetch transactions or no transactions recorded.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
