module mathapp {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires java.base;
    requires com.formdev.flatlaf;

    opens mathapp to javafx.graphics, javafx.fxml;
    exports mathapp;
    exports mathapp.gui;
    opens mathapp.gui to javafx.fxml, javafx.graphics;
    exports mathapp.objects.twoD;
    opens mathapp.objects.twoD to javafx.fxml, javafx.graphics;
}
