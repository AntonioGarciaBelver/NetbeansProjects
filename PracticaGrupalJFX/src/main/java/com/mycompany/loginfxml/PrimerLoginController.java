package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Alumno;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class PrimerLoginController implements Initializable {

    AlumnoDAOHib gestorAlumno = new AlumnoDAOHib();
    Alumno alumnoActual = SessionData.getAlumno();
    @FXML
    private Label info;
    @FXML
    private TextField txtCrearContraseña;
    @FXML
    private PasswordField txtRepetirContraseña;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }

    @FXML
    private void aceptar(ActionEvent event) throws IOException {
        if (txtCrearContraseña.getText().equals(txtRepetirContraseña.getText())) {
            String psw = txtCrearContraseña.getText();
            gestorAlumno.crearContraseña(alumnoActual, psw);
            alumnoActual.setLogin(true);
            App.setRoot("alumno");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Dialogo de Informacion");
            alert.setHeaderText("Contraseñas no coinciden!");
            alert.setContentText("Escriba las contraseñas de nuevo");

            alert.showAndWait();

            txtCrearContraseña.setText("");
            txtRepetirContraseña.setText("");
        }

    }

}
