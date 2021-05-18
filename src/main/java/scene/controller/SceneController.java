package scene.controller;

import javafx.css.Stylesheet;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import scene.MainApp;

public abstract class SceneController extends FXMLController {
    protected final Scene scene;

    private static final String defaultStylePath = "/css/default.css";

    protected SceneController(String FXMLResourceString, double sceneWidth, double sceneHeight) {
        super(FXMLResourceString);
        if(MainApp.isMaximized()){
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            scene = new Scene(getRoot(), bounds.getWidth(), bounds.getHeight());

        }else{
            scene = new Scene(getRoot(), sceneWidth, sceneHeight);
        }

        // Add default css
        scene.getStylesheets().add(getClass().getResource(defaultStylePath).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }
}
