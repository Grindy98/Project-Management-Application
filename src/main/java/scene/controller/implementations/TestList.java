package scene.controller.implementations;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import scene.controller.SceneController;
import scene.list.FXMLList;
import scene.list.elements.TestListElement;

public class TestList extends SceneController {

    FXMLList<TestListElement> list;
    int id;

    @FXML
    VBox listVBox;

    public TestList() {
        super("/lists/test_list.fxml", 400, 400);
        list = new FXMLList<>(listVBox);
        id = 1;
        eventButton(new ActionEvent());
    }

    private void eventButton(ActionEvent event){
        list.add(new TestListElement(id, this::eventButton));
        System.out.println(id);
        id++;
    }
}
