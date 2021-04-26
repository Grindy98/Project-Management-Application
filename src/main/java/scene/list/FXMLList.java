package scene.list;

import javafx.scene.Parent;
import scene.controller.FXMLController;

import java.util.ArrayList;
import java.util.List;

public abstract class FXMLList<T extends FXMLListElement>
        extends FXMLController
        implements List<T> {

    protected ArrayList<T> list;

    /**
     * Each extended class must return a Parent object representing the place where the list nodes will
     * be created/destroyed
     * @return Parent object representing the root of the list
     */
    protected abstract Parent getListRoot();

    protected FXMLList(String FXMLResourceString) {
        super(FXMLResourceString);
    }

    public void addElement(T newElem){
        list.add(newElem);
    }

    public void removeElement(){

    }
}
