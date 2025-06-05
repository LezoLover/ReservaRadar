package bd.java.controllers;

import bd.java.classes.*;
import bd.java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HuespedDetallesController
{
    @FXML
    private VBox ventana;
    @FXML
    private TextField txtNombre, txtApellido, txtTelefono, txtCorreo;
    @FXML
    private TextField txtCalle, txtNumero, txtColonia, txtCodigoPostal;
    @FXML
    private Button btnEditarInfo, btnGuardarInfo, btnEditarDomi, btnGuardarDomi;
    @FXML
    private ComboBox<String> cmbCiudad;
    @FXML
    private VBox vboxReservas;

    @FXML
    private void initialize()
    {
        initComponents();
    }

    @FXML
    private void editarInfoClick()
    {
        // Habilitar editado de campos
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
        txtCorreo.setEditable(true);

        // Habilitar botón de guardar
        btnGuardarInfo.setDisable(false);

        // Deshabilitar botón de editar
        btnEditarInfo.setDisable(true);
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

            // Deshabilitar editado de campos
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);

            // Deshabilitar botón de guardar
            btnGuardarInfo.setDisable(true);

            // Habilitar botón de editar
            btnEditarInfo.setDisable(false);
        }
    }

    @FXML
    private void editarDomiClick()
    {
        // Habilitar editado de campos
        txtCalle.setEditable(true);
        txtNumero.setEditable(true);
        txtColonia.setEditable(true);
        txtCodigoPostal.setEditable(true);
        cmbCiudad.setDisable(false);

        // Habilitar botón de guardar
        btnGuardarDomi.setDisable(false);

        // Deshabilitar botón de editar
        btnEditarDomi.setDisable(true);
    }

    @FXML
    private void guardarDomiClick()
    {
        if (validarCampos())
        {
            // Obtenemos el ID de la ciudad para asignarlo al Domicilio
            Ciudad ciudad = new Ciudad();
            CiudadCRUD ciudadCRUD = new CiudadCRUD();

            List<Ciudad> ciudades = ciudadCRUD.select("WHERE nombre = '" + cmbCiudad.getValue() + "'");

            if (!ciudades.isEmpty())
                ciudad = ciudades.get(0);

            Integer idf_ciudad = ciudad.getId_ciudad();

            // Obtenemos el Huesped
            HuespedCRUD huespedCRUD = new HuespedCRUD();
            Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

            // Actualizamos los datos del Domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + huesped.getIdf_domicilio()).get(0);

            domicilio.setCalle(txtCalle.getText());
            domicilio.setNumero(Integer.parseInt(txtNumero.getText()));
            domicilio.setColonia(txtColonia.getText());
            domicilio.setCodigo_postal(txtCodigoPostal.getText());
            domicilio.setIdf_Ciudad(idf_ciudad);

            domicilioCRUD.update(domicilio);

            // Deshabilitar editado de campos
            txtCalle.setEditable(false);
            txtNumero.setEditable(false);
            txtColonia.setEditable(false);
            txtCodigoPostal.setEditable(false);
            cmbCiudad.setDisable(true);

            // Deshabilitar botón de guardar
            btnGuardarDomi.setDisable(true);

            // Habilitar botón de editar
            btnEditarDomi.setDisable(false);
        }
    }

    @FXML
    private void eliminarClick()
    {
        // Creamos una ventana de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar tu cuenta?");
        alert.setContentText("Esta acción no se puede deshacer");

        // Mostramos la ventana y esperamos a que el usuario presione un botón
        alert.showAndWait();

        // Si el usuario presiona OK, eliminamos la cuenta
        if (alert.getResult() == alert.getButtonTypes().get(0))
        {
            // Obtenemos el Huesped
            HuespedCRUD huespedCRUD = new HuespedCRUD();
            Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

            // Eliminamos el Domicilio
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            domicilioCRUD.delete(huesped.getIdf_domicilio());

            // Eliminamos el Huesped
            huespedCRUD.delete(huesped.getId_huesped());

            // Cerramos la ventana
            cerrarVentana();

            // Abrimos la ventana de login
            LoginController.abrirVentana("login", "ReservaRadar");
        }
    }

    @FXML
    private void regresarClick()
    {
        LoginController.abrirVentana("main", "ReservaRadar");
        cerrarVentana();
    }

    // Métodos
    private void anadirReservas()
    {
        // Creamos un VBox para cada reserva
        ReservaCRUD reservaCRUD = new ReservaCRUD();
        List<Reserva> reservas = reservaCRUD.select("WHERE idf_huesped = " + Huesped.id_huesped_static);

        for (Reserva reserva : reservas)
        {
            VBox vboxReserva = new VBox();
            vboxReserva.setSpacing(5);
            vboxReserva.setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 10px; -fx-border-radius: 5px;");

            // Obtenemos el nombre del hotel
            HotelCRUD hotelCRUD = new HotelCRUD();
            Hotel hotel = hotelCRUD.select("WHERE id_hotel = " + reserva.getIdf_hotel()).get(0);

            // Obtenemos el domicilio del hotel
            DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
            Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + hotel.getIdf_domicilio()).get(0);

            // Obtenemos la ciudad del hotel
            CiudadCRUD ciudadCRUD = new CiudadCRUD();
            Ciudad ciudad = ciudadCRUD.select("WHERE id_ciudad = " + domicilio.getIdf_Ciudad()).get(0);

            // Creamos un Label para cada dato de la reserva
            Label lblID = new Label("ID: " + reserva.getId_reserva());
            lblID.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

            Label lblHotel = new Label(hotel.getNombre());
            lblHotel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label lblDomicilio1 = new Label(domicilio.getCalle() + " " + domicilio.getNumero() + ", " + domicilio.getColonia());
            Label lblDomicilio2 = new Label(ciudad.getNombre() + ", " + domicilio.getCodigo_postal());

            Separator separador0 = new Separator();

            Label lblFechas = new Label("Fechas: " + reserva.getFecha_llegada() + " - " + reserva.getFecha_salida());
            Label lblNumHuespedes = new Label("Huespedes: " + reserva.getNum_huespedes());

            Separator separador1 = new Separator();

            vboxReserva.getChildren().addAll(lblID, lblHotel, lblDomicilio1, lblDomicilio2, separador0, lblFechas, lblNumHuespedes, separador1);

            // Obtnemos las relaciones de la reserva con la habitacion
            HabResCRUD habResCRUD = new HabResCRUD();
            List<HabRes> relaciones = habResCRUD.select("WHERE idf_reserva = " + reserva.getId_reserva());

            for (HabRes habRes : relaciones)
            {
                HBox hboxHabitacion = new HBox();
                hboxHabitacion.setSpacing(5);

                // Obtenemos la habitacion
                HabitacionCRUD habitacionCRUD = new HabitacionCRUD();
                Habitacion habitacion = habitacionCRUD.select("WHERE id_habitacion = " + habRes.getIdf_habitacion()).get(0);

                // Obtenemos el tipo de habitacion
                TipoHabitacionCRUD tipoHabCRUD = new TipoHabitacionCRUD();
                TipoHabitacion tipoHab = tipoHabCRUD.select("WHERE id_tipo_habitacion = " + habitacion.getIdf_tipo_habitacion()).get(0);

                // Creamos un Label para cada dato de la habitacion
                Label lblTipoHab = new Label(tipoHab.getNombre());
                Label lblNumHab = new Label("#" + habitacion.getNumero());
                Label lblPrecio = new Label("$" + tipoHab.getPrecio_base());

                hboxHabitacion.getChildren().addAll(lblTipoHab, lblNumHab, lblPrecio);

                vboxReserva.getChildren().add(hboxHabitacion);
            }

            // Obtenemos los detalles del pago
            PagoCRUD pagoCRUD = new PagoCRUD();
            Pago pago = pagoCRUD.select("WHERE idf_reserva = " + reserva.getId_reserva()).get(0);

            Separator separador2 = new Separator();

            // Creamos un Label para cada dato del pago
            Label lblTotal = new Label("Total: $" + pago.getCantidad());
            Label lblMetodo = new Label("Método: " + pago.getMetodo_pago());

            vboxReserva.getChildren().addAll(separador2, lblTotal, lblMetodo);

            Button btnEliminar = new Button("Eliminar");
            btnEliminar.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 12px; -fx-border-radius: 5px; -fx-cursor: hand;");

            btnEliminar.setOnAction(event ->
            {
                // Creamos una ventana de confirmación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Estás seguro de eliminar esta reserva?");
                alert.setContentText("Esta acción no se puede deshacer");

                // Mostramos la ventana y esperamos a que el usuario presione un botón
                alert.showAndWait();

                //Obtenemos los habres de la reserva
                List<HabRes> habRes = habResCRUD.select("WHERE idf_reserva = " + reserva.getId_reserva());

                // Si el usuario presiona OK, eliminamos la reserva
                if (alert.getResult() == alert.getButtonTypes().get(0))
                {
                    // Eliminamos los habres
                    for (HabRes hab : habRes)
                        habResCRUD.delete(hab.getId_hab_res());

                    // Eliminamos el pago
                    pagoCRUD.delete(pago.getId_pago());

                    // Eliminamos la reserva
                    reservaCRUD.delete(reserva.getId_reserva());

                    // Eliminamos el VBox de la reserva
                    vboxReservas.getChildren().remove(vboxReserva);
                }
            });

            vboxReserva.getChildren().add(btnEliminar);

            vboxReservas.getChildren().add(vboxReserva);
        }
    }

    private void cerrarVentana()
    {
        Stage stage = (Stage) ventana.getScene().getWindow();
        stage.setOnCloseRequest(event ->
        {
        });
        stage.close();
    }

    private boolean validarCampos()
    {
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
            return true;
        }
        else
        {
            LoginController.mostrarAlerta(Alert.AlertType.ERROR, "Correo incorrecto", "Has ingresado un correo incorrecto", "Vuelve a intentarlo");
            return false;
        }
    }

    private void initComponents()
    {
        // Iniciar combobox destinos
        CiudadCRUD crud = new CiudadCRUD();

        List<Ciudad> ciudades = crud.select("");

        for (Ciudad ciudad : ciudades)
            cmbCiudad.getItems().addAll(ciudad.getNombre());

        // Rellenar campos con datos del usuario
        HuespedCRUD huespedCRUD = new HuespedCRUD();
        Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

        txtNombre.setText(huesped.getNombre());
        txtApellido.setText(huesped.getApellido());
        txtTelefono.setText(huesped.getTelefono());
        txtCorreo.setText(huesped.getCorreo());

        // Rellenar campos con datos del domicilio
        DomicilioCRUD domicilioCRUD = new DomicilioCRUD();
        Domicilio domicilio = domicilioCRUD.select("WHERE id_domicilio = " + huesped.getIdf_domicilio()).get(0);

        txtCalle.setText(domicilio.getCalle());
        txtNumero.setText(String.valueOf(domicilio.getNumero()));
        txtColonia.setText(domicilio.getColonia());
        txtCodigoPostal.setText(domicilio.getCodigo_postal());

        // Obtenemos la ciudad del domicilio
        Ciudad ciudad = crud.select("WHERE id_ciudad = " + domicilio.getIdf_Ciudad()).get(0);

        cmbCiudad.setValue(ciudad.getNombre());

        // Añadimos las reservaciones del usuario
        anadirReservas();
    }
}
