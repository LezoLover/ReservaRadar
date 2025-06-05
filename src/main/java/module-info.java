module bd.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens bd.java to javafx.fxml;
    exports bd.java;
    exports bd.java.controllers;
    opens bd.java.controllers to javafx.fxml;
}