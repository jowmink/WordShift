module ca.ucalgary.wordshift {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    opens ca.ucalgary.wordshift to javafx.fxml;
    exports ca.ucalgary.wordshift;
}