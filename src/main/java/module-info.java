module mathapp {
    requires  java.desktop;
    requires javafx.controls;   // for Buttons, Sliders, etc.
    requires javafx.graphics;   // for 3D graphics
    requires javafx.fxml;       // only if you use FXML

    // Allow JavaFX to access your classes via reflection
    opens mathapp to javafx.graphics, javafx.fxml;

    // Optional: export package if other modules need it
    exports mathapp;
}