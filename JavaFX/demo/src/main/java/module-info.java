module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;  // Add this line to use the HTTP client module

    exports com.example.library;
    opens com.example.library to javafx.fxml;
}
