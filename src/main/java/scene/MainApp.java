package scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.controller.implementations.TestPageController;

import java.io.IOException;

public class MainApp extends Application{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new TestPageController().getScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}