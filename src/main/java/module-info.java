module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens scene.controller.implementations to javafx.fxml;
    exports scene;
    exports scene.controller;
    exports scene.controller.implementations;
}