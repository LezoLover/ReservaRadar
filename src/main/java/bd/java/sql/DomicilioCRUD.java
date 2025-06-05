package bd.java.sql;

import bd.java.classes.Domicilio;
import bd.java.controllers.LoginController;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DomicilioCRUD extends ConnectionSQL
{
    public int insert(Domicilio domicilio)
    {
        int id = -1;

        try
        {
            String query = "INSERT INTO domicilio (calle, numero, colonia, codigo_postal, idf_ciudad) " +
                    "VALUES (?, ?, ?, ?, ?)";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, domicilio.getCalle());
            statement.setInt(2, domicilio.getNumero());
            statement.setString(3, domicilio.getColonia());
            statement.setString(4, domicilio.getCodigo_postal());
            statement.setInt(5, domicilio.getIdf_Ciudad());

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

    public List<Domicilio> select(String condicion)
    {
        List<Domicilio> resultados = new ArrayList<>();

        try
        {
            Connection connection = getConnection();

            String query = "SELECT * FROM domicilio " + condicion;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                // Obtener datos del resultado
                int idDomicilio = result.getInt("id_domicilio");
                String calle = result.getString("calle");
                int numero = result.getInt("numero");
                String colonia = result.getString("colonia");
                String codigo_postal = result.getString("codigo_postal");
                int idfCiudad = result.getInt("idf_ciudad");

                Domicilio domicilio = new Domicilio();
                domicilio.setId_Domicilio(idDomicilio);
                domicilio.setCalle(calle);
                domicilio.setNumero(numero);
                domicilio.setColonia(colonia);
                domicilio.setCodigo_postal(codigo_postal);
                domicilio.setIdf_Ciudad(idfCiudad);

                resultados.add(domicilio);
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

    public boolean update(Domicilio domicilio)
    {
        try
        {
            String query = "UPDATE domicilio " +
                    "SET calle = ?, numero = ?, colonia = ?, codigo_postal = ?, idf_ciudad = ? " +
                    "WHERE id_domicilio = ?";

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, domicilio.getCalle());
            statement.setInt(2, domicilio.getNumero());
            statement.setString(3, domicilio.getColonia());
            statement.setString(4, domicilio.getCodigo_postal());
            statement.setInt(5, domicilio.getIdf_Ciudad());
            statement.setInt(6, domicilio.getId_Domicilio()); // Usamos el id para encontrar el registro

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
            String query = "DELETE FROM domicilio WHERE id_domicilio = ?";

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
