package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import persistent.Project;
import scene.list.utils.ListBind;
import scene.MainApp;
import scene.controller.SceneController;
import scene.controller.implementations.popups.ProjectCreatePopup;
import scene.list.FXMLList;
import scene.list.elements.ProjectMainPageElement;

public class MainPageController extends SceneController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox listVBox;

    @FXML
    private Button createProjectButton;

    FXMLList<ProjectMainPageElement> list;

    public MainPageController() {
        super("/pages/main_page.fxml", 600, 400);


        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // After having a way of getting the logged in user
        if(false){
            createProjectButton.setManaged(false);
            createProjectButton.setVisible(false);
        }

        createProjectButton.setOnAction(e -> {
            new ProjectCreatePopup();
        });

        list = new FXMLList<>(listVBox);

        ListBind.listBind(list, Project.getProjects(), ProjectMainPageElement::new);

    }
    
}
