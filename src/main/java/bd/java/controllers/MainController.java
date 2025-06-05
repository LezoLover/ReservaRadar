package bd.java.controllers;

import bd.java.classes.*;
import bd.java.sql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController
{
    @FXML
    private VBox ventana;
    @FXML
    private ComboBox<String> cmbDestino, cmbMetodoPago;
    @FXML
    private DatePicker dtpLlegada, dtpSalida;
    @FXML
    private TextField txtNumHuespedes;
    @FXML
    private ScrollPane paneHoteles, paneHabitaciones, panePago;
    @FXML
    private Label lblNombreHuesped, lblTotal;

    private final List<Habitacion> habitacionesReservadas = new ArrayList<>();

    @FXML
    private void initialize()
    {
        initComponents();
    }

    @FXML
    private void detallesClick()
    {
        // Borramos todos los contenidos
        paneHoteles.setContent(null);
        paneHabitaciones.setContent(null);
        panePago.setContent(null);

        // Vaciamos la lista de habitaciones reservadas
        habitacionesReservadas.clear();

        // Vaciamos el precio total
        lblTotal.setText("0");
    }

    @FXML
    private void buscarClick()
    {
        try
        {
            // Creamos un VBox para los detalles del pago
            VBox vboxPago = new VBox();
            vboxPago.setPadding(new Insets(10, 10, 10, 10));
            vboxPago.setSpacing(10);

            // Creamos un Label para el titulo
            Label lblTituloPago = new Label("Detalles del pago");
            lblTituloPago.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            vboxPago.getChildren().add(lblTituloPago);

            // Obtenemos el id de la ciudad
            CiudadCRUD crudCiudad = new CiudadCRUD();
            Ciudad ciudad = crudCiudad.select("WHERE nombre = '" + cmbDestino.getValue() + "'").get(0);

            // Obtenemos la fecha de llegada y salida (YYYY-MM-DD)
            String fecha_llegada = dtpLlegada.getValue().toString();
            String fecha_salida = dtpSalida.getValue().toString();

            // Buscamos hoteles por ciudad y que tengan disponibles las fechas en las que se quiere reservar
            HotelCRUD crud = new HotelCRUD();
            List<Hotel> hoteles = crud.selectDistinct("JOIN domicilio d ON hotel.idf_domicilio = d.id_domicilio\n" +
                    "JOIN ciudad c ON d.idf_ciudad = c.id_ciudad\n" +
                    "LEFT JOIN habitacion hab ON hotel.id_hotel = hab.idf_hotel\n" +
                    "LEFT JOIN hab_res hr ON hab.id_habitacion = hr.idf_habitacion\n" +
                    "LEFT JOIN reserva r ON hr.idf_reserva = r.id_reserva\n" +
                    "WHERE c.nombre = '" + ciudad.getNombre() + "'\n" +
                    "AND (\n" +
                    "    r.id_reserva IS NULL OR\n" +
                    "    NOT (\n" +
                    "        r.fecha_salida >= '" + fecha_llegada + "' AND r.fecha_llegada <= '" + fecha_salida + "'\n" +
                    "    )\n" +
                    ")");

            // Creamos un VBox para los hoteles
            VBox vboxHotel = new VBox();
            vboxHotel.setPadding(new Insets(10, 10, 10, 10));
            vboxHotel.setSpacing(10);

            // Creamos un Label para el titulo
            Label lblTitulo = new Label("Hoteles");
            lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            vboxHotel.getChildren().add(lblTitulo);

            for (Hotel hotel : hoteles)
            {
                // Creamos un VBox para cada hotel
                VBox vboxHotel_i = new VBox();
                vboxHotel_i.setPadding(new Insets(10, 10, 10, 10));
                vboxHotel_i.setSpacing(5);
                vboxHotel_i.setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");

                // Creamos un Label para el nombre del hotel
                Label lblNombre = new Label(hotel.getNombre());
                lblNombre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                // Creamos un HBox para los detalles del hotel
                HBox hboxDetalles = new HBox();
                hboxDetalles.setSpacing(10);

                // Obtenemos el domicilio del hotel
                DomicilioCRUD crudDomicilio = new DomicilioCRUD();
                Domicilio domicilio = crudDomicilio.select("WHERE id_domicilio = " + hotel.getIdf_domicilio()).get(0);

                // Creamos un Label para el domicilio del hotel
                Label lblDomicilio = new Label(domicilio.getCalle() + " " + domicilio.getNumero() + ", " + domicilio.getColonia() + ", CP. " + domicilio.getCodigo_postal());

                // Agregamos el Label del domicilio al HBox
                hboxDetalles.getChildren().add(lblDomicilio);

                // Creamos un Label para el teléfono del hotel
                Label lblTelefono = new Label(hotel.getTelefono());

                // Creamos un Button para reservar
                Button btnReservarHotel = new Button("Reservar");
                btnReservarHotel.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                // Creamos el evento para el botón de reservar del HOTEL
                btnReservarHotel.setOnAction(actionEvent_0 ->
                {
                    // Obtenemos la cantidad de noche que se va a hospedar
                    int noches = ChronoUnit.DAYS.between(dtpLlegada.getValue(), dtpSalida.getValue()) == 0 ? 1 : (int) ChronoUnit.DAYS.between(dtpLlegada.getValue(), dtpSalida.getValue());

                    // Asignamos el id del hotel a la variable estática
                    Hotel.id_hotel_static = hotel.getId_hotel();

                    // Vaciamos el VBox de pago y el total
                    vboxPago.getChildren().clear();
                    lblTotal.setText("0");

                    // Vaciamos la lista de habitaciones reservadas
                    habitacionesReservadas.clear();

                    // Creamos un VBox para las habitaciones
                    VBox vboxHabitaciones = new VBox();
                    vboxHabitaciones.setPadding(new Insets(10, 10, 10, 10));
                    vboxHabitaciones.setSpacing(5);

                    // Creamos un Label para el titulo
                    Label lblTitulo1 = new Label("Habitaciones");
                    lblTitulo1.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                    vboxHabitaciones.getChildren().add(lblTitulo1);

                    // Creamos un HBox para los tipos de habitación
                    HBox hboxTiposHabitacion = new HBox();
                    hboxTiposHabitacion.setSpacing(10);

                    // ********* Tipo de habitación (Doble) ********* //
                    TipoHabitacion tipoHabitacion = obtenerTipoHabitacion(1);
                    VBox vboxHabitacionDoble = crearVBoxHabitacion("Doble", tipoHabitacion);

                    // Obtenemos las habitaciones dobles del hotel que estén disponibles en las fechas seleccionadas
                    List<Habitacion> habDisponibles = obetenerHabitacionesDisponibles(hotel.getId_hotel(), tipoHabitacion.getId_tipo_habitacion(), fecha_llegada, fecha_salida);

                    for (Habitacion habitacion : habDisponibles)
                    {
                        // Creamos un HBox para cada habitación
                        HBox hboxHabitacion = crearHBoxHabitacion(habitacion);

                        // Creamos un Button para reservar
                        Button btnAnadir = new Button("Añadir");
                        btnAnadir.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                        // Creamos el evento para el botón de reservar
                        TipoHabitacion finalTipoHabitacion = tipoHabitacion;
                        btnAnadir.setOnAction(actionEvent_1 ->
                        {
                            // Deshabilitamos el botón de reservar
                            btnAnadir.setDisable(true);

                            // Creamos un Label para el tipo y número de habitación + su precio y su botón de eliminar
                            HBox hboxHabitacionReservada = crearHBoxHabitacionReservada(finalTipoHabitacion, habitacion);

                            Button btnEliminarHabitacionReservada = new Button("Eliminar");
                            btnEliminarHabitacionReservada.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                            // Creamos el evento para el botón de eliminar
                            btnEliminarHabitacionReservada.setOnAction(actionEvent1 ->
                            {
                                // Habilitamos el botón de reservar
                                btnAnadir.setDisable(false);

                                // Eliminamos el HBox de la habitación reservada
                                vboxPago.getChildren().remove(hboxHabitacionReservada);

                                // Eliminamos la habitación de la lista de habitaciones reservadas
                                habitacionesReservadas.remove(habitacion);

                                // Actualizamos el total
                                int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                                int total = Integer.parseInt(lblTotal.getText());

                                lblTotal.setText(String.valueOf(total - costoHab));
                            });

                            // Agregamos la habitación a la lista de habitaciones reservadas
                            habitacionesReservadas.add(habitacion);

                            hboxHabitacionReservada.getChildren().addAll(btnEliminarHabitacionReservada);
                            vboxPago.getChildren().add(hboxHabitacionReservada);

                            // Actualizamos el total
                            int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                            int total = Integer.parseInt(lblTotal.getText());

                            lblTotal.setText(String.valueOf(total + costoHab));
                        });

                        hboxHabitacion.getChildren().addAll(btnAnadir);
                        vboxHabitacionDoble.getChildren().add(hboxHabitacion);
                    }

                    // Agregamos el VBox de habitaciones dobles al VBox principal
                    hboxTiposHabitacion.getChildren().add(vboxHabitacionDoble);

                    // ********* Tipo de habitación (Triple) ********* //
                    tipoHabitacion = obtenerTipoHabitacion(2);
                    VBox vboxHabitacionTriple = crearVBoxHabitacion("Triple", tipoHabitacion);

                    // Obtenemos las habitaciones dobles del hotel que estén disponibles en las fechas seleccionadas
                    habDisponibles = obetenerHabitacionesDisponibles(hotel.getId_hotel(), tipoHabitacion.getId_tipo_habitacion(), fecha_llegada, fecha_salida);

                    for (Habitacion habitacion : habDisponibles)
                    {
                        // Creamos un HBox para cada habitación
                        HBox hboxHabitacion = crearHBoxHabitacion(habitacion);

                        // Creamos un Button para reservar
                        Button btnAnadir = new Button("Añadir");
                        btnAnadir.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                        // Creamos el evento para el botón de reservar
                        TipoHabitacion finalTipoHabitacion = tipoHabitacion;
                        btnAnadir.setOnAction(actionEvent_2 ->
                        {
                            // Deshabilitamos el botón de reservar
                            btnAnadir.setDisable(true);

                            // Creamos un Label para el tipo y número de habitación + su precio y su botón de eliminar
                            HBox hboxHabitacionReservada = crearHBoxHabitacionReservada(finalTipoHabitacion, habitacion);

                            Button btnEliminarHabitacionReservada = new Button("Eliminar");
                            btnEliminarHabitacionReservada.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                            // Creamos el evento para el botón de eliminar
                            btnEliminarHabitacionReservada.setOnAction(actionEvent131 ->
                            {
                                // Habilitamos el botón de reservar
                                btnAnadir.setDisable(false);

                                // Eliminamos el HBox de la habitación reservada
                                vboxPago.getChildren().remove(hboxHabitacionReservada);

                                // Eliminamos la habitación de la lista de habitaciones reservadas
                                habitacionesReservadas.remove(habitacion);

                                // Actualizamos el total
                                int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                                int total = Integer.parseInt(lblTotal.getText());

                                lblTotal.setText(String.valueOf(total - costoHab));
                            });

                            // Agregamos la habitación a la lista de habitaciones reservadas
                            habitacionesReservadas.add(habitacion);

                            hboxHabitacionReservada.getChildren().addAll(btnEliminarHabitacionReservada);
                            vboxPago.getChildren().add(hboxHabitacionReservada);

                            // Actualizamos el total
                            int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                            int total = Integer.parseInt(lblTotal.getText());

                            lblTotal.setText(String.valueOf(total + costoHab));
                        });

                        // Agregamos los elementos al HBox
                        hboxHabitacion.getChildren().addAll(btnAnadir);

                        // Agregamos el HBox al VBox
                        vboxHabitacionTriple.getChildren().add(hboxHabitacion);
                    }

                    // Agregamos el VBox de habitaciones dobles al VBox principal
                    hboxTiposHabitacion.getChildren().add(vboxHabitacionTriple);

                    // ********* Tipo de habitación (Suite) ********* //
                    tipoHabitacion = obtenerTipoHabitacion(3);
                    VBox vboxHabitacionSuite = crearVBoxHabitacion("Suite", tipoHabitacion);

                    // Obtenemos las habitaciones dobles del hotel que estén disponibles en las fechas seleccionadas
                    habDisponibles = obetenerHabitacionesDisponibles(hotel.getId_hotel(), tipoHabitacion.getId_tipo_habitacion(), fecha_llegada, fecha_salida);

                    for (Habitacion habitacion : habDisponibles)
                    {
                        // Creamos un HBox para cada habitación
                        HBox hboxHabitacion = crearHBoxHabitacion(habitacion);

                        // Creamos un Button para reservar
                        Button btnAnadir = new Button("Añadir");
                        btnAnadir.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                        // Creamos el evento para el botón de reservar
                        TipoHabitacion finalTipoHabitacion = tipoHabitacion;
                        btnAnadir.setOnAction(actionEvent_3 ->
                        {
                            // Deshabilitamos el botón de reservar
                            btnAnadir.setDisable(true);

                            // Creamos un Label para el tipo y número de habitación + su precio y su botón de eliminar
                            HBox hboxHabitacionReservada = crearHBoxHabitacionReservada(finalTipoHabitacion, habitacion);

                            Button btnEliminarHabitacionReservada = new Button("Eliminar");
                            btnEliminarHabitacionReservada.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                            // Creamos el evento para el botón de eliminar
                            btnEliminarHabitacionReservada.setOnAction(actionEvent141 ->
                            {
                                // Habilitamos el botón de reservar
                                btnAnadir.setDisable(false);

                                // Eliminamos el HBox de la habitación reservada
                                vboxPago.getChildren().remove(hboxHabitacionReservada);

                                // Eliminamos la habitación de la lista de habitaciones reservadas
                                habitacionesReservadas.remove(habitacion);

                                // Actualizamos el total
                                int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                                int total = Integer.parseInt(lblTotal.getText());

                                lblTotal.setText(String.valueOf(total - costoHab));
                            });

                            // Agregamos la habitación a la lista de habitaciones reservadas
                            habitacionesReservadas.add(habitacion);

                            hboxHabitacionReservada.getChildren().addAll(btnEliminarHabitacionReservada);
                            vboxPago.getChildren().add(hboxHabitacionReservada);

                            // Actualizamos el total
                            int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                            int total = Integer.parseInt(lblTotal.getText());

                            lblTotal.setText(String.valueOf(total + costoHab));
                        });

                        // Agregamos los elementos al HBox
                        hboxHabitacion.getChildren().addAll(btnAnadir);
                        vboxHabitacionSuite.getChildren().add(hboxHabitacion);
                    }

                    hboxTiposHabitacion.getChildren().add(vboxHabitacionSuite);

                    // ********* Tipo de habitación (Presidencial) ********* //
                    tipoHabitacion = obtenerTipoHabitacion(4);
                    VBox vboxHabitacionPresidencial = crearVBoxHabitacion("Presidencial", tipoHabitacion);

                    // Obtenemos las habitaciones dobles del hotel que estén disponibles en las fechas seleccionadas
                    habDisponibles = obetenerHabitacionesDisponibles(hotel.getId_hotel(), tipoHabitacion.getId_tipo_habitacion(), fecha_llegada, fecha_salida);

                    for (Habitacion habitacion : habDisponibles)
                    {
                        // Creamos un HBox para cada habitación
                        HBox hboxHabitacion = crearHBoxHabitacion(habitacion);

                        // Creamos un Button para añadir
                        Button btnAnadir = new Button("Añadir");
                        btnAnadir.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                        // Creamos el evento para el botón de AÑADIR
                        TipoHabitacion finalTipoHabitacion = tipoHabitacion;
                        btnAnadir.setOnAction(actionEvent_4 ->
                        {
                            // Deshabilitamos el botón de añadir
                            btnAnadir.setDisable(true);

                            // Creamos un Label para el tipo y número de habitación + su precio y su botón de eliminar
                            HBox hboxHabitacionReservada = crearHBoxHabitacionReservada(finalTipoHabitacion, habitacion);

                            Button btnEliminarHabitacionReservada = new Button("Eliminar");
                            btnEliminarHabitacionReservada.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-cursor: hand;");

                            // Creamos el evento para el botón de eliminar
                            btnEliminarHabitacionReservada.setOnAction(actionEvent151 ->
                            {
                                // Habilitamos el botón de reservar
                                btnAnadir.setDisable(false);

                                // Eliminamos el HBox de la habitación reservada
                                vboxPago.getChildren().remove(hboxHabitacionReservada);

                                // Eliminamos la habitación de la lista de habitaciones reservadas
                                habitacionesReservadas.remove(habitacion);

                                // Actualizamos el total
                                int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                                int total = Integer.parseInt(lblTotal.getText());

                                lblTotal.setText(String.valueOf(total - costoHab));
                            });

                            // Agregamos la habitación a la lista de habitaciones reservadas
                            habitacionesReservadas.add(habitacion);

                            hboxHabitacionReservada.getChildren().addAll(btnEliminarHabitacionReservada);
                            vboxPago.getChildren().add(hboxHabitacionReservada);

                            // Actualizamos el total
                            int costoHab = noches * finalTipoHabitacion.getPrecio_base();
                            int total = Integer.parseInt(lblTotal.getText());

                            lblTotal.setText(String.valueOf(total + costoHab));
                        });

                        hboxHabitacion.getChildren().addAll(btnAnadir);
                        vboxHabitacionPresidencial.getChildren().add(hboxHabitacion);
                    }

                    hboxTiposHabitacion.getChildren().add(vboxHabitacionPresidencial);
                    vboxHabitaciones.getChildren().add(hboxTiposHabitacion);

                    // Agregamos el VBox de habitaciones al ScrollPane
                    paneHabitaciones.setContent(vboxHabitaciones);
                });

                // Agregamos todos los elemmentos al VBox
                vboxHotel_i.getChildren().addAll(lblNombre, hboxDetalles, lblTelefono, btnReservarHotel);

                // Agregamos el VBox del hotel al VBox principal
                vboxHotel.getChildren().add(vboxHotel_i);
            }
            panePago.setContent(vboxPago);
            paneHoteles.setContent(vboxHotel);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Class: MainController");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void huespedClick()
    {
        LoginController.abrirVentana("huesped-detalles", "Herramientas de Huésped");
        cerrarVentana();
    }

    @FXML
    private void pagarClick()
    {
        if (validarSeleccion())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de pago");
            alert.setHeaderText("¿Estás seguro de que deseas realizar el pago?");
            alert.setContentText("El total a pagar es de $" + lblTotal.getText() + ".00 MXN");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                try
                {
                    // Creamos la reserva
                    Reserva reserva = new Reserva();
                    reserva.setFecha_llegada(Date.valueOf(dtpLlegada.getValue()));
                    reserva.setFecha_salida(Date.valueOf(dtpSalida.getValue()));
                    reserva.setNum_huespedes(Integer.parseInt(txtNumHuespedes.getText()));
                    reserva.setIdf_hotel(Hotel.id_hotel_static);
                    reserva.setIdf_huesped(Huesped.id_huesped_static);

                    ReservaCRUD crudReserva = new ReservaCRUD();
                    int id_reserva = crudReserva.insert(reserva);

                    // Creamos el pago
                    Pago pago = new Pago();
                    pago.setCantidad(Double.parseDouble(lblTotal.getText()));
                    pago.setMetodo_pago(cmbMetodoPago.getValue());
                    pago.setFecha(Date.valueOf(LocalDate.now()));
                    pago.setIdf_reserva(id_reserva);

                    PagoCRUD crudPago = new PagoCRUD();
                    crudPago.insert(pago);

                    // Creamos los registros de las habitaciones reservadas
                    HabRes habRes = new HabRes();
                    habRes.setIdf_reserva(id_reserva);

                    HabResCRUD crudHabRes = new HabResCRUD();

                    for (Habitacion habitacion : habitacionesReservadas)
                    {
                        habRes.setIdf_habitacion(habitacion.getId_habitacion());
                        crudHabRes.insert(habRes);
                    }

                    // Mostramos el mensaje de éxito
                    LoginController.mostrarAlerta(Alert.AlertType.INFORMATION, "Pago realizado", "El pago se ha realizado con éxito", "¡Gracias por reservar con nosotros!");

                    limpiarCampos();
                }
                catch (Exception e)
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error Class: MainController");
                    alert1.setContentText(e.getMessage());
                    alert1.showAndWait();
                }
            }
        }
    }

    @FXML
    private void regresarClick()
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

    private VBox crearVBoxHabitacion(String titulo, TipoHabitacion tipoHabitacion)
    {
        VBox vboxHabitacion = new VBox();
        vboxHabitacion.setPadding(new Insets(10, 10, 10, 10));
        vboxHabitacion.setSpacing(5);
        vboxHabitacion.setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblCapacidad = new Label("Capacidad: " + tipoHabitacion.getCapacidad() + " personas");
        Label lblPrecio = new Label("Precio: $" + tipoHabitacion.getPrecio_base() + " p/noche");

        Separator separator = new Separator();

        vboxHabitacion.getChildren().addAll(lblTitulo, lblCapacidad, lblPrecio, separator);

        return vboxHabitacion;
    }

    private HBox crearHBoxHabitacion(Habitacion habitacion)
    {
        HBox hboxHabitacion = new HBox();
        hboxHabitacion.setSpacing(15);

        Label lblNumeroHabitacion = new Label("#" + habitacion.getNumero());

        hboxHabitacion.getChildren().add(lblNumeroHabitacion);

        return hboxHabitacion;
    }

    private HBox crearHBoxHabitacionReservada(TipoHabitacion tipoHabitacion, Habitacion habitacion)
    {
        HBox hboxHabitacionReservada = new HBox();
        hboxHabitacionReservada.setSpacing(15);
        hboxHabitacionReservada.setAlignment(Pos.CENTER_LEFT);

        Label lblHabitacionReservada = new Label(tipoHabitacion.getNombre() + " #" + habitacion.getNumero());
        Label lblPrecioHabitacionReservada = new Label("$" + tipoHabitacion.getPrecio_base() + " p/noche");

        hboxHabitacionReservada.getChildren().addAll(lblHabitacionReservada, lblPrecioHabitacionReservada);

        return hboxHabitacionReservada;
    }

    private TipoHabitacion obtenerTipoHabitacion(int tipo)
    {
        TipoHabitacionCRUD crud = new TipoHabitacionCRUD();

        return crud.select("WHERE id_tipo_habitacion = " + tipo).get(0);
    }

    private List<Habitacion> obetenerHabitacionesDisponibles(int id_hotel, int id_tipo_habitacion, String fecha_llegada, String fecha_salida)
    {
        HabitacionCRUD crud = new HabitacionCRUD();

        return crud.selectDisctint("JOIN tipo_habitacion ON habitacion.idf_tipo_habitacion = tipo_habitacion.id_tipo_habitacion\n" +
                "WHERE habitacion.idf_hotel = " + id_hotel + "\n" +
                "  AND habitacion.idf_tipo_habitacion = " + id_tipo_habitacion + "\n" +
                "  AND NOT EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM hab_res\n" +
                "    JOIN reserva ON hab_res.idf_reserva = reserva.id_reserva\n" +
                "    WHERE hab_res.idf_habitacion = habitacion.id_habitacion\n" +
                "      AND (\n" +
                "        (reserva.fecha_salida >= '" + fecha_llegada + "' AND reserva.fecha_llegada <= '" + fecha_salida + "') OR\n" +
                "        (reserva.fecha_llegada <= '" + fecha_llegada + "' AND reserva.fecha_salida >= '" + fecha_salida + "')\n" +
                "      )\n" +
                "  )");
    }

    private boolean validarSeleccion()
    {
        if (cmbDestino.getValue() == null)
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Destino no seleccionado", "No has seleccionado un destino", "Por favor, selecciona un destino");
            return false;
        }
        else if (dtpLlegada.getValue() == null)
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Fecha de llegada no seleccionada", "No has seleccionado una fecha de llegada", "Por favor, selecciona una fecha de llegada");
            return false;
        }
        else if (dtpSalida.getValue() == null)
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Fecha de salida no seleccionada", "No has seleccionado una fecha de salida", "Por favor, selecciona una fecha de salida");
            return false;
        }
        else if (txtNumHuespedes.getText().isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Número de huéspedes no seleccionado", "No has seleccionado un número de huéspedes", "Por favor, selecciona un número de huéspedes");
            return false;
        }
        else if (cmbMetodoPago.getValue() == null)
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Método de pago no seleccionado", "No has seleccionado un método de pago", "Por favor, selecciona un método de pago");
            return false;
        }
        else if (habitacionesReservadas.isEmpty())
        {
            LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Habitaciones no seleccionadas", "No has seleccionado ninguna habitación", "Por favor, selecciona al menos una habitación");
            return false;
        }
        else
        {
            return true;
        }
    }

    private void limpiarCampos()
    {
        cmbDestino.setValue(null);
        dtpLlegada.setValue(null);
        dtpSalida.setValue(null);
        txtNumHuespedes.setText("");
        cmbMetodoPago.setValue(null);
        paneHoteles.setContent(null);
        paneHabitaciones.setContent(null);
        panePago.setContent(null);
        lblTotal.setText("0");
    }

    private void initComponents()
    {
        // Iniciar Label nombre del huesped
        HuespedCRUD huespedCRUD = new HuespedCRUD();
        Huesped huesped = huespedCRUD.select("WHERE id_huesped = " + Huesped.id_huesped_static).get(0);

        lblNombreHuesped.setText("Bienvenid@, " + huesped.getNombre() + " " + huesped.getApellido());

        // Iniciar combobox destinos
        CiudadCRUD crud = new CiudadCRUD();

        List<Ciudad> ciudades = crud.select("");

        for (Ciudad ciudad : ciudades)
            cmbDestino.getItems().addAll(ciudad.getNombre());

        // Init combobox metodos de pago
        cmbMetodoPago.getItems().addAll("Efectivo", "Transferencia");

        // Restringir fechas
        dtpLlegada.setDayCellFactory(picker -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now().plusDays(1)));
            }
        });

        dtpLlegada.valueProperty().addListener((obs, oldDate, newDate) ->
        {
            dtpSalida.setValue(null);

            dtpSalida.setDayCellFactory(picker -> new DateCell()
            {
                @Override
                public void updateItem(LocalDate date, boolean empty)
                {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(newDate.plusDays(1)));
                }
            });
        });
    }
}