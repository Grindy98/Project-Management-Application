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

    public RegistrationPageController(){
        super("/pages/registration_page.fxml", 500, 500);

        backButton.setOnAction(e -> backPressed());

        //initialize the role selector options
        roleSelector.setValue("Select role...");
        roleSelector.getItems().add("Team member");
        roleSelector.getItems().add("Project manager");

        //hide the error messages
        userErrorLabel.setVisible(false);
        passErrorLabel.setVisible(false);
        addressErrorLabel.setVisible(false);
        phoneErrorLabel.setVisible(false);

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
