module com.example.cooperativa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens com.example.cooperativa to javafx.fxml;
    exports com.example.cooperativa;
}