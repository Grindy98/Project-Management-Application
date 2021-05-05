package scene;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public interface ListBind<T, S> {

    T changeToTarget(S source);

    default List<T> changeToTargetArray(List<S> source){
        List<T> target = new ArrayList<>(source.size());
        source.forEach(s -> {
            target.add(changeToTarget(s));
        });
        return target;
    }


    static <T, S> void listBind(List<T> target,
                                ObservableList<S> source,
                                ListBind<T, S> binder) {
        target.clear();
        target.addAll(binder.changeToTargetArray(source));
        ListChangeListener<S> listener = change -> {
            while(change.next()) {
                int from = change.getFrom();
                int to = change.getTo();
                if(change.wasPermutated()) {
                    target.subList(from, to).clear();
                    target.addAll(from, binder.changeToTargetArray(source.subList(from, to)));
                } else {
                    target.subList(from, from + change.getRemovedSize()).clear();
                    target.addAll(from, binder.changeToTargetArray(
                            source.subList(from, from + change.getAddedSize()) ));
                }
            }
        };
        source.addListener(listener);
    }
}
