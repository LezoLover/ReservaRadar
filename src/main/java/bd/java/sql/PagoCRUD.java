package bd.java.sql;

import bd.java.classes.Pago;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagoCRUD extends ConnectionSQL
{
    public int insert(Pago pago)
    {
        int id = 0;

        try
        {
            String query = "INSERT INTO pago (cantidad, metodo_pago, fecha, idf_reserva) " +
                    "VALUES (?, ?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setDouble(1, pago.getCantidad());
            statement.setString(2, pago.getMetodo_pago());
            statement.setDate(3, pago.getFecha());
            statement.setInt(4, pago.getIdf_reserva());

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

    public List<Pago> select(String condicion)
    {
        List<Pago> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM pago " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idPago = result.getInt("id_pago");
                double cantidad = result.getDouble("cantidad");
                String metodoPago = result.getString("metodo_pago");
                java.sql.Date fecha = result.getDate("fecha");
                int idfReserva = result.getInt("idf_reserva");

                Pago pago = new Pago();
                pago.setId_pago(idPago);
                pago.setCantidad(cantidad);
                pago.setMetodo_pago(metodoPago);
                pago.setFecha(fecha);
                pago.setIdf_reserva(idfReserva);

                resultados.add(pago);
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

    public boolean update(Pago pago)
    {
        try
        {
            String query = "UPDATE pago " +
                    "SET cantidad = ?, metodo_pago = ?, fecha = ?, idf_reserva = ? " +
                    "WHERE id_pago = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDouble(1, pago.getCantidad());
            statement.setString(2, pago.getMetodo_pago());
            statement.setDate(3, pago.getFecha());
            statement.setInt(4, pago.getIdf_reserva());
            statement.setInt(5, pago.getId_pago()); // Usamos el ID para identificar el registro a actualizar

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
            String query = "DELETE FROM pago WHERE id_pago = ?";

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
