/**
 *
 */
module com.example.javaassign2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaassign2 to javafx.fxml;
    exports com.example.javaassign2;

}