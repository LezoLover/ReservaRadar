package bd.java.controllers;

import bd.java.MainApp;
import bd.java.classes.Huesped;
import bd.java.sql.HuespedCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LoginController
{
    @FXML
    private VBox ventana;
    @FXML
    private TextField txtCorreo;

    @FXML
    private void ingresarClick()
    {
        if (validarEntrada())
        {
            abrirVentana("main", "ReservaRadar");
            cerrarVentana();
        }
    }

    @FXML
    private void registrarClick()
    {
        abrirVentana("huesped", "Registro nuevo");
        cerrarVentana();
    }

    @FXML
    private void administradorClick()
    {
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Código");
        txtPass.setAlignment(Pos.CENTER);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Por favor, introduce el código:");

        alert.getDialogPane().setContent(txtPass);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            if (txtPass.getText().equals("2023"))
            {
                abrirVentana("administrador", "Herramientas de Administrador");
                cerrarVentana();
            }
            else
            {
                mostrarAlerta(Alert.AlertType.WARNING, "Código incorrecto", "Has ingresado el código incorrecto", "Vuelve a intentarlo");
            }
        }
    }

    // Métodos
    private boolean validarEntrada()
    {
        HuespedCRUD huespedCRUD = new HuespedCRUD();
        List<Huesped> huespedes = huespedCRUD.select("WHERE correo = '" + txtCorreo.getText() + "'");

        if (huespedes.isEmpty())
        {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo no registrado", "El correo ingresado no está registrado", "Por favor, regístrate");
            return false;
        }
        else
        {
            Huesped huesped = huespedes.get(0);
            Huesped.id_huesped_static = huesped.getId_huesped();
            return true;
        }
    }

    private void cerrarVentana()
    {
        Stage stage = (Stage) ventana.getScene().getWindow();
        stage.setOnCloseRequest(event -> {});
        stage.close();
    }

    public static void abrirVentana(String url, String title)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource(url + "-view.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e)
        {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar la ventana", e.toString());
        }
    }

    public static void mostrarAlerta(Alert.AlertType alertType, String title, String header, String e)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(e);
        alert.showAndWait();
    }
}
