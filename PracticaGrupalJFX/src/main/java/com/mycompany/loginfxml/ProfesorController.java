package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import models.Profesor;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class ProfesorController implements Initializable {
    
    ProfesorDAOHib gestorProfesores = new ProfesorDAOHib();
    AlumnoDAOHib gestorAlumnos = new AlumnoDAOHib();
    Profesor profesorActual = SessionData.getProfesor();
    Alumno alumnoActual = null;
    
    @FXML
    private MenuItem mCerrar;
    @FXML
    private Button btnVolver;
    @FXML
    private Label info;
    @FXML
    private TableView<Alumno> tabla;
    @FXML
    private TableColumn<Alumno, String> cNombre;
    @FXML
    private TableColumn<Alumno, String> cApellidos;
    @FXML
    private TableColumn<Alumno, String> cDNI;
    @FXML
    private TableColumn<Alumno, Date> cFechaNacimiento;
    @FXML
    private TableColumn<Alumno, String> cEmail;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnEmpresas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        cDNI.setCellValueFactory(new PropertyValueFactory("dni"));
        cFechaNacimiento.setCellValueFactory(new PropertyValueFactory("fecha_nac"));
        cEmail.setCellValueFactory(new PropertyValueFactory("email"));

        actualizarTabla();
    }

    private void actualizarTabla() {

        List<Alumno> alumnos = new ArrayList();
        alumnos = gestorAlumnos.getAlumnosDatos(profesorActual);
        ObservableList<Alumno> alumnos1 = FXCollections.observableArrayList();
        
        alumnos1.addAll(alumnos);

        tabla.getItems().clear();
        tabla.getItems().addAll(alumnos1);
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }

    @FXML
    private void abrirAlumno(ActionEvent event) throws IOException {
        App.setRoot("datosAlumno");
    }

    @FXML
    private void mostrarAlumno(MouseEvent event) {
        alumnoActual = tabla.getSelectionModel().getSelectedItem();
        SessionData.setAlumno(alumnoActual);
    }

    @FXML
    private void abrirEmpresas(ActionEvent event) throws IOException {
        App.setRoot("empresas");
    }

}
