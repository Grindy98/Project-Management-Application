package scene.list.elements;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private Button editTaskButton;
    @FXML
    private Button seeReviewButton;
    @FXML
    private Button addReviewButton;
    @FXML
    private CheckBox completedCheckBox;

    public TaskProjectPageElement(Task task){
        super("/lists/elements/task_project_page_elem.fxml");

        descriptionLabel.setText(task.getDescription());
        descriptionLabel.setWrapText(true);
        deadlineLabel.setText(task.getDeadline().toString());

        if(task.getIsCompleted()){
            completedCheckBox.setSelected(true);
            completedCheckBox.setDisable(true);
        }else{
            seeReviewButton.setVisible(false);
        }

        completedCheckBox.setOnAction(e -> {
            completedCheckBoxChecked();
        });

        if(MainApp.getLoggedIn() instanceof TeamMember){
            editTaskButton.setVisible(false);
            addReviewButton.setVisible(false);
        }
        else{
            seeReviewButton.setVisible(false);
            completedCheckBox.setDisable(true);
        }

    }

    private void completedCheckBoxChecked(){
        completedCheckBox.setDisable(true);

        for(var taskEntry:Task.getTasks()){
            if(taskEntry.getDescription().equals(descriptionLabel.getText())){
                taskEntry.setIsCompleted(completedCheckBox.isSelected());
            }
        }

        Task.save();
        seeReviewButton.setVisible(true);
    }
}
