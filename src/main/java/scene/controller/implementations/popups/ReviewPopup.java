package scene.controller.implementations.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistent.Task;
import persistent.user.TeamMember;
import scene.MainApp;
import scene.SceneType;
import scene.controller.SceneController;
import scene.controller.implementations.ProjectPageController;

public class ReviewPopup extends SceneController {


    @FXML
    private Label taskDescription;
    @FXML
    private TextArea textArea;
    @FXML
    private Button reviewButton;
    @FXML
    private Button closeButton;

    private Stage popup;

    public ReviewPopup(Task task) {
        super("/pages/popups/review_popup.fxml", 400, 400);

        popup = MainApp.createPopup();
        popup.setTitle("Review Task");
        popup.setResizable(false);

        taskDescription.setText(task.getDescription());

        if(MainApp.getLoggedIn() instanceof TeamMember){
            textArea.setText(task.getReview());
            textArea.setEditable(false);
            reviewButton.setManaged(false);
            reviewButton.setVisible(false);
        }else{
            reviewButton.setOnAction(e ->{
                if(textArea.getText().isBlank()){
                    // Cannot leave an empty review
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid input alert");
                    alert.setHeaderText("Invalid input!");
                    alert.setContentText("The review cannot be empty!");
                    alert.showAndWait();
                    textArea.setText("");
                }else{
                    // Set review to task
                    task.setReview(textArea.getText());
                    ((ProjectPageController) SceneType.PROJECT_PAGE.getSceneController()).fillList();
                    popup.close();
                }
            });
        }

        closeButton.setOnAction(e -> {
            popup.close();
        });

        popup.setScene(getScene());
        popup.show();
    }
}
