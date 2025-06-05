package bd.java.sql;

import bd.java.classes.Huesped;
import bd.java.classes.Reserva;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaCRUD extends ConnectionSQL
{
    public int insert(Reserva reserva)
    {
        int id = -1;

        try
        {
            String query = "INSERT INTO reserva (fecha_llegada, fecha_salida, num_huespedes, idf_hotel, idf_huesped) " +
                    "VALUES (?, ?, ?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setDate(1, reserva.getFecha_llegada());
            statement.setDate(2, reserva.getFecha_salida());
            statement.setInt(3, reserva.getNum_huespedes());
            statement.setInt(4, reserva.getIdf_hotel());
            statement.setInt(5, reserva.getIdf_huesped());

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

    public List<Reserva> select(String condicion)
    {
        List<Reserva> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM reserva " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idReserva = result.getInt("id_reserva");
                Date fechaLlegada = result.getDate("fecha_llegada");
                Date fechaSalida = result.getDate("fecha_salida");
                int numHuespedes = result.getInt("num_huespedes");
                int idfHotel = result.getInt("idf_hotel");
                int idfHuesped = result.getInt("idf_huesped");

                Reserva reserva = new Reserva();
                reserva.setId_reserva(idReserva);
                reserva.setFecha_llegada(fechaLlegada);
                reserva.setFecha_salida(fechaSalida);
                reserva.setNum_huespedes(numHuespedes);
                reserva.setIdf_hotel(idfHotel);
                reserva.setIdf_huesped(idfHuesped);

                resultados.add(reserva);
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

    public boolean update(Reserva reserva)
    {
        try
        {
            String query = "UPDATE reserva " +
                    "SET fecha_llegada = ?, fecha_salida = ?, num_huespedes = ?, idf_hotel = ?, idf_huesped = ? " +
                    "WHERE id_reserva = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDate(1, reserva.getFecha_llegada());
            statement.setDate(2, reserva.getFecha_salida());
            statement.setInt(3, reserva.getNum_huespedes());
            statement.setInt(4, reserva.getIdf_hotel());
            statement.setInt(5, reserva.getIdf_huesped());

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
            String query = "DELETE FROM reserva WHERE id_reserva = ?";

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
