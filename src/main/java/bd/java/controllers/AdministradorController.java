package bd.java.controllers;

import bd.java.classes.*;
import bd.java.sql.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class AdministradorController
{
    @FXML
    private VBox ventana;
    @FXML
    private ComboBox<String> cmbEntidades, cmbEdicion;
    @FXML
    private VBox vTable;

    @FXML
    private void initialize()
    {
        initComponents();
    }

    @FXML
    private void buscarClick()
    {
        switch (cmbEdicion.getValue())
        {
            case "Huéspedes" ->
            {
                LoginController.abrirVentana("huesped-admin", "Editar huésped");
                cerrarVentana();
            }

            case "Hoteles" ->
            {
                LoginController.abrirVentana("hotel-admin", "Editar hotel");
                cerrarVentana();
            }

            case "Reservas" ->
            {
                LoginController.abrirVentana("reserva-admin", "Editar reserva");
                cerrarVentana();
            }
            default -> {
                LoginController.mostrarAlerta(Alert.AlertType.WARNING, "Error", "No se ha seleccionado una opción", "Por favor, selecciona una opción de la lista desplegable");
            }
        }
    }

    @FXML
    private void actualizarTabla()
    {
        // Limpiamos la tabla
        vTable.getChildren().clear();

        ObservableList<Object> data = FXCollections.observableArrayList();
        String opcion = cmbEntidades.getValue();

        switch (opcion)
        {
            case "Huesped" ->
            {
                // Crear tabla
                TableView<Huesped> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Huesped, Number> id_huesped = new TableColumn<>("ID");
                TableColumn<Huesped, String> nombre = new TableColumn<>("Nombre(s)");
                TableColumn<Huesped, String> apellido = new TableColumn<>("Apellido(s)");
                TableColumn<Huesped, String> telefono = new TableColumn<>("Teléfono");
                TableColumn<Huesped, String> correo = new TableColumn<>("Correo Electrónico");
                TableColumn<Huesped, Number> idf_domicilio = new TableColumn<>("ID Domicilio");

                // Configurar celdas de las columnas
                id_huesped.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_huesped()));
                nombre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNombre()));
                apellido.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getApellido()));
                telefono.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelefono()));
                correo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCorreo()));
                idf_domicilio.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_domicilio()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_huesped);
                tblVista.getColumns().add(nombre);
                tblVista.getColumns().add(apellido);
                tblVista.getColumns().add(telefono);
                tblVista.getColumns().add(correo);
                tblVista.getColumns().add(idf_domicilio);

                // Obtener datos de la base de datos mediante el CRUD
                HuespedCRUD crud = new HuespedCRUD();
                List<Huesped> huespedes = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Huesped> datos = FXCollections.observableArrayList();
                datos.addAll(huespedes);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Hotel" ->
            {
                // Crear tabla
                TableView<Hotel> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Hotel, Number> id_hotel = new TableColumn<>("ID");
                TableColumn<Hotel, String> nombre = new TableColumn<>("Nombre");
                TableColumn<Hotel, String> telefono = new TableColumn<>("Teléfono");
                TableColumn<Hotel, Number> idf_domicilio = new TableColumn<>("ID Domicilio");

                // Configurar celdas de las columnas
                id_hotel.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_hotel()));
                nombre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNombre()));
                telefono.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelefono()));
                idf_domicilio.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_domicilio()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_hotel);
                tblVista.getColumns().add(nombre);
                tblVista.getColumns().add(telefono);
                tblVista.getColumns().add(idf_domicilio);

                // Obtener datos de la base de datos mediante el CRUD
                HotelCRUD crud = new HotelCRUD();
                List<Hotel> hoteles = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Hotel> datos = FXCollections.observableArrayList();
                datos.addAll(hoteles);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Habitacion" ->
            {
                // Crear tabla
                TableView<Habitacion> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Habitacion, Number> id_habitacion = new TableColumn<>("ID");
                TableColumn<Habitacion, Number> numero = new TableColumn<>("Número");
                TableColumn<Habitacion, Number> idf_tipo = new TableColumn<>("ID Tipo");
                TableColumn<Habitacion, Number> idf_hotel = new TableColumn<>("ID Hotel");

                // Configurar celdas de las columnas
                id_habitacion.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_habitacion()));
                numero.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNumero()));
                idf_tipo.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_tipo_habitacion()));
                idf_hotel.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_hotel()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_habitacion);
                tblVista.getColumns().add(numero);
                tblVista.getColumns().add(idf_tipo);
                tblVista.getColumns().add(idf_hotel);

                // Obtener datos de la base de datos mediante el CRUD
                HabitacionCRUD crud = new HabitacionCRUD();
                List<Habitacion> habitaciones = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Habitacion> datos = FXCollections.observableArrayList();
                datos.addAll(habitaciones);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Tipo de Habitación" ->
            {
                // Crear tabla
                TableView<TipoHabitacion> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<TipoHabitacion, Number> id_tipo_habitacion = new TableColumn<>("ID");
                TableColumn<TipoHabitacion, String> nombre = new TableColumn<>("Nombre");
                TableColumn<TipoHabitacion, Number> capacidad = new TableColumn<>("Capacidad");
                TableColumn<TipoHabitacion, Number> precio_base = new TableColumn<>("Precio Base");

                // Configurar celdas de las columnas
                id_tipo_habitacion.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_tipo_habitacion()));
                nombre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNombre()));
                capacidad.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getCapacidad()));
                precio_base.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPrecio_base()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_tipo_habitacion);
                tblVista.getColumns().add(nombre);
                tblVista.getColumns().add(capacidad);
                tblVista.getColumns().add(precio_base);

                // Obtener datos de la base de datos mediante el CRUD
                TipoHabitacionCRUD crud = new TipoHabitacionCRUD();
                List<TipoHabitacion> tipos = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<TipoHabitacion> datos = FXCollections.observableArrayList();
                datos.addAll(tipos);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Domicilio" ->
            {
                // Crear tabla
                TableView<Domicilio> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Domicilio, Number> id_domicilio = new TableColumn<>("ID");
                TableColumn<Domicilio, String> calle = new TableColumn<>("Calle");
                TableColumn<Domicilio, Number> numero = new TableColumn<>("Número");
                TableColumn<Domicilio, String> colonia = new TableColumn<>("Colonia");
                TableColumn<Domicilio, String> codigo_postal = new TableColumn<>("C.P.");
                TableColumn<Domicilio, Number> idf_ciudad = new TableColumn<>("ID Ciudad");

                // Configurar celdas de las columnas
                id_domicilio.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_Domicilio()));
                calle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCalle()));
                numero.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNumero()));
                colonia.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getColonia()));
                codigo_postal.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCodigo_postal()));
                idf_ciudad.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_Ciudad()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_domicilio);
                tblVista.getColumns().add(calle);
                tblVista.getColumns().add(numero);
                tblVista.getColumns().add(colonia);
                tblVista.getColumns().add(codigo_postal);
                tblVista.getColumns().add(idf_ciudad);

                // Obtener datos de la base de datos mediante el CRUD
                DomicilioCRUD crud = new DomicilioCRUD();
                List<Domicilio> domicilios = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Domicilio> datos = FXCollections.observableArrayList();
                datos.addAll(domicilios);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Ciudad" ->
            {
                // Crear tabla
                TableView<Ciudad> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Ciudad, Number> id_ciudad = new TableColumn<>("ID");
                TableColumn<Ciudad, String> nombre = new TableColumn<>("Nombre");

                // Configurar celdas de las columnas
                id_ciudad.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_ciudad()));
                nombre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNombre()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_ciudad);
                tblVista.getColumns().add(nombre);

                // Obtener datos de la base de datos mediante el CRUD
                CiudadCRUD crud = new CiudadCRUD();
                List<bd.java.classes.Ciudad> ciudades = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Ciudad> datos = FXCollections.observableArrayList();
                datos.addAll(ciudades);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Reserva" ->
            {
                // Crear tabla
                TableView<Reserva> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Reserva, Number> id_reserva = new TableColumn<>("ID");
                TableColumn<Reserva, Date> fecha_llegada = new TableColumn<>("Fecha Llegada");
                TableColumn<Reserva, Date> fecha_salida = new TableColumn<>("Fecha Salida");
                TableColumn<Reserva, Number> num_huespedes = new TableColumn<>("Núm. Huéspedes");
                TableColumn<Reserva, Number> idf_hotel = new TableColumn<>("ID Hotel");
                TableColumn<Reserva, Number> idf_huesped = new TableColumn<>("ID Huesped");

                // Configurar celdas de las columnas
                id_reserva.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_reserva()));
                fecha_llegada.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFecha_llegada()));
                fecha_salida.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFecha_salida()));
                num_huespedes.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNum_huespedes()));
                idf_hotel.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_hotel()));
                idf_huesped.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_huesped()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_reserva);
                tblVista.getColumns().add(fecha_llegada);
                tblVista.getColumns().add(fecha_salida);
                tblVista.getColumns().add(num_huespedes);
                tblVista.getColumns().add(idf_hotel);
                tblVista.getColumns().add(idf_huesped);

                // Obtener datos de la base de datos mediante el CRUD
                ReservaCRUD crud = new ReservaCRUD();
                List<Reserva> reservas = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Reserva> datos = FXCollections.observableArrayList();
                datos.addAll(reservas);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Pago" ->
            {
                // Crear tabla
                TableView<Pago> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<Pago, Number> id_pago = new TableColumn<>("ID");
                TableColumn<Pago, Number> cantidad = new TableColumn<>("Cantidad");
                TableColumn<Pago, String> metodo = new TableColumn<>("Método de pago");
                TableColumn<Pago, Date> fecha = new TableColumn<>("Fecha");
                TableColumn<Pago, Number> idf_reserva = new TableColumn<>("ID Reserva");

                // Configurar celdas de las columnas
                id_pago.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId_pago()));
                cantidad.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getCantidad()));
                metodo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMetodo_pago()));
                fecha.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFecha()));
                idf_reserva.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_reserva()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_pago);
                tblVista.getColumns().add(cantidad);
                tblVista.getColumns().add(metodo);
                tblVista.getColumns().add(fecha);
                tblVista.getColumns().add(idf_reserva);

                // Obtener datos de la base de datos mediante el CRUD
                PagoCRUD crud = new PagoCRUD();
                List<Pago> pagos = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<Pago> datos = FXCollections.observableArrayList();
                datos.addAll(pagos);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
            }

            case "Relacíon H-R" ->
            {
                // Crear tabla
                TableView<HabRes> tblVista = new TableView<>();
                vTable.getChildren().add(tblVista);

                // Crear columnas
                TableColumn<HabRes, Number> id_habitacion = new TableColumn<>("ID Habitación");
                TableColumn<HabRes, Number> id_reserva = new TableColumn<>("ID Reserva");

                // Configurar celdas de las columnas
                id_habitacion.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_habitacion()));
                id_reserva.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdf_reserva()));

                // Agregar columnas a la tabla
                tblVista.getColumns().add(id_habitacion);
                tblVista.getColumns().add(id_reserva);

                // Obtener datos de la base de datos mediante el CRUD
                HabResCRUD crud = new HabResCRUD();
                List<HabRes> habRes = crud.select("");

                // Crear lista observable para almacenar los datos
                ObservableList<HabRes> datos = FXCollections.observableArrayList();
                datos.addAll(habRes);

                // Asignar datos a la tabla
                tblVista.setItems(datos);
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
        {});
        stage.close();
    }

    private void initComponents()
    {
        // Inicializar combobox
        ObservableList<String> opciones = FXCollections.observableArrayList("Huesped", "Hotel", "Habitacion", "Tipo de Habitación", "Domicilio", "Ciudad", "Reserva", "Pago", "Relacíon H-R");
        cmbEntidades.setItems(opciones);

        ObservableList<String> editables = FXCollections.observableArrayList("Huéspedes", "Hoteles", "Reservas");
        cmbEdicion.setItems(editables);
    }
}
