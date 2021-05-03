package scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistent.TestPersistent;
import persistent.TestPersistentSubclass;
import scene.controller.implementations.MainPageController;
import scene.controller.implementations.StartPageController;
import scene.controller.implementations.TestList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainApp extends Application{

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        setMinSizeStage(stage);

        scene = new MainPageController(50).getScene();
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

    public static Stage createPopup(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(stage);
        return popup;
    }

    private static void setMinSizeStage(Stage stage){
        stage.widthProperty().addListener((o, oldValue, newValue)->{
            if(newValue.intValue() < 400.0) {
                stage.setResizable(false);
                stage.setWidth(400);
                stage.setResizable(true);
            }
        });
        stage.heightProperty().addListener((o, oldValue, newValue)->{
            if(newValue.intValue() < 300.0) {
                stage.setResizable(false);
                stage.setHeight(300);
                stage.setResizable(true);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}