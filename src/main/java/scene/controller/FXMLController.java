package scene.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Superclass that defines structure of anything that can be represented as root
 */
public abstract class FXMLController {
    private FXMLLoader loader;

    protected FXMLController(String FXMLResourceString) {
        loader = new FXMLLoader(this.getClass().getResource(FXMLResourceString));
        loader.setController(this);
    }

    protected final Parent getRoot(){
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
