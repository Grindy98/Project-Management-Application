package scene.controller.implementations;

import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import persistent.Project;
import persistent.Task;
import persistent.user.TeamMember;
import scene.MainApp;
import scene.SceneType;
import scene.controller.SceneController;
import scene.controller.implementations.popups.ManageTeamPopup;
import scene.controller.implementations.popups.TaskCreatePopup;
import scene.list.FXMLList;
import scene.list.elements.ProjectMainPageElement;
import scene.list.elements.TaskProjectPageElement;
import scene.list.utils.ListBind;

import java.util.function.Predicate;

public class ProjectPageController extends SceneController {

    private static Project currentProject = null;

    @FXML
    private Button backButton;
    @FXML
    private Button manageTeamButton;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private Label projectNameLabel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button createTaskButton;
    @FXML
    private VBox listVBox;


    FXMLList<TaskProjectPageElement> list;

    public ProjectPageController(){
        super("/pages/project_page.fxml", 600, 400);
        currentProject = null;

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        manageTeamButton.setOnAction(e -> manageTeamButtonPressed());
        backButton.setOnAction(e -> backButtonPressed());
        createTaskButton.setOnAction(e -> createTaskButtonPressed());

    }

    public void fillList(){
        if(MainApp.getLoggedIn() instanceof TeamMember){
            manageTeamButton.setVisible(false);
            deleteProjectButton.setVisible(false);
            createTaskButton.setVisible(false);
        }

        // Filter tasks based on who is logged in
        Predicate<Task> pred;
        String loggedInUsername = MainApp.getLoggedIn().getUsername();
        if(MainApp.getLoggedIn() instanceof TeamMember){
            // Only see assigned tasks
            pred = p -> (p.getAssigneeUsername().equals(loggedInUsername) && p.getProjectName().equals(projectNameLabel.getText()));
        }else{
            // See project tasks
            pred = p -> p.getProjectName().equals(projectNameLabel.getText());
        }
        // Create intermediary filtered list
        FilteredList<Task> taskFiltered = new FilteredList<>(Task.getTasks(), pred);
        // Doesn't work without this
        Task.getTasks().addListener((ListChangeListener<Task>) change ->{
            taskFiltered.equals("");
        });
        list = new FXMLList<>(listVBox);

        ListBind.listBind(list, taskFiltered, TaskProjectPageElement::new);
    }

    private void backButtonPressed(){
        MainApp.changeToScene(SceneType.MAIN_PAGE);
    }

    private void createTaskButtonPressed(){
      TaskCreatePopup popup = new TaskCreatePopup();
      popup.setProjectName(projectNameLabel.getText());
    }

    private void manageTeamButtonPressed(){
        new ManageTeamPopup();
    }

    public void setProject(Project project){
        currentProject = project;
        projectNameLabel.setText(project.getName());
        descriptionArea.setText(project.getDescription());

    }

    public static Project getCurrentProject(){
        if (currentProject == null){
            throw new NullPointerException("Current project not set");
        }
        return currentProject;
    }

}
