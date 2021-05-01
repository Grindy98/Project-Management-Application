package scene.controller.implementations.popups;

import javafx.stage.Stage;
import scene.MainApp;
import scene.controller.FXMLController;
import scene.controller.SceneController;

public class ProjectCreatePopup extends SceneController {

    Stage popup;

    public ProjectCreatePopup() {
        super("/pages/popups/project_create_popups.fxml", 400, 400);

        popup = MainApp.createPopup();
        popup.setTitle("Create new project");
        popup.setScene(getScene());
        popup.show();
    }
}
