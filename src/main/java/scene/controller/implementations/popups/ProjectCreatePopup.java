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
import scene.MainApp;
import scene.controller.SceneController;

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
    private TableColumn<UserView, String> firstNameCol;
    @FXML
    private TableColumn<UserView, String> lastNameCol;
    @FXML
    private TableColumn<UserView, String> emailCol;
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

        //Get all users after implementing that
        ArrayList<UserView> tempArr = new ArrayList<>();
        // Foreach assign
        tempArr.add(new UserView("A", "A", "A", "A"));
        tempArr.add(new UserView("B", "A", "A", "A"));
        tempArr.add(new UserView("C", "A", "A", "A"));
        tempArr.add(new UserView("D", "A", "A", "A"));
        tempArr.add(new UserView("E", "A", "A", "A"));
        tempArr.add(new UserView("F", "A", "A", "A"));
        tempArr.add(new UserView("G", "A", "A", "A"));
        tempArr.add(new UserView("H", "A", "A", "A"));
        tempArr.add(new UserView("I", "A", "A", "A"));
        tempArr.add(new UserView("J", "A", "A", "A"));
        tempArr.add(new UserView("K", "A", "A", "A"));
        tempArr.add(new UserView("L", "A", "A", "A"));
        data = FXCollections.observableArrayList(tempArr);

        // Table init
        table.setEditable(true);

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        selectCol.setCellValueFactory(
                new PropertyValueFactory<>("select"));

        FilteredList<UserView> flUser = new FilteredList<>(data, p -> true);//Pass the data to a filtered list
        table.setItems(flUser);//Set the table's items using the filtered list

        combo.getItems().addAll("None", "Username", "First Name", "Last Name", "Email");
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
                case "First Name":
                    flUser.setPredicate(p -> p.getFirstName().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Username":
                    flUser.setPredicate(p -> p.getUsername().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Last Name":
                    flUser.setPredicate(p -> p.getLastName().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
                case "Email":
                    flUser.setPredicate(p -> p.getEmail().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by email
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
            MainApp.getProjects().add(newProj);
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
        private String firstName;
        private String lastName;
        private String email;
        private CheckBox select;

        public UserView(/* user */String u, String f, String l, String e){
            this.username = u;
            this.firstName = f;
            this.lastName = l;
            this.email = e;

            this.select = new CheckBox();
        }

        public String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public CheckBox getSelect() {
            return select;
        }
    }
}
