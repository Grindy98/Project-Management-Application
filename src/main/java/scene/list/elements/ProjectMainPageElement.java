package scene.list.elements;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    public ProjectMainPageElement(/*Project project*/) {
        super("/lists/elements/project_main_page_elem.fxml");

        selectButton.setOnAction(e -> {
            System.out.println("Select button pressed");
        });

        deleteButton.setOnAction(e -> {
            System.out.println("Delete button pressed");
        });

        nameLabel.setText("test");
        noParticipantsLabel.setText(String.valueOf(7));

        // After having a way of getting the logged in user
        if(false){
            deleteBorderPane.setManaged(false);
            deleteBorderPane.setVisible(false);
        }
    }
}
