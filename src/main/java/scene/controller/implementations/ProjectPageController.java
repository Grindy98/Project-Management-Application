package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import persistent.Project;
import scene.MainApp;
import scene.SceneType;
import scene.controller.SceneController;
import scene.controller.implementations.popups.ManageTeamPopup;

public class ProjectPageController extends SceneController {

    @FXML
    private Button backButton;
    @FXML
    private Button manageTeamButton;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private Label projectNameLabel;
    @FXML
    private TextArea descriptionArea;

    public ProjectPageController(){
        super("/pages/project_page.fxml", 600, 600);

        manageTeamButton.setOnAction(e -> manageTeamButtonPressed());
        backButton.setOnAction(e -> backButtonPressed());
    }

    private void backButtonPressed(){
        MainApp.changeToScene(SceneType.MAIN_PAGE);
    }

    private void manageTeamButtonPressed(){
        new ManageTeamPopup();
    }

    public void setProject(Project project){
        projectNameLabel.setText(project.getName());
        descriptionArea.setText(project.getDescription());

    }

}
