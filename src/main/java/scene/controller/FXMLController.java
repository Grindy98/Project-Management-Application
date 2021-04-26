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
    private Parent root;

    protected FXMLController(String FXMLResourceString) {
        root = null;
        loader = new FXMLLoader(this.getClass().getResource(FXMLResourceString));
        loader.setController(this);
    }

    protected final Parent getRoot(){
        if(root == null){
            return resetRoot();
        }
        return root;
    }

    protected final Parent resetRoot(){
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return root;
    }
}
