package bd.java.sql;

import bd.java.classes.Domicilio;
import bd.java.classes.Habitacion;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabitacionCRUD extends ConnectionSQL
{
    public int insert(Habitacion habitacion)
    {
        int id = -1;

        try
        {
            String query = "INSERT INTO habitacion (numero, idf_tipo_habitacion, idf_hotel) " +
                    "VALUES (?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, habitacion.getNumero());
            statement.setInt(2, habitacion.getIdf_tipo_habitacion());
            statement.setInt(3, habitacion.getIdf_hotel());

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

    public List<Habitacion> select(String condicion)
    {
        List<Habitacion> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM habitacion " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idHabitacion = result.getInt("id_habitacion");
                int numero = result.getInt("numero");
                int idfTipoHabitacion = result.getInt("idf_tipo_habitacion");
                int idfHotel = result.getInt("idf_hotel");

                Habitacion habitacion = new Habitacion();
                habitacion.setId_habitacion(idHabitacion);
                habitacion.setNumero(numero);
                habitacion.setIdf_tipo_habitacion(idfTipoHabitacion);
                habitacion.setIdf_hotel(idfHotel);

                resultados.add(habitacion);
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

    public boolean update(Habitacion habitacion)
    {
        try
        {
            String query = "UPDATE habitacion " +
                    "SET numero = ?, idf_tipo_hbaitacion = ?, idf_hotel = ? " +
                    "WHERE id_habitacion = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, habitacion.getNumero());
            statement.setInt(2, habitacion.getIdf_tipo_habitacion());
            statement.setInt(3, habitacion.getIdf_hotel());
            statement.setInt(4, habitacion.getId_habitacion()); // Usamos el ID para el WHERE

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
            String query = "DELETE FROM habitacion WHERE id_habitacion = ?";

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

    // Select Distinct
    public List<Habitacion> selectDisctint(String condicion)
    {
        List<Habitacion> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT DISTINCT habitacion.* AS tipo_habitacion FROM habitacion " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idHabitacion = result.getInt("id_habitacion");
                int numero = result.getInt("numero");
                int idfTipoHabitacion = result.getInt("idf_tipo_habitacion");
                int idfHotel = result.getInt("idf_hotel");

                Habitacion habitacion = new Habitacion();
                habitacion.setId_habitacion(idHabitacion);
                habitacion.setNumero(numero);
                habitacion.setIdf_tipo_habitacion(idfTipoHabitacion);
                habitacion.setIdf_hotel(idfHotel);

                resultados.add(habitacion);
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
