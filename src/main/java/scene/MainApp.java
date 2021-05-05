package scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.controller.implementations.RegistrationPageController;

import java.io.IOException;

import persistent.user.ProjectManager;
import persistent.user.TeamMember;
import persistent.user.User;

public class MainApp extends Application{

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        MainApp.stage = stage;
        changeToScene(SceneType.LOGIN);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        System.out.println(RegistrationPageController.memorizedUsers);
        User.save(RegistrationPageController.memorizedUsers);
    }

    public void changeToScene(SceneType scene){
        stage.setScene(scene.getSceneController().getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}