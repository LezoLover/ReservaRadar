package bd.java.sql;

import bd.java.classes.Domicilio;
import bd.java.classes.HabRes;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabResCRUD extends ConnectionSQL
{
    public int insert(HabRes habRes)
    {
        int id = -1;

        try
        {
            String query = "INSERT INTO hab_res (idf_habitacion, idf_reserva) " +
                    "VALUES (?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, habRes.getIdf_habitacion());
            statement.setInt(2, habRes.getIdf_reserva());

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

    public List<HabRes> select(String condicion)
    {
        List<HabRes> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM hab_res " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                Integer id_hab_res = result.getInt("id_hab_res");
                Integer idf_habitacion = result.getInt("idf_habitacion");
                Integer idf_reserva = result.getInt("idf_reserva");

                HabRes habRes = new HabRes();
                habRes.setId_hab_res(id_hab_res);
                habRes.setIdf_habitacion(idf_habitacion);
                habRes.setIdf_reserva(idf_reserva);

                resultados.add(habRes);
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

    public boolean update(HabRes habRes)
    {
        try
        {
            String query = "UPDATE hab_res " +
                    "SET idf_habitacion = ?, idf_reserva = ? " +
                    "WHERE id_hab_res = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, habRes.getIdf_habitacion());
            statement.setInt(2, habRes.getIdf_reserva());
            statement.setInt(3, habRes.getId_hab_res());

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
            String query = "DELETE FROM hab_res WHERE id_hab_res = ?";

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
