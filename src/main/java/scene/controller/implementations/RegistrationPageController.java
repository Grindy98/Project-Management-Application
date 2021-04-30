package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        scene.getWindow().hide();
        scene = new StartPageController().getScene();
        Stage startStage = new Stage();
        startStage.setScene(scene);
        startStage.show();
        System.out.println("back pressed");
    }

    private void registerPressed() {
        //TODO: parse JSON to validate username

        boolean proceed = true;

        //password validation
        if(passwordTF.getText().length() <= 8) {
            passErrorLabel.setVisible(true);
            proceed = false;
        } else{
            passErrorLabel.setVisible(false);
        }
        //address validation
        boolean addrIsValid = addressTF.getText().matches("[0-9]+");
        if(addrIsValid || addressTF.getText().length() > 100) {
            addressErrorLabel.setVisible(true);
            proceed = false;
        } else{
            addressErrorLabel.setVisible(false);
        }
        //phone validation
        boolean phoneIsValid = phoneTF.getText().matches("[0-9]+");
        if(!phoneIsValid) {
            phoneErrorLabel.setVisible(true);
            proceed = false;
        } else{
            phoneErrorLabel.setVisible(false);
        }
        //role validation
        if(roleSelector.getValue().equals("Select role...")){
            selectorErrorLabel.setVisible(true);
            proceed = false;
        }else{
            selectorErrorLabel.setVisible(false);
        }

        if(!proceed)
            return;

        //TODO: write to JSON file if validation passed
        System.out.println("Registration successfull");
    }
}
