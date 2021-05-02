package scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scene.controller.implementations.MainPageController;
import scene.controller.implementations.StartPageController;
import scene.controller.implementations.TestList;
import scene.controller.implementations.popups.CreateProjectPopup;

import java.io.IOException;

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
        new CreateProjectPopup();
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