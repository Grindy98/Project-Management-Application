package scene.list.utils;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface MapBind<T, V> {
    T changeToTarget(V sourceValue);

    static <T, K, V> void mapBind(List<T> target,
                                  ObservableMap<K, V> source,
                                  MapBind<T, V> binder) {
        target.clear();
        target.addAll(source.values().stream()
                .map(binder::changeToTarget)
                .collect(Collectors.toList()));
        MapChangeListener<K, V> listener = change -> {
            if(change.wasRemoved()){
                target.remove(binder.changeToTarget(change.getValueRemoved()));
            }
            if(change.wasAdded()){
                target.add(binder.changeToTarget(change.getValueAdded()));
            }
        };
        source.addListener(listener);
    }

    static <T, K, V> FilteredList<T> mapBindToFiltered(
            ObservableMap<K, V> source,
            MapBind<T, V> binder,
            Predicate<T> pred) {
        ObservableList<T> targetObs = FXCollections.observableArrayList();
        MapBind.mapBind(targetObs, source, binder);
        return new FilteredList<T>(targetObs, pred);
    }
}
