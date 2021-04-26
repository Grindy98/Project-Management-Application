module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    opens scene.controller.implementations to javafx.fxml;
    exports scene;
    exports scene.controller;
    exports scene.controller.implementations;
}