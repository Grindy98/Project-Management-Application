module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens scene.controller.implementations to javafx.fxml;
    opens scene.list.elements to javafx.fxml;

    opens persistent to com.fasterxml.jackson.databind;
    exports scene;
    exports scene.controller;
    exports scene.list;
    exports scene.controller.implementations;
    exports scene.list.elements;
}