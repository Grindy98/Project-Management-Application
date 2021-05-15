package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import persistent.Project;
import persistent.user.TeamMember;
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
    @FXML
    private Button createTaskButton;

    public ProjectPageController(){
        super("/pages/project_page.fxml", 600, 400);

        manageTeamButton.setOnAction(e -> manageTeamButtonPressed());
        backButton.setOnAction(e -> backButtonPressed());
        createTaskButton.setOnAction(e -> createTaskButtonPressed());

        if(MainApp.getLoggedIn() instanceof TeamMember){
            manageTeamButton.setVisible(false);
            deleteProjectButton.setVisible(false);
            createTaskButton.setVisible(false);
        }

    }

    private void backButtonPressed(){
        MainApp.changeToScene(SceneType.MAIN_PAGE);
    }

    private void createTaskButtonPressed(){
        if(MainApp.getLoggedIn() instanceof TeamMember)
            System.out.println("TeamMember pressed");
        else
            System.out.println("Manaager pressed");
    }

    private void manageTeamButtonPressed(){
        new ManageTeamPopup();
    }

    public void setProject(Project project){
        projectNameLabel.setText(project.getName());
        descriptionArea.setText(project.getDescription());

    }

}
