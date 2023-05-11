module com.example.graafilinepokker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.graafilinepokker to javafx.fxml;
    exports com.example.graafilinepokker;
}