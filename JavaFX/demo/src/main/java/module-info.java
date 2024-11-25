module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.library to javafx.fxml; // Allow reflection access for FXML
    exports com.example.library;             // Exports library classes for external use
}
