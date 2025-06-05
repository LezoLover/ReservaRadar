package bd.java.sql;

import bd.java.classes.Ciudad;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CiudadCRUD extends ConnectionSQL
{
    public List<Ciudad> select(String condicion)
    {
        List<Ciudad> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM ciudad " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idCiudad = result.getInt("id_ciudad");
                String nombre = result.getString("nombre");

                Ciudad ciudad = new Ciudad();
                ciudad.setId_ciudad(idCiudad);
                ciudad.setNombre(nombre);

                resultados.add(ciudad);
            }

            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo insertar el registro", e.getMessage());
        }

        return resultados;
    }
}
