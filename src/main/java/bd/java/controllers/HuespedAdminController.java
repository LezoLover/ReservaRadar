package bd.java.controllers;

import bd.java.classes.Ciudad;
import bd.java.classes.Domicilio;
import bd.java.classes.Huesped;
import bd.java.sql.CiudadCRUD;
import bd.java.sql.DomicilioCRUD;
import bd.java.sql.HuespedCRUD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HuespedAdminController
{
    @FXML
    private VBox ventana;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TextField txtNombre, txtApellido, txtTelefono, txtCorreo;
    @FXML
    private TextField txtCalle, txtNumero, txtColonia, txtCodigoPostal;
    @FXML
    private Button btnEditarInfo, btnGuardarInfo, btnEliminar;
    @FXML
    private ComboBox<String> cmbCriterio, cmbCiudad;

    @FXML
    private void initialize()
    {
        initComponentes();
    }

    @FXML
    private void buscarClick()
    {
        try
        {
            HuespedCRUD huespedCRUD = new HuespedCRUD();
            Huesped huesped = new Huesped();

            switch (cmbCriterio.getValue())
            {
                case "ID" -> huesped = huespedCRUD.select("WHERE id_huesped = " + Integer.parseInt(txtBusqueda.getText())).get(0);
                case "Correo" -> huesped = huespedCRUD.select("WHERE correo = '" + txtBusqueda.getText() + "'").get(0);
                case "Teléfono" -> huesped = huespedCRUD.select("WHERE telefono = '" + txtBusqueda.getText() + "'").get(0);
            }

            Huesped.id_huesped_static = huesped.getId_huesped();

            // Llenar los campos
            txtNombre.setText(huesped.getNombre());
            txtApellido.setText(huesped.getApellido());
            txtTelefono.setText(huesped.getTelefono());
            txtCorreo.setText(huesped.getCorreo());

            // Obtenemos el domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + huesped.getIdf_domicilio()).get(0);

            // Llenamos los campos del domicilio
            txtCalle.setText(domicilio.getCalle());
            txtNumero.setText(domicilio.getNumero().toString());
            txtColonia.setText(domicilio.getColonia());
            txtCodigoPostal.setText(domicilio.getCodigo_postal());

            // Obtenemos la ciudad
            CiudadCRUD ciudadCRUD = new CiudadCRUD();
            Ciudad ciudad = ciudadCRUD.select("WHERE id_ciudad = " + domicilio.getIdf_Ciudad()).get(0);

            // Seleccionamos la ciudad en el combobox
            cmbCiudad.getSelectionModel().select(ciudad.getNombre());

            btnEliminar.setDisable(false);
        }
        catch (Exception e)
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al buscar el Huesped", e.toString());
        }
    }

    @FXML
    private void editarInfoClick()
    {
        // Habilitar editado de campos
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
        txtCorreo.setEditable(true);

        txtCalle.setEditable(true);
        txtNumero.setEditable(true);
        txtColonia.setEditable(true);
        txtCodigoPostal.setEditable(true);

        cmbCiudad.setDisable(false);

        // Habilitar botón de guardar
        btnGuardarInfo.setDisable(false);

        // Deshabilitar botón de editar
        btnEditarInfo.setDisable(true);
    }

    @FXML
    private void eliminarClick()
    {
        // Creamos una ventana de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar la cuenta?");
        alert.setContentText("Esta acción no se puede deshacer");

        // Mostramos la ventana y esperamos a que el usuario presione un botón
        alert.showAndWait();

        if (alert.getResult().getText().equals("Aceptar"))
        {
            // Obtenemos el Huesped
            HuespedCRUD huespedCRUD = new HuespedCRUD();
            Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

            // Obtenemos el Domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + huesped.getIdf_domicilio()).get(0);

            // Eliminamos el Huesped
            huespedCRUD.delete(huesped.getId_huesped());

            // Eliminamos el Domicilio
            domicilioCRUD.delete(domicilio.getId_Domicilio());

            limpiaCampos();

            btnEliminar.setDisable(true);
        }
    }

    @FXML
    private void guardarInfoClick()
    {
        if (validarCampos() && validarCorreo())
        {
            // Actualizamos los datos del Huesped
            HuespedCRUD huespedCRUD = new HuespedCRUD();
            Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

            huesped.setNombre(txtNombre.getText());
            huesped.setApellido(txtApellido.getText());
            huesped.setTelefono(txtTelefono.getText());
            huesped.setCorreo(txtCorreo.getText());

            huespedCRUD.update(huesped);

            // Obtenemos el id de la ciudad
            CiudadCRUD ciudadCRUD = new CiudadCRUD();
            Ciudad ciudad = ciudadCRUD.select("WHERE nombre = '" + cmbCiudad.getValue() + "'").get(0);

            // Actualizamos los datos del Domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + huesped.getIdf_domicilio()).get(0);

            domicilio.setCalle(txtCalle.getText());
            domicilio.setNumero(Integer.parseInt(txtNumero.getText()));
            domicilio.setColonia(txtColonia.getText());
            domicilio.setCodigo_postal(txtCodigoPostal.getText());
            domicilio.setIdf_Ciudad(ciudad.getId_ciudad());

            domicilioCRUD.update(domicilio);

            // Deshabilitar editado de campos
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);

            txtCalle.setEditable(false);
            txtNumero.setEditable(false);
            txtColonia.setEditable(false);
            txtCodigoPostal.setEditable(false);

            cmbCiudad.setDisable(true);

            // Deshabilitar botón de guardar
            btnGuardarInfo.setDisable(true);

            // Habilitar botón de editar
            btnEditarInfo.setDisable(false);
        }
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

    private boolean validarCampos()
    {
        if (txtNombre.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Nombre está vacío", "Por favor, ingresa un nombre");
            return false;
        }
        else if (txtApellido.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Apellido está vacío", "Por favor, ingresa un apellido");
            return false;
        }
        else if (txtTelefono.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Teléfono está vacío", "Por favor, ingresa un teléfono");
            return false;
        }
        else if (txtCorreo.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Correo está vacío", "Por favor, ingresa un correo");
            return false;
        }
        else if (txtCalle.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Calle está vacío", "Por favor, ingresa una calle");
            return false;
        }
        else if (txtNumero.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Número está vacío", "Por favor, ingresa un número");
            return false;
        }
        else if (txtColonia.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Colonia está vacío", "Por favor, ingresa una colonia");
            return false;
        }
        else if (txtCodigoPostal.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Código Postal está vacío", "Por favor, ingresa un código postal");
            return false;
        }
        else if (cmbCiudad.getValue() == null)
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo Ciudad está vacío", "Por favor, ingresa una ciudad");
            return false;
        }
        else
        {
            return true;
        }
    }

    private void limpiaCampos()
    {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");

        txtCalle.setText("");
        txtNumero.setText("");
        txtColonia.setText("");
        txtCodigoPostal.setText("");

        cmbCiudad.getSelectionModel().clearSelection();
    }

    private boolean validarCorreo()
    {
        HuespedCRUD huespedCRUD = new HuespedCRUD();

        if (!huespedCRUD.select("WHERE correo = '" + txtCorreo.getText() + "'").isEmpty())
        {
            return true;
        }
        else
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Correo incorrecto", "Has ingresado un correo incorrecto", "Vuelve a intentarlo");
            return false;
        }
    }

    private void initComponentes()
    {
        CiudadCRUD crud = new CiudadCRUD();

        List<Ciudad> ciudades = crud.select("");

        for (Ciudad ciudad : ciudades)
            cmbCiudad.getItems().addAll(ciudad.getNombre());

        cmbCriterio.getItems().addAll("ID", "@", "Tel");
        cmbCriterio.getSelectionModel().selectFirst();
    }
}
