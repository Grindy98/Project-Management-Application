package scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistent.TestPersistent;
import persistent.TestPersistentSubclass;
import scene.controller.implementations.StartPageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainApp extends Application{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new RegistrationPageController().getScene();
        stage.setScene(scene);
        stage.show();

        TestPersistent t = new TestPersistent(5, "me");
        TestPersistent t2 = new TestPersistent(7, "me3");
        TestPersistent t3 = new TestPersistentSubclass(5, "me2", "fi21f9bwq");

        ArrayList<TestPersistent> arr = TestPersistent.load();
        System.out.println(arr);
        arr = new ArrayList<>(3);
        arr.add(t);
        arr.add(t2);
        arr.add(t3);
        System.out.println(arr);

        TestPersistent.save(arr);
    }

    public static void main(String[] args) {
        launch(args);
    }
}