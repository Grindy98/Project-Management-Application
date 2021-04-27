package scene.list;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import scene.controller.FXMLController;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is terribly inefficient for now, since update() is called multiple times depending on context
 * @param <T> FXML element that has a representation in FXML
 */
public class FXMLList<T extends FXMLListElement>
        extends AbstractList<T> {

    private ArrayList<T> list;

    private Pane listRoot;

    public FXMLList(Pane listRoot) {
        this.listRoot = listRoot;
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        T ret = list.set(index, element);
        update();
        return ret;
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
        update();
    }

    @Override
    public T remove(int index) {
        T ret = list.remove(index);
        update();
        return ret;
    }

    protected void update(){
        ArrayList<Parent> fxmlList = new ArrayList<>(list.size());
        for(T elem : list){
            fxmlList.add(elem.getRoot());
        }

        listRoot.getChildren().setAll(fxmlList);
    }
}
