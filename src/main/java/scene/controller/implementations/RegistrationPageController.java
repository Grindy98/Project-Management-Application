package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scene.MainApp;
import scene.SceneType;
import scene.controller.SceneController;
import persistent.user.User;
import persistent.user.TeamMember;
import persistent.user.ProjectManager;
import persistent.user.utils.Encryptor;

import java.util.ArrayList;

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
    @FXML
    private ChoiceBox<String> roleSelector;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label passErrorLabel;
    @FXML
    private Label addressErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label selectorErrorLabel;

    public RegistrationPageController(){
        super("/pages/registration_page.fxml", 500, 500);

        backButton.setOnAction(e -> backPressed());
        registerButton.setOnAction(e -> registerPressed());

        //initialize the role selector options
        roleSelector.setValue("Select role...");
        roleSelector.getItems().add("Team member");
        roleSelector.getItems().add("Project manager");

        //hide the error messages
        userErrorLabel.setVisible(false);
        passErrorLabel.setVisible(false);
        addressErrorLabel.setVisible(false);
        phoneErrorLabel.setVisible(false);
        selectorErrorLabel.setVisible(false);
    }

    private void backPressed() {
        MainApp.changeToScene(SceneType.START, StartPageController::resetError);
    }

    private void registerPressed() {

        //role validation
        if(roleSelector.getValue().equals("Select role...")){
            selectorErrorLabel.setVisible(true);
            return;
        }else{
            selectorErrorLabel.setVisible(false);
        }

        boolean proceed = true;
        User user;

        if(roleSelector.getValue().equals("Team member"))
            user = new TeamMember(usernameTF.getText(), passwordTF.getText(), addressTF.getText(), phoneTF.getText());
        else
            user = new ProjectManager(usernameTF.getText(), passwordTF.getText(), addressTF.getText(), phoneTF.getText());

        //password validation
        if(!User.validatePassword(passwordTF.getText())) {
            passErrorLabel.setVisible(true);
            proceed = false;
        } else{
            passErrorLabel.setVisible(false);
        }
        //address validation
        if(!User.validateAddress(user.getAddress())) {
            addressErrorLabel.setVisible(true);
            proceed = false;
        } else{
            addressErrorLabel.setVisible(false);
        }
        //phone validation
        if(!User.validatePhone(user.getPhone())) {
            phoneErrorLabel.setVisible(true);
            proceed = false;
        } else{
            phoneErrorLabel.setVisible(false);
        }


        //return if flag is set to false
        if(!proceed)
            return;

        //username validation
        if(!User.validateUsername(user.getUsername())){
            userErrorLabel.setVisible(true);
        }else{
            User.getUsers().put(user.getUsername(), user);
            userErrorLabel.setVisible(false);
            System.out.println("Registration successfull");
        }
    }
}
