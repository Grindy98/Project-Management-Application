package scene.list.utils;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;
import java.util.Map;
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
}
