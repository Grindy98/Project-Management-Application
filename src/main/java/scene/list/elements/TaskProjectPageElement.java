package scene.list.elements;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import persistent.Task;
import persistent.user.TeamMember;
import scene.MainApp;
import scene.list.FXMLListElement;

public class TaskProjectPageElement extends FXMLListElement {

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Button manageMembersButton;
    @FXML
    private Button seeReviewButton;
    @FXML
    private Button addReviewButton;

    public TaskProjectPageElement(Task task){
        super("/lists/elements/task_project_page_elem.fxml");

        if(MainApp.getLoggedIn() instanceof TeamMember){
            manageMembersButton.setVisible(false);
            addReviewButton.setVisible(false);
        }
        else{
            seeReviewButton.setVisible(false);
        }

    }
}
