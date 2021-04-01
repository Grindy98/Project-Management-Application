module com.project_management_app {
    requires javafx.controls;
    requires javafx.fxml;

    opens test to javafx.fxml;
    exports test;
}