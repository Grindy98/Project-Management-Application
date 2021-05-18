package scene.list.elements;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import persistent.Task;
import persistent.user.TeamMember;
import scene.MainApp;
import scene.SceneType;
import scene.controller.implementations.ProjectPageController;
import scene.controller.implementations.popups.ReviewPopup;
import scene.list.FXMLListElement;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

public class TaskProjectPageElement extends FXMLListElement {

    @FXML
    private VBox assigneeVBox;
    @FXML
    private Label assigneeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private CheckBox completedCheckBox;
    @FXML
    private FontIcon iconNotification;
    @FXML
    private VBox teamMemberVBox;
    @FXML
    private Button seeReviewButton;
    @FXML
    private VBox projManagerVBox;
    @FXML
    private Button addReviewButton;
    @FXML
    private Button deleteTaskButton;

    public TaskProjectPageElement(Task task){
        super("/lists/elements/task_project_page_elem.fxml");
        descriptionLabel.setText(task.getDescription());
        descriptionLabel.setWrapText(true);
        deadlineLabel.setText(task.getDeadline().toString());
        assigneeLabel.setText(task.getAssigneeUsername());

        if(task.getIsCompleted()){
            setChecked();
        }else{
            addReviewButton.setDisable(true);
            setNotifications(task.getDeadline().getRemainingDays());
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
            assigneeVBox.setVisible(false);
            assigneeVBox.setManaged(false);
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation alert");
        alert.setHeaderText("Confirm task finish!");
        alert.setContentText("Are you sure you want to proceed?\n" +
                "You cannot uncheck your task!");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            task.setIsCompleted(true);
            setChecked();
        }else{
            completedCheckBox.setSelected(false);
        }
    }

    private void setChecked(){
        completedCheckBox.setSelected(true);
        completedCheckBox.setDisable(true);
        // If completed make the notifier gray
        //iconNotification.setStyle("-fx-fill: #999999");
        iconNotification.setIconColor(Color.RED);
    }

    private void setNotifications(long remaining){
        if(remaining <= 3 ) {
            Tooltip warning = new Tooltip();
            if(remaining >= 1){
                // If three to one day(s) are left, change to yellow warning
                if(remaining >= 2){
                    warning.setText("There are " + remaining + " days left!");
                }else{
                    warning.setText("There is " + remaining + " day left!");
                }
                iconNotification.setIconColor(Color.valueOf("#ffe500"));
                //iconNotification.setStyle("-fx-fill: #ffe500");
            }else if(remaining == 0){
                // If today is the deadline, change to red warning
                warning.setText("Today is the last day!");
                iconNotification.setIconColor(Color.valueOf("#ff0000"));
                //iconNotification.setStyle("-fx-fill: #ff0000");
            }else{
                // Task is overdue, change to purple warning
                warning.setText("Task is overdue by " + -remaining + " day(s)!");
                //iconNotification.setStyle("-fx-fill: #7d40a7");
                //iconNotification.setIconColor(Color.valueOf("#7d40a7"));
                iconNotification.setIconColor(Color.web("#7d40a7"));
            }
            warning.setShowDelay(Duration.ZERO);
            warning.setFont(Font.font("Roboto"));
            Tooltip.install(iconNotification, warning);
        }else{
            // Default blue
            iconNotification.setIconColor(Color.valueOf("#85edff"));
            //iconNotification.setStyle("-fx-fill: #85edff");
        }
    }
}
