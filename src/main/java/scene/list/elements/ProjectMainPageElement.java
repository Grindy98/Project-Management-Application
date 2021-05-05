package scene.list.elements;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import persistent.Project;
import scene.MainApp;
import scene.list.FXMLListElement;

public class ProjectMainPageElement extends FXMLListElement {

    @FXML
    private BorderPane deleteBorderPane;
    @FXML
    private Button deleteButton;
    @FXML
    private Button selectButton;

    @FXML
    private Label noParticipantsLabel;
    @FXML
    private Label nameLabel;

    public ProjectMainPageElement(Project project) {
        super("/lists/elements/project_main_page_elem.fxml");

        selectButton.setOnAction(e -> {
            System.out.println("Select button pressed -- popup window");
        });

        deleteButton.setOnAction(e -> {
            System.out.println("Delete button pressed");
            MainApp.getProjects().remove(project);
        });

        nameLabel.setText(project.getName());
        noParticipantsLabel.setText(String.valueOf(project.getMemberUsernameList().size()));

        // After having a way of getting the logged in user
        if(false){
            deleteBorderPane.setManaged(false);
            deleteBorderPane.setVisible(false);
        }
    }

    public interface DeleterDelegate{
        void deleter(ProjectMainPageElement elem, Project p);
    }
}
