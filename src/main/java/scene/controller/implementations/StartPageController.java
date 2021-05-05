package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistent.user.User;
import scene.MainApp;
import scene.SceneType;
import scene.controller.SceneController;

import javafx.scene.control.Button;

public class StartPageController extends SceneController {

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorMessageLabel;

    public StartPageController() {
        super("/pages/start_page.fxml", 600, 400);

        loginButton.setOnAction(e -> {
            onLogin();
        });
        registerButton.setOnAction(e -> {
            MainApp.changeToScene(SceneType.REGISTER);
        });
        errorMessageLabel.setText("Debug");
    }

    private void onLogin(){

        System.out.println("Login");
    }
}
