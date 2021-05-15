package scene.controller.implementations.popups;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import persistent.Task;
import persistent.user.TeamMember;
import persistent.user.User;
import scene.MainApp;
import scene.controller.SceneController;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TaskCreatePopup extends SceneController{

    private Stage popup;

    @FXML
    private DatePicker ddlDatePicker;
    @FXML
    private TextArea descTextArea;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField textField;
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
        choiceBox.setValue("Select user here..");
        ObservableMap<String, User> userMap = User.getUsers();

        for(Map.Entry<String, User> entry : userMap.entrySet()){
            User user = entry.getValue();
            if(user instanceof TeamMember)
                choiceBox.getItems().add(entry.getKey());
        }
    }

    private void cancelButtonPressed(){
        popup.close();
    }

    private void finishButtonPressed(){
        validate();
    }

    private void validate()
    {
        boolean dateFlag = true, descriptionFlag = true, selectorFlag = true;

        //check date
        if(ddlDatePicker.getValue() == null)
            dateFlag = false;
        //check description
        if(descTextArea.getLength() == 0 || descTextArea.getLength() > 50)
            descriptionFlag = false;
        //check selector
        if(choiceBox.getValue().equals("Select user here.."))
            selectorFlag = false;

        if(!dateFlag || !descriptionFlag || !selectorFlag){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input alert");
            alert.setHeaderText("Invalid input!");

            String errors = "";

            if(!dateFlag)
                errors = errors + "Invalid date!\n";

            if(!descriptionFlag)
                errors = errors + "Description should be between 1 and 50 characters!\n";

            if(!selectorFlag)
                errors = errors + "No assignee choosen!\n";

            alert.setContentText(errors);

            alert.showAndWait();
        }

    }

    private void addTask(){
        ObservableList<Task> taskList = Task.getTasks();
        Task.SimpleDate date = new Task.SimpleDate(ddlDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        //Task newTask = new Task(choiceBox.getValue(), date, descTextArea.getText(), )
    }

}
