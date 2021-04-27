package scene.list.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import scene.list.FXMLListElement;

public class TestListElement extends FXMLListElement {

    @FXML
    private Button button;

    public int ID;

    public TestListElement(int ID, EventHandler<ActionEvent> e) {
        super("/lists/elements/test_element.fxml");
        this.ID = ID;

        button.setOnAction(e);
    }
}
