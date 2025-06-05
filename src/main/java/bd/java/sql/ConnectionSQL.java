package bd.java.sql;

import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.*;

public class ConnectionSQL
{
    private Connection connection = null;

    public Connection getConnection()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            String URL = "jdbc:postgresql://localhost:5432/hoteles";
            String USUARIO = "postgres";
            String PASSWORD = "postgres";

            connection = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al conectar con la base de datos", e.getMessage());
        }

        return connection;
    }
}
