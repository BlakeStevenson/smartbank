package smartbank;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        TextField input = new TextField();
        Label out = new Label();
        Button go = new Button("Greet");
        go.setOnAction(e -> out.setText("Hi, " + input.getText() + "!"));

        VBox root = new VBox(10, new Label("Name:"), input, go, out);
        Scene scene = new Scene(root, 320, 180);
        stage.setTitle("JavaFX Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
