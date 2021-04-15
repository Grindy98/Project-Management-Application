package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import scene.controller.SceneController;

public class TestPageController extends SceneController {

    @FXML
    Button primaryButton;

    @FXML
    Label label;
    int clicked;

    public TestPageController() {
        super("/test/primary.fxml", 400, 400);
        clicked = 0;
        System.out.println("DEBUG PRINT");
        primaryButton.setOnAction( e -> {
            clicked++;
            label.setText("Button clicked " + clicked + " times!");
        });
    }
}
