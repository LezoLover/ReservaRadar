package bd.java.controllers;

import bd.java.classes.Ciudad;
import bd.java.classes.Domicilio;
import bd.java.classes.Huesped;
import bd.java.sql.CiudadCRUD;
import bd.java.sql.DomicilioCRUD;
import bd.java.sql.HuespedCRUD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HuespedController
{
    @FXML
    private VBox ventana;
    @FXML
    private TextField txtNombre, txtApellido, txtTelefono, txtCorreo;
    @FXML
    private TextField txtCalle, txtNumero, txtColonia, txtCodigoPostal;
    @FXML
    private ComboBox<String> cmbCiudad;

    @FXML
    private void initialize()
    {
        initComponents();
    }

    @FXML
    private void registrarseClick()
    {
        if (validarCampos() && validarCorreo())
        {
            // Obtenemos el ID de la ciudad para asignarlo al Domicilio
            Ciudad ciudad = new Ciudad();
            CiudadCRUD ciudadCRUD = new CiudadCRUD();

            List<Ciudad> ciudades = ciudadCRUD.select("WHERE nombre = '" + cmbCiudad.getValue() + "'");

            if (!ciudades.isEmpty())
                ciudad = ciudades.get(0);

            Integer idf_ciudad = ciudad.getId_ciudad();

            // Registramos el Domicilio
            Domicilio domicilio = new Domicilio();
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();

            domicilio.setCalle(txtCalle.getText());
            domicilio.setNumero(Integer.parseInt(txtNumero.getText()));
            domicilio.setColonia(txtColonia.getText());
            domicilio.setCodigo_postal(txtCodigoPostal.getText());
            domicilio.setIdf_Ciudad(idf_ciudad);

            Integer idf_domicilio = domicilioCRUD.insert(domicilio);

            // Registramos el Huesped
            Huesped huesped = new Huesped();
            HuespedCRUD huespedCRUD = new HuespedCRUD();

            huesped.setNombre(txtNombre.getText());
            huesped.setApellido(txtApellido.getText());
            huesped.setTelefono(txtTelefono.getText());
            huesped.setCorreo(txtCorreo.getText());
            huesped.setIdf_domicilio(idf_domicilio);

            int id_huesped = huespedCRUD.insert(huesped);

            if (id_huesped != 0)
                LoginController.mostrarAlerta(Alert.AlertType.INFORMATION, "Registrado éxitosamente", "Se realizó con éxito", "Bienvenid@ " + huesped.getNombre() + ", su id es: " + id_huesped);

            limpiarCampos();
        }
        else
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campos en blanco", "Error", "Rellene todos los campos");
        }
    }

    @FXML
    private void cancelarClick()
    {
        LoginController.abrirVentana("login", "Login");
        cerrarVentana();
    }

    // Métodos
    private void cerrarVentana()
    {
        Stage stage = (Stage) ventana.getScene().getWindow();
        stage.setOnCloseRequest(event ->
        {
        });
        stage.close();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty())
            return false;

        if (txtApellido.getText().trim().isEmpty())
            return false;

        if (txtTelefono.getText().trim().isEmpty())
            return false;

        if (txtCorreo.getText().trim().isEmpty())
            return false;

        if (txtCalle.getText().trim().isEmpty())
            return false;

        if (txtNumero.getText().trim().isEmpty())
            return false;

        if (txtColonia.getText().trim().isEmpty())
            return false;

        if (txtCodigoPostal.getText().trim().isEmpty())
            return false;

        return cmbCiudad.getValue() != null;
    }

    private boolean validarCorreo()
    {
        HuespedCRUD huespedCRUD = new HuespedCRUD();

        if (!huespedCRUD.select("WHERE correo = '" + txtCorreo.getText() + "'").isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El correo ya está registrado", "Por favor, ingresa otro");
            return false;
        }

        return true;
    }

    private void limpiarCampos()
    {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtCalle.clear();
        txtNumero.clear();
        txtColonia.clear();
        txtCodigoPostal.clear();
        cmbCiudad.setValue(null);
    }


    private void initComponents()
    {
        // Iniciar combobox destinos
        CiudadCRUD crud = new CiudadCRUD();

        List<Ciudad> ciudades = crud.select("");

        for (Ciudad ciudad : ciudades)
            cmbCiudad.getItems().addAll(ciudad.getNombre());
    }
}
