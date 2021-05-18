package scene.controller.implementations;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import persistent.Project;
import persistent.exception.ProjectValidationFailedException;
import persistent.user.ProjectManager;
import persistent.user.TeamMember;
import persistent.user.User;
import persistent.user.utils.Encryptor;
import scene.SceneType;
import scene.list.utils.ListBind;
import scene.MainApp;
import scene.controller.SceneController;
import scene.controller.implementations.popups.ProjectCreatePopup;
import scene.list.FXMLList;
import scene.list.elements.ProjectMainPageElement;

import java.util.function.Predicate;

public class MainPageController extends SceneController {

    @FXML
    private Button logOutButton;

    @FXML
    private Label usernameLabel;
    @FXML
    private PasswordField oldPasswordPF;
    @FXML
    private PasswordField newPasswordPF;
    @FXML
    private Button passwordButton;
    @FXML
    private Button addressButton;
    @FXML
    private Button phoneButton;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField phoneTF;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox listVBox;
    @FXML
    private BorderPane deleteBorderPane;

    @FXML
    private Button createProjectButton;

    FXMLList<ProjectMainPageElement> list;

    public MainPageController() {
        super("/pages/main_page.fxml", 870, 400);


        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        if(MainApp.getLoggedIn() instanceof TeamMember){
            createProjectButton.setManaged(false);
            createProjectButton.setVisible(false);
            deleteBorderPane.setManaged(false);
            deleteBorderPane.setVisible(false);
        }

        createProjectButton.setOnAction(e -> {
            new ProjectCreatePopup();
        });

        // Filter projects based on who is logged in
        Predicate<Project> pred;
        String loggedInUsername = MainApp.getLoggedIn().getUsername();
        if(MainApp.getLoggedIn() instanceof TeamMember){
            // Only see projects you are part of
            pred = p -> p.getMemberUsernameList().contains(loggedInUsername);
        }else{
            // Only see projects you own
            pred = p -> p.getOwnerUsername().equals(loggedInUsername);
        }
        // Create intermediary filtered list
        FilteredList<Project> projFiltered = new FilteredList<>(Project.getProjects(), pred);
        // Doesn't work without this
        Project.getProjects().addListener((ListChangeListener<Project>) change ->{
            projFiltered.equals("");
        });
        list = new FXMLList<>(listVBox);

        ListBind.listBind(list, projFiltered, ProjectMainPageElement::new);

        usernameLabel.setText(MainApp.getLoggedIn().getUsername());

        passwordButton.setOnAction(e -> {
            updatePassword();
        });
        addressButton.setOnAction(e -> {
            updateAddress();
        });
        phoneButton.setOnAction(e -> {
            updatePhone();
        });

        addressTF.setText(MainApp.getLoggedIn().getAddress());
        phoneTF.setText(MainApp.getLoggedIn().getPhone());

        logOutButton.setOnAction(e -> {
            // Go back to login and remove logged in user
            MainApp.setLoggedIn(null);
            MainApp.changeToScene(SceneType.START);
        });

    }

    void updatePassword(){
        User currUser = MainApp.getLoggedIn();
        if(!currUser.checkPassword(oldPasswordPF.getText())){
            createAlert("Old password invalid!", Alert.AlertType.ERROR);
        }else if(!User.validatePassword(newPasswordPF.getText())){
            createAlert("New password must be at least 8 characters long!", Alert.AlertType.ERROR);
        }else{
            // Directly change the instance of user
            // Since user data besides the username (which is unchangeable) doesn't alter the app logic ->
            // We don't need to update anything else
            currUser.passwordSetter(newPasswordPF.getText());

            createAlert("Password changed successfully!", Alert.AlertType.INFORMATION);
        }
        oldPasswordPF.clear();
        newPasswordPF.clear();
    }

    void updateAddress(){
        User currUser = MainApp.getLoggedIn();
        if(!User.validateAddress(addressTF.getText())){
            createAlert("Invalid address!", Alert.AlertType.ERROR);
        }else if(currUser.getAddress().equals(addressTF.getText())){
            // Same address
            createAlert("Address is identical!", Alert.AlertType.ERROR);
        }else{
            currUser.addressSetter(addressTF.getText());
            createAlert("Address changed successfully!", Alert.AlertType.INFORMATION);
        }

        addressTF.setText(MainApp.getLoggedIn().getAddress());
    }

    void updatePhone(){
        User currUser = MainApp.getLoggedIn();
        if(!User.validatePhone(phoneTF.getText())){
            createAlert("Phone number must contain only digits!", Alert.AlertType.ERROR);
        }else if(currUser.getPhone().equals(phoneTF.getText())){
            // Same phone
            createAlert("Phone number is identical!", Alert.AlertType.ERROR);
        }else{
            currUser.phoneSetter(phoneTF.getText());
            createAlert("Phone number changed successfully!", Alert.AlertType.INFORMATION);
        }

        phoneTF.setText(MainApp.getLoggedIn().getPhone());
    }

    void createAlert(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("User update alert");
        if(type == Alert.AlertType.ERROR){
            alert.setHeaderText("Invalid input!");
        }
        if(type == Alert.AlertType.INFORMATION){
            alert.setHeaderText("Changes applied!");
        }
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
