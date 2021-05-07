package scene;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistent.Project;
import persistent.exception.ProjectValidationFailedException;
import persistent.user.User;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application{
    private static Stage stage;
    
    public static User loggedIn;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        // Initialize parameters
        load();
        loggedIn = null;

        // Initial scene
        changeToScene(SceneType.MAIN_PAGE);
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

    public static void changeToScene(SceneType scene){
        stage.setScene(scene.getSceneController().getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}