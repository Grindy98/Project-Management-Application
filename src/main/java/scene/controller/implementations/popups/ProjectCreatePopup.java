package scene.controller.implementations.popups;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistent.Project;
import persistent.exception.ProjectValidationFailedException;
import persistent.user.User;
import scene.MainApp;
import scene.controller.SceneController;
import scene.list.utils.MapBind;

import java.util.ArrayList;

public class ProjectCreatePopup extends SceneController {

    private Stage popup;

    private final ObservableList<ProjectCreatePopup.UserView> data;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descTextArea;

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
    private ComboBox<String> combo;
    @FXML
    private TextField textField;

    @FXML
    private Button finishButton;
    @FXML
    private Button cancelButton;

    public ProjectCreatePopup() {
        super("/pages/popups/project_create_popup.fxml", 500, 600);

        popup = MainApp.createPopup();
        popup.setTitle("Create new project");
        popup.setResizable(false);

        data = FXCollections.observableArrayList();
        MapBind.mapBind(data, User.getUsers(), UserView::new);

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

        FilteredList<UserView> flUser = new FilteredList<>(data, p -> true);//Pass the data to a filtered list
        table.setItems(flUser);//Set the table's items using the filtered list

        combo.getItems().addAll("None", "Username", "Phone Number", "Address");
        combo.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue.equals( "None")){
                textField.setDisable(true);
            }else{
                textField.setDisable(false);
            }
        });

        combo.setValue("None");

        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (combo.getValue())//Switch on choiceBox value
            {
                case "None":
                    flUser.setPredicate(p -> true);
                    break;
                case "Phone Number":
                    flUser.setPredicate(p -> p.getPhoneNumber().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Username":
                    flUser.setPredicate(p -> p.getUsername().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Address":
                    flUser.setPredicate(p -> p.getAddress().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
            }
        });

        combo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // reset table and textField when new choice is selected
            if (newVal != null) {
                textField.setText("");
            }
        });

        // Button init
        finishButton.setOnAction(e -> {
            addProject();
        });
        cancelButton.setOnAction(e -> {
            popup.close();
        });

        popup.setScene(getScene());
        popup.show();
    }

    private void addProject(){
        ArrayList<String> usernames = new ArrayList<>();
        data.forEach(userView -> {
            if(userView.getSelect().isSelected()){
                usernames.add(userView.getUsername());
            }
        });
        usernames.sort(String::compareTo);
        // To be implemented fully when user is implemented
        Project newProj = null;
        try {
            newProj = new Project(usernames, "this_username",
                    nameTextField.getText(), descTextArea.getText());
        } catch (ProjectValidationFailedException e) {
            // If invalid input present an alert box to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input alert");
            alert.setHeaderText("Invalid input!");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            // After alert was closed, close and reload popup
            reload();
        }
        if(newProj != null){
            // If the validation passed, add the project to the persistent global list
            Project.getProjects().add(newProj);
            popup.close();
        }

    }

    private void reload(){
        nameTextField.setText("");
        descTextArea.setText("");

        data.forEach(userView -> {
            userView.getSelect().setSelected(false);
        });

        combo.setValue("None");
    }

    public static class UserView{
        private String username;
        private String phoneNumber;
        private String address;
        private CheckBox select;

        public UserView(User user){
            this.username = user.getUsername();
            this.phoneNumber = user.getPhone();
            this.address = user.getAddress();

            this.select = new CheckBox();
        }

        public String getUsername() {
            return username;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public CheckBox getSelect() {
            return select;
        }
    }
}
