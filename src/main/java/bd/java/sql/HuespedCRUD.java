package bd.java.sql;

import bd.java.classes.Huesped;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HuespedCRUD extends ConnectionSQL
{
    public int insert(Huesped huesped)
    {
        int id = 0;

        try
        {
            String query = "INSERT INTO huesped (nombre, apellido, telefono, correo, idf_domicilio) " +
                    "VALUES (?, ?, ?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setString(3, huesped.getTelefono());
            statement.setString(4, huesped.getCorreo());
            statement.setInt(5, huesped.getIdf_domicilio());

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

    public List<Huesped> select(String condicion)
    {
        List<Huesped> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM huesped " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idHuesped = result.getInt("id_huesped");
                String nombre = result.getString("nombre");
                String apellido = result.getString("apellido");
                String telefono = result.getString("telefono");
                String correo = result.getString("correo");
                int idDomicilio = result.getInt("idf_domicilio");

                Huesped huesped = new Huesped();
                huesped.setId_huesped(idHuesped);
                huesped.setNombre(nombre);
                huesped.setApellido(apellido);
                huesped.setTelefono(telefono);
                huesped.setCorreo(correo);
                huesped.setIdf_domicilio(idDomicilio);

                resultados.add(huesped);
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

    public boolean update(Huesped huesped)
    {
        try
        {
            String query = "UPDATE huesped " +
                    "SET nombre = ?, apellido = ?, telefono = ?, correo = ?, idf_domicilio = ? " +
                    "WHERE id_huesped = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setString(3, huesped.getTelefono());
            statement.setString(4, huesped.getCorreo());
            statement.setInt(5, huesped.getIdf_domicilio());
            statement.setInt(6, huesped.getId_huesped()); // Usamos el ID para identificar el registro a actualizar

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
            String query = "DELETE FROM huesped WHERE id_huesped = ?";

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
