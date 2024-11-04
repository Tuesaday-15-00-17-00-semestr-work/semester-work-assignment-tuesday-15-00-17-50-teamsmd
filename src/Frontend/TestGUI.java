package Frontend;
    
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class TestGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Label text = new Label("test");
            Button hello = new Button("helo");
            
            hello.setLayoutY(30);
            
            Pane root = new Pane();
            Scene scene = new Scene(root,400,400);
            
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            
            root.setStyle("-fx-background-color: grey;");
            
            root.getChildren().add(text);
            root.getChildren().add(hello);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}