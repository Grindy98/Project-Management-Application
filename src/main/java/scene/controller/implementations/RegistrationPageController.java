package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import scene.controller.SceneController;

public class RegistrationPageController extends SceneController {

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private Button backButton;
    @FXML
    private Button registerButton;

    public RegistrationPageController(){
        super("/pages/registration_page.fxml", 700, 700);

        backButton.setOnAction(e -> {
            backPressed();
        });
    }

    private void backPressed() {
        scene.getWindow().hide();
        scene = new StartPageController().getScene();
        Stage startStage = new Stage();
        startStage.setScene(scene);
        startStage.show();
        System.out.println("back pressed");
    }
}
