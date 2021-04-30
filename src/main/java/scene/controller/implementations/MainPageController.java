package scene.controller.implementations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import scene.controller.SceneController;
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

    public MainPageController(int testnum) {
        super("/pages/main_page.fxml", 600, 400);


        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // After having a way of getting the logged in user
        if(true){
            createProjectButton.setManaged(false);
            createProjectButton.setVisible(false);
        }

        list = new FXMLList<>(listVBox);

        for(int i = 0; i < testnum; i++){
            list.add(new ProjectMainPageElement());
        }
    }


}
