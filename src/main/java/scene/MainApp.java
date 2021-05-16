package scene;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistent.Project;
import persistent.Task;
import persistent.exception.ProjectValidationFailedException;
import persistent.user.User;
import scene.controller.SceneController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainApp extends Application{
    private static Stage stage;
    
    private static User loggedIn;

    @Override
    public void start(Stage stage) throws IOException {
        MainApp.stage = stage;

        loggedIn = null;

        // Initialize parameters
        load();
        loggedIn = null;

        // Initial scene
        changeToScene(SceneType.START);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        save();
    }

    public static void load(){
        // Load from memory
        Project.load();
        User.load();
    }

    public static void save(){
        // Save to memory
        Project.save();
        User.save();
    }

    public static User getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(User loggedIn) {
        MainApp.loggedIn = loggedIn;
    }

    public static Stage createPopup(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(stage);
        return popup;
    }

    private static void setMinSizeStage(Stage stage, int minWidth, int minHeight){
        stage.widthProperty().addListener((o, oldValue, newValue)->{
            if(newValue.intValue() < minWidth) {
                stage.setWidth(minWidth);
            }
        });
        stage.heightProperty().addListener((o, oldValue, newValue)->{
            if(newValue.intValue() < minHeight) {
                stage.setHeight(minHeight);
            }
        });
    }

    public static SceneController changeToScene(SceneType scene){
        SceneController controller = scene.getSceneController();
        stage.setScene(controller.getScene());
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}