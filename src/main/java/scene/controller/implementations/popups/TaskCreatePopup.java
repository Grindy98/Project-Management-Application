package scene.controller.implementations.popups;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import persistent.Task;
import persistent.exception.TaskValidationFailedException;
import persistent.user.User;
import scene.MainApp;
import scene.controller.SceneController;
import scene.controller.implementations.ProjectPageController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskCreatePopup extends SceneController{

    private Stage popup;

    @FXML
    private DatePicker ddlDatePicker;
    @FXML
    private TextArea descTextArea;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button finishButton;
    @FXML
    private Button cancelButton;

    public TaskCreatePopup(){
        super("/pages/popups/task_create_popup.fxml", 600, 400);

        popup = MainApp.createPopup();
        popup.setTitle("Manage Team");
        popup.setResizable(false);

        popup.setScene(getScene());
        popup.show();

        cancelButton.setOnAction(e -> {
            cancelButtonPressed();
        });

        finishButton.setOnAction(e ->{
            finishButtonPressed();
        });

        //initialize ChoiceBox with usernames
        comboBox.setPromptText("Select user...");
        ObservableMap<String, User> userMap = User.getUsers();

        // Add only team members that are in the project
        ProjectPageController.getCurrentProject().getMemberUsernameList().forEach(u -> {
            comboBox.getItems().add(u);
        });
        comboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText(comboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
    }

    private void cancelButtonPressed(){
        popup.close();
    }

    private void finishButtonPressed(){
        if(addTask()){
            popup.close();
        }
    }

    private void validate(TaskValidationFailedException e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input alert");
        alert.setHeaderText("Invalid input!");

        StringBuilder sb = new StringBuilder();
        e.getErrors().forEach(type -> sb.append(type.getError()).append('\n'));

        alert.setContentText(sb.toString());

        alert.showAndWait();

        //Clear fields
        ddlDatePicker.getEditor().clear();
        descTextArea.setText("");
        comboBox.getSelectionModel().clearSelection();
    }

    private boolean addTask(){
        Task.SimpleDate date = null;
        LocalDate toValidate = ddlDatePicker.getValue();
        if(toValidate != null){
            date = new Task.SimpleDate(ddlDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        try {
            Task newTask = new Task(comboBox.getValue(), date, descTextArea.getText());
            ProjectPageController.getCurrentProject().getTasks().add(newTask);
            return true;
        } catch (TaskValidationFailedException e) {
            validate(e);
        }
        return false;
    }

}
