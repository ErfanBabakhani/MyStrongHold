module Tamrin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    exports controller;
    opens view to javafx.fxml;
}