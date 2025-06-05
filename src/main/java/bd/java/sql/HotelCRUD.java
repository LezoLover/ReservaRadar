package bd.java.sql;

import bd.java.classes.Hotel;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelCRUD extends ConnectionSQL
{
    public int insert(Hotel hotel)
    {
        int id = 0;

        try
        {
            String query = "INSERT INTO hotel (nombre, telefono, idf_domicilio) " +
                    "VALUES (?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, hotel.getNombre());
            statement.setString(2, hotel.getTelefono());
            statement.setInt(3, hotel.getIdf_domicilio());

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

    public List<Hotel> select(String condicion)
    {
        List<Hotel> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM hotel " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idHotel = result.getInt("id_hotel");
                String nombre = result.getString("nombre");
                String telefono = result.getString("telefono");
                int idDomicilio = result.getInt("idf_domicilio");

                Hotel hotel = new Hotel();
                hotel.setId_hotel(idHotel);
                hotel.setNombre(nombre);
                hotel.setTelefono(telefono);
                hotel.setIdf_domicilio(idDomicilio);

                resultados.add(hotel);
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

    public boolean update(Hotel hotel)
    {
        try
        {
            String query = "UPDATE hotel " +
                    "SET nombre = ?, telefono = ?, idf_domicilio = ? " +
                    "WHERE id_hotel = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, hotel.getNombre());
            statement.setString(2, hotel.getTelefono());
            statement.setInt(3, hotel.getIdf_domicilio());
            statement.setInt(4, hotel.getId_hotel()); // Usamos el ID para identificar el registro a actualizar

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
            String query = "DELETE FROM hotel WHERE id_hotel = ?";

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
    public List<Hotel> selectDistinct(String condicion)
    {
        List<Hotel> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT DISTINCT hotel.* FROM hotel " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idHotel = result.getInt("id_hotel");
                String nombre = result.getString("nombre");
                String telefono = result.getString("telefono");
                int idDomicilio = result.getInt("idf_domicilio");

                Hotel hotel = new Hotel();
                hotel.setId_hotel(idHotel);
                hotel.setNombre(nombre);
                hotel.setTelefono(telefono);
                hotel.setIdf_domicilio(idDomicilio);

                resultados.add(hotel);
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
