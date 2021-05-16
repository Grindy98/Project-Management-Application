package scene.controller.implementations.popups;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistent.Project;
import persistent.user.TeamMember;
import persistent.user.User;
import scene.MainApp;
import scene.controller.SceneController;
import scene.controller.implementations.ProjectPageController;
import scene.controller.implementations.popups.ProjectCreatePopup.UserView;
import scene.list.utils.MapBind;

import java.util.List;
import java.util.function.Predicate;

import static scene.controller.implementations.popups.ProjectCreatePopup.getSelectedFromUserView;

public class ManageTeamPopup extends SceneController {

    private Stage popup;

    @FXML
    private TableView<UserView> table;
    @FXML
    private TableColumn<UserView, String> usernameCol;
    @FXML
    private TableColumn<UserView, String> phoneNumCol;
    @FXML
    private TableColumn<UserView, String> addressCol;
    @FXML
    private TableColumn<UserView, String> selectCol;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private TextField textField;

    private final ObservableList<UserView> data;

    public ManageTeamPopup(){
        super("/pages/popups/manage_team_popup.fxml", 610, 350);

        popup = MainApp.createPopup();
        popup.setTitle("Manage Team");
        popup.setResizable(false);

        data = FXCollections.observableArrayList();
        MapBind.mapBind(data, User.getUsers(), this::getUserWithRightSelect);

        // Table init
        table.setEditable(true);

        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        phoneNumCol.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        selectCol.setCellValueFactory(
                new PropertyValueFactory<>("select"));

        Predicate<UserView> basePred = p -> User.getUsers().get(p.getUsername()) instanceof TeamMember;

        FilteredList<UserView> flUserView = new FilteredList<>(data, basePred);//Pass the data to a filtered list
        table.setItems(flUserView);//Set the table's items using the filtered list

        table.getColumns().forEach(c ->{
            c.setReorderable(false);
        });

        combo.getItems().addAll("None", "Username", "Phone Number", "Address");
        combo.valueProperty().addListener((obs, oldValue, newValue) -> {
            textField.setDisable(newValue.equals("None"));
        });

        combo.setValue("None");

        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (combo.getValue())//Switch on choiceBox value
            {
                case "None":
                    flUserView.setPredicate(basePred);
                    break;
                case "Phone Number":
                    flUserView.setPredicate(basePred.and(
                            p -> p.getPhoneNumber().toLowerCase().contains(newValue.toLowerCase().trim())));
                    break;
                case "Username":
                    flUserView.setPredicate(basePred.and(
                            p -> p.getUsername().toLowerCase().contains(newValue.toLowerCase().trim())));
                    break;
                case "Address":
                    flUserView.setPredicate(basePred.and(
                            p -> p.getAddress().toLowerCase().contains(newValue.toLowerCase().trim())));
                    break;
            }
        });

        combo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // reset table and textField when new choice is selected
            if (newVal != null) {
                textField.setText("");
            }
        });

        saveButton.setOnAction(e -> {
            saveChanges();
        });

        popup.setScene(getScene());
        popup.show();
    }

    private UserView getUserWithRightSelect(User u){
        UserView ret = new UserView(u);
        boolean isUserInProj;
        Project curr = ProjectPageController.getCurrentProject();
        isUserInProj = curr.getMemberUsernameList().contains(u.getUsername());
        ret.getSelect().setSelected(isUserInProj);
        return ret;
    }

    private void saveChanges(){
        Project curr = ProjectPageController.getCurrentProject();
        List<String> usernames = getSelectedFromUserView(data);
        curr.getMemberUsernameList().clear();
        curr.getMemberUsernameList().addAll(usernames);
        popup.close();
    }


}
