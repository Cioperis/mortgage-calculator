module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;

    exports com.application.GUI;
    opens com.application.GUI to javafx.fxml;

    exports com.application.Calculator;
    opens com.application.Calculator;
}