package scene.controller;

import javafx.scene.Scene;

public abstract class SceneController extends FXMLController {
    protected Scene scene;

    public SceneController(String FXMLResourceString, double sceneWidth, double sceneHeight) {
        super(FXMLResourceString);
        scene = new Scene(getRoot(), sceneWidth, sceneHeight);
    }

    public Scene getScene() {
        return scene;
    }
}
