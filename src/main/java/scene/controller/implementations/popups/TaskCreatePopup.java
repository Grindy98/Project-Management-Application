package scene.controller.implementations.popups;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistent.user.TeamMember;
import persistent.user.User;
import scene.MainApp;
import scene.controller.SceneController;
import scene.list.utils.MapBind;

import java.util.Map;
import java.util.function.Predicate;

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

        //initialize ChoiceBox with usernames
        choiceBox.setValue("Select user here..");
        ObservableMap<String, User> userMap = User.getUsers();

        for(Map.Entry<String, User> entry : userMap.entrySet()){
            choiceBox.getItems().add(entry.getKey());
        }
    }

    private void cancelButtonPressed(){
        popup.close();
    }
}
