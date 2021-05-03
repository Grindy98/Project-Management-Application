module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens scene.controller.implementations to javafx.fxml;
    opens persistent to com.fasterxml.jackson.databind;
    opens user to com.fasterxml.jackson.databind;

    exports scene;
    exports scene.controller;
    exports scene.controller.implementations;
}