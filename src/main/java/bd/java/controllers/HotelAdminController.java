package bd.java.controllers;

import bd.java.classes.Ciudad;
import bd.java.classes.Domicilio;
import bd.java.classes.Hotel;
import bd.java.sql.CiudadCRUD;
import bd.java.sql.DomicilioCRUD;
import bd.java.sql.HotelCRUD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HotelAdminController
{
    @FXML
    private VBox ventana;
    @FXML
    private TextField txtBusqueda, txtNombre, txtTelefono;
    @FXML
    private TextField txtCalle, txtNumero, txtColonia, txtCodigoPostal;
    @FXML
    private Button btnEditarInfo, btnGuardarInfo, btnEliminar;
    @FXML
    private ComboBox<String> cmbCiudad, cmbCriterio;

    @FXML
    private void initialize()
    {
        initComponents();
    }

    @FXML
    private void buscarClick()
    {
        try
        {
            HotelCRUD crud = new HotelCRUD();
            Hotel hotel = new Hotel();

            switch (cmbCriterio.getValue())
            {
                case "ID" -> hotel = crud.select("WHERE id_hotel = " + txtBusqueda.getText()).get(0);
                case "Tel"-> hotel = crud.select("WHERE telefono = '" + txtBusqueda.getText() + "'").get(0);
            }

            txtNombre.setText(hotel.getNombre());
            txtTelefono.setText(hotel.getTelefono());

            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + hotel.getIdf_domicilio()).get(0);

            txtCalle.setText(domicilio.getCalle());
            txtNumero.setText(String.valueOf(domicilio.getNumero()));
            txtColonia.setText(domicilio.getColonia());
            txtCodigoPostal.setText(domicilio.getCodigo_postal());
            cmbCiudad.getSelectionModel().select(domicilio.getIdf_Ciudad() - 1);

            btnEliminar.setDisable(false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Class: HotelAdminController");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void anadirHotelClick()
    {
        // Obtenemos el domicilio
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(txtCalle.getText());
        domicilio.setNumero(Integer.parseInt(txtNumero.getText()));
        domicilio.setColonia(txtColonia.getText());
        domicilio.setCodigo_postal(txtCodigoPostal.getText());
        domicilio.setIdf_Ciudad(cmbCiudad.getSelectionModel().getSelectedIndex() + 1);

        // Insertamos el domicilio
        DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
        int idf_domicilio = domicilioCRUD.insert(domicilio);

        // Obtenemos el hotel
        Hotel hotel = new Hotel();
        hotel.setNombre(txtNombre.getText());
        hotel.setTelefono(txtTelefono.getText());
        hotel.setIdf_domicilio(idf_domicilio);

        // Insertamos el hotel
        HotelCRUD crud = new HotelCRUD();
        if (crud.insert(hotel) != 0)
            LoginController.mostrarAlerta(Alert.AlertType.INFORMATION, "Hotel añadido", "El hotel se ha añadido correctamente", "");

        limpiarCampos();
    }

    @FXML
    private void editarInfoClick()
    {
        txtNombre.setEditable(true);
        txtTelefono.setEditable(true);
        txtCalle.setEditable(true);
        txtNumero.setEditable(true);
        txtColonia.setEditable(true);
        txtCodigoPostal.setEditable(true);
        cmbCiudad.setDisable(false);

        btnEditarInfo.setDisable(true);
        btnGuardarInfo.setDisable(false);
    }

    @FXML
    private void eliminarClick()
    {
        // Creamos un alert de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar el hotel?");
        alert.setContentText("Esta acción no se puede deshacer.");

        // Mostramos el alert y esperamos a que el usuario seleccione una opción
        alert.showAndWait();

        // Si el usuario selecciona OK, eliminamos el hotel
        if (alert.getResult().getText().equals("Aceptar"))
        {
            // Obtenemos el hotel
            HotelCRUD crud = new HotelCRUD();
            Hotel hotel = crud.select("WHERE telefono = '" + txtTelefono.getText() + "'").get(0);

            // Eliminamos el hotel
            crud.delete(hotel.getId_hotel());

            // Obtenemos el domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + hotel.getIdf_domicilio()).get(0);

            // Eliminamos el domicilio
            domicilioCRUD.delete(domicilio.getId_Domicilio());

            btnEliminar.setDisable(true);

            limpiarCampos();
        }
    }

    @FXML
    private void guardarInfoClick()
    {
        // Obtenemos el hotel
        HotelCRUD crud = new HotelCRUD();
        Hotel hotel = crud.select("WHERE telefono = '" + txtTelefono.getText() + "'").get(0);

        // Obtenemos el domicilio
        DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
        Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + hotel.getIdf_domicilio()).get(0);

        // Actualizamos el domicilio
        domicilio.setCalle(txtCalle.getText());
        domicilio.setNumero(Integer.parseInt(txtNumero.getText()));
        domicilio.setColonia(txtColonia.getText());
        domicilio.setCodigo_postal(txtCodigoPostal.getText());
        domicilio.setIdf_Ciudad(cmbCiudad.getSelectionModel().getSelectedIndex() + 1);
        domicilioCRUD.update(domicilio);

        // Actualizamos el hotel
        hotel.setNombre(txtNombre.getText());
        hotel.setTelefono(txtTelefono.getText());
        hotel.setIdf_domicilio(domicilio.getId_Domicilio());
        crud.update(hotel);

        // Deshabilitamos los campos
        txtNombre.setEditable(false);
        txtTelefono.setEditable(false);
        txtCalle.setEditable(false);
        txtNumero.setEditable(false);
        txtColonia.setEditable(false);
        txtCodigoPostal.setEditable(false);
        cmbCiudad.setDisable(true);

        btnEditarInfo.setDisable(false);
        btnGuardarInfo.setDisable(true);
    }

    @FXML
    private void regresarClick()
    {
        LoginController.abrirVentana("administrador", "Herramientas de Administrador");
        cerrarVentana();
    }

    // Métodos
    private void cerrarVentana()
    {
        Stage stage = (Stage) ventana.getScene().getWindow();
        stage.setOnCloseRequest(event -> {});
        stage.close();
    }

    private void limpiarCampos()
    {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtColonia.setText("");
        txtCodigoPostal.setText("");
        cmbCiudad.getSelectionModel().clearSelection();
    }

    private void initComponents()
    {
        // Iniciar combobox ciudades
        CiudadCRUD crud = new CiudadCRUD();
        List<Ciudad> ciudades = crud.select("");

        for (Ciudad ciudad : ciudades)
            cmbCiudad.getItems().addAll(ciudad.getNombre());

        // Iniciar combobox
        cmbCriterio.getItems().addAll("ID", "Tel");
        cmbCriterio.getSelectionModel().selectFirst();
    }
}
