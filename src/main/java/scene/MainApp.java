package scene;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistent.Project;
import scene.controller.implementations.MainPageController;
import scene.controller.implementations.popups.ProjectCreatePopup;

import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application{
    private static Stage stage;

    private static Scene scene;
    private static Stage stage;
    
    private static User loggedIn;

    private static ObservableList<Project> projects;
    private static ObservableList<User> users;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        setMinSizeStage(stage, 400, 300);

        load();

        scene = new MainPageController().getScene();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        save();
    }

    public static void load(){
        // Load from memory
        projects = FXCollections.observableArrayList(Project.load());
    }

    public static void save(){
        // Save to memory
        Project.save(projects);
    }

    public static ObservableList<Project> getProjects() {
        return projects;
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
                stage.setResizable(false);
                stage.setWidth(minWidth);
                stage.setResizable(true);
            }
        });
        stage.heightProperty().addListener((o, oldValue, newValue)->{
            if(newValue.intValue() < minHeight) {
                stage.setResizable(false);
                stage.setHeight(minHeight);
                stage.setResizable(true);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}