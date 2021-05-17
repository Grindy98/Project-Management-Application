package scene.controller;

import javafx.css.Stylesheet;
import javafx.scene.Scene;

public abstract class SceneController extends FXMLController {
    protected final Scene scene;

    private static final String defaultStylePath = "/css/default.css";

    protected SceneController(String FXMLResourceString, double sceneWidth, double sceneHeight) {
        super(FXMLResourceString);
        scene = new Scene(getRoot(), sceneWidth, sceneHeight);
        // Add default css
        scene.getStylesheets().add(getClass().getResource(defaultStylePath).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }
}
