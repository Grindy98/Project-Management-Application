module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    opens scene.controller.implementations to javafx.fxml;
    opens scene.controller.implementations.popups to javafx.fxml;
    opens scene.list.elements to javafx.fxml;

    exports scene;
    exports scene.controller;
    exports scene.list;
    exports scene.controller.implementations;
    exports scene.controller.implementations.popups;
    exports scene.list.elements;
}