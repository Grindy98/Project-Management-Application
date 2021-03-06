open module Project.Management.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    // Icons
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules
    requires org.kordamp.ikonli.fontawesome5;

    exports persistent;
    exports persistent.exception;
    exports scene;
    exports scene.controller;
    exports scene.list;
    exports scene.controller.implementations;
    exports persistent.user;
    exports scene.controller.implementations.popups;
    exports scene.list.elements;
}