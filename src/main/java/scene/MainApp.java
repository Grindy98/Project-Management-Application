package scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistent.TestPersistent;
import persistent.TestPersistentSubclass;
import persistent.service.ListSave;
import scene.controller.implementations.StartPageController;

import java.io.IOException;

public class MainApp extends Application{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new StartPageController().getScene();
        stage.setScene(scene);
        stage.show();

        TestPersistent t = new TestPersistent(5, "me");
        TestPersistent t2 = new TestPersistentSubclass(5, "me2", "fi21f9bwq");

        ListSave<TestPersistent> list = new ListSave<>(TestPersistent.class, "testPersistent",
                "/save_models/empty_save.json");
        list.add(t);
        list.add(t2);
        list.save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}