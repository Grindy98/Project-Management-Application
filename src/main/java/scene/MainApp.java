package scene;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.controller.implementations.RegistrationPageController;

import java.io.IOException;
import persistent.user.User;

public class MainApp extends Application{
    private static Stage stage;

    private static User loggedIn;

    private static ObservableList<User> users;

    @Override
    public void start(Stage stage) throws IOException {
        MainApp.stage = stage;

        // Initialization of variables
        loggedIn = null;

        // Initial scene
        changeToScene(SceneType.START);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        System.out.println(RegistrationPageController.memorizedUsers);
        User.save(RegistrationPageController.memorizedUsers);
    }

    public static void changeToScene(SceneType scene){
        stage.setScene(scene.getSceneController().getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}