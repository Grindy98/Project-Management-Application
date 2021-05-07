package scene.controller.implementations;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import persistent.user.User;
import persistent.user.utils.Encryptor;
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
    private Text userErrorText;
    @FXML
    private Text passwordErrorText;

    public StartPageController() {
        super("/pages/start_page.fxml", 600, 400);

        loginButton.setOnAction(e -> {
            onLogin();
        });
        registerButton.setOnAction(e -> {
            MainApp.changeToScene(SceneType.REGISTER);
        });

        userErrorText.setVisible(false);
        passwordErrorText.setVisible(false);
    }

    private void onLogin(){

        ObservableMap<String, User> tempUsers = User.getUsers();

        //check username
        if(!tempUsers.containsKey(usernameTF.getText())){
            userErrorText.setVisible(true);
        } else{
            User user = tempUsers.get(usernameTF.getText());
            //check password
          if(user.checkPassword(passwordTF.getText())){
              MainApp.setLoggedIn(tempUsers.get(usernameTF.getText()));
              MainApp.changeToScene(SceneType.MAIN_PAGE);
          }else{
              passwordErrorText.setVisible(true);
              userErrorText.setVisible(false);
          }
        }

    }
}
