module PacMan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens sample.model to javafx.base, com.google.gson;
    opens sample.view to javafx.fxml;
    exports sample.view;
    exports sample;
    opens sample to javafx.fxml, com.google.gson,javafx.media;
    opens sample.model.button.and.image to com.google.gson, javafx.base;
    opens sample.model.map to com.google.gson, javafx.base;
    opens sample.model.ghost to com.google.gson, javafx.base;
}