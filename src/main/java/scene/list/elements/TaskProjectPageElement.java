package scene.list.elements;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import persistent.Task;
import persistent.user.TeamMember;
import scene.MainApp;
import scene.SceneType;
import scene.controller.implementations.ProjectPageController;
import scene.controller.implementations.popups.ReviewPopup;
import scene.list.FXMLListElement;

public class TaskProjectPageElement extends FXMLListElement {

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Button deleteTaskButton;
    @FXML
    private Button seeReviewButton;
    @FXML
    private Button addReviewButton;
    @FXML
    private CheckBox completedCheckBox;
    @FXML
    private VBox projManagerVBox;
    @FXML
    private VBox teamMemberVBox;

    public TaskProjectPageElement(Task task){
        super("/lists/elements/task_project_page_elem.fxml");
        descriptionLabel.setText(task.getDescription());
        descriptionLabel.setWrapText(true);
        deadlineLabel.setText(task.getDeadline().toString());

        if(task.getIsCompleted()){
            completedCheckBox.setSelected(true);
            completedCheckBox.setDisable(true);
        }else{
            addReviewButton.setDisable(true);
        }

        if(task.getReview() == null){
            seeReviewButton.setDisable(true);
        }else{
            addReviewButton.setDisable(true);
        }

        completedCheckBox.setOnAction(e -> {
            completedCheckBoxChecked(task);
        });

        if(MainApp.getLoggedIn() instanceof TeamMember){
            projManagerVBox.setVisible(false);
        }
        else{
            teamMemberVBox.setVisible(false);
            completedCheckBox.setDisable(true);
        }
        deleteTaskButton.setOnAction(e -> {
            ProjectPageController.getCurrentProject().getTasks().remove(task);
        });

        addReviewButton.setOnAction(e -> {
            new ReviewPopup(task);
        });

        seeReviewButton.setOnAction(e -> {
            new ReviewPopup(task);
        });

    }

    private void completedCheckBoxChecked(Task task){
        completedCheckBox.setDisable(true);
        task.setIsCompleted(true);
    }
}
