package bd.java.sql;

import bd.java.classes.Domicilio;
import bd.java.classes.TipoHabitacion;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TipoHabitacionCRUD extends ConnectionSQL
{
    public int insert(TipoHabitacion tipoHabitacion)
    {
        int id = -1;

        try
        {
            String query = "INSERT INTO tipo_habitacion (nombre, capacidad, precio_base) " +
                    "VALUES (?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, tipoHabitacion.getNombre());
            statement.setInt(2, tipoHabitacion.getCapacidad());
            statement.setInt(3, tipoHabitacion.getPrecio_base());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0)
            {
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next())
                    id = generatedKeys.getInt(1);
            }

            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al insertar los datos", e.getMessage());
        }

        return id;
    }

    public List<TipoHabitacion> select(String condicion)
    {
        List<TipoHabitacion> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM tipo_habitacion " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                Integer id_tipo_habitacion = result.getInt("id_tipo_habitacion");
                String nombre = result.getString("nombre");
                Integer capacidad = result.getInt("capacidad");
                Integer precio_base = result.getInt("precio_base");

                TipoHabitacion tipoHabitacion = new TipoHabitacion();
                tipoHabitacion.setId_tipo_habitacion(id_tipo_habitacion);
                tipoHabitacion.setNombre(nombre);
                tipoHabitacion.setCapacidad(capacidad);
                tipoHabitacion.setPrecio_base(precio_base);

                resultados.add(tipoHabitacion);
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

    public boolean update(TipoHabitacion tipoHabitacion)
    {
        try
        {
            String query = "UPDATE tipo_habitacion " +
                    "SET nombre = ?, capacidad = ?, precio_base = ? " +
                    "WHERE id_tipo_habitacion = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, tipoHabitacion.getNombre());
            statement.setInt(2, tipoHabitacion.getCapacidad());
            statement.setInt(3, tipoHabitacion.getPrecio_base());
            statement.setInt(4, tipoHabitacion.getId_tipo_habitacion());

            int filasAfectadas = statement.executeUpdate();

            statement.close();
            connection.close();

            if (filasAfectadas > 0)
                return true;
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar", e.getMessage());
        }

        return false;
    }

    public boolean delete(int id)
    {
        try
        {
            String query = "DELETE FROM tipo_habitacion WHERE id_tipo_habitacion = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            int filasAfectadas = statement.executeUpdate();

            statement.close();
            connection.close();

            if (filasAfectadas > 0)
                return true;
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar", e.getMessage());
        }

        return false;
    }
}
