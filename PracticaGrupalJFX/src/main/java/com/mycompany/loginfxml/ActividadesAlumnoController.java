package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import models.Actividad;
import models.Alumno;
import models.Profesor;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class ActividadesAlumnoController implements Initializable {

    ActividadDAOHib gestorActividades = new ActividadDAOHib();
    Actividad actividadActual = null;
    Alumno alumnoActual = SessionData.getAlumno();
    Profesor tutorActual = SessionData.getProfesor();
    DatosAlumnoController gestor = new DatosAlumnoController();

    @FXML
    private MenuItem mCerrar;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Label lbl3;
    @FXML
    private Label lbl4;
    @FXML
    private Label lbl5;
    @FXML
    private Label lbl6;
    @FXML
    private Label lbl7;
    @FXML
    private Label lbl8;
    @FXML
    private Button btnVolver;
    @FXML
    private Label lbl9;
    @FXML
    private Label lbl10;
    @FXML
    private Label lbl11;
    @FXML
    private Label lbl12;
    @FXML
    private Label lbl13;
    @FXML
    private Label lbl14;
    @FXML
    private Label lbl15;
    @FXML
    private Label lbl16;
    @FXML
    private TableView<Actividad> tabla;
    @FXML
    private TableColumn<Actividad, String> cFecha;
    @FXML
    private TableColumn<Actividad, String> cTipoPractica;
    @FXML
    private TableColumn<Actividad, String> cHorasdia;
    @FXML
    private TableColumn<Actividad, String> cNombre;
    @FXML
    private TableColumn<Actividad, String> cObservcaiones;
    @FXML
    private Label lbl17;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        cFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        cTipoPractica.setCellValueFactory(new PropertyValueFactory("tipo_practica"));
        cHorasdia.setCellValueFactory((var fila) -> {
            Actividad actividad = fila.getValue();
            return new ReadOnlyObjectWrapper(actividad.getHoras_dia() + " horas");
        });

        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cObservcaiones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        actualizarLabels();
        actualizarTabla();
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("datosAlumno");
    }

    @FXML
    private void mostrarActividad(MouseEvent event) {
    }

    private void actualizarTabla() {

        List<Actividad> actvidades1 = new ArrayList();
        actvidades1 = gestorActividades.getAllById(alumnoActual);

        ObservableList<Actividad> actividades = FXCollections.observableArrayList();
        actividades.addAll(actvidades1);

        tabla.getItems().clear();
        tabla.getItems().addAll(actividades);
    }

    private void actualizarLabels() {

        //Datos del tutor
        lbl1.setText(tutorActual.getNombre());
        lbl2.setText(tutorActual.getApellidos());
        lbl3.setText(tutorActual.getEmail());

        //Datos de la empresa
        lbl4.setText(alumnoActual.getEmpresa().getNombre());
        lbl5.setText(alumnoActual.getEmpresa().getTelefono());
        lbl6.setText(alumnoActual.getEmpresa().getEmail());
        lbl7.setText(alumnoActual.getEmpresa().getResponsable());
        lbl8.setText(alumnoActual.getEmpresa().getObservaciones());

        //Datos del Alumno
        lbl9.setText(alumnoActual.getNombre());
        lbl10.setText(alumnoActual.getApellidos());
        lbl11.setText(alumnoActual.getEmail());
        lbl12.setText(alumnoActual.getTelefono());
        lbl13.setText(alumnoActual.getEmpresa().getNombre());
        lbl14.setText(alumnoActual.getHoras_dual().toString());
        lbl15.setText(alumnoActual.getHoras_fct().toString());
        lbl16.setText(actualizarHorasRestantesDUAL());
        lbl17.setText(actualizarHorasRestantesFCT());

    }

    public String actualizarHorasRestantesDUAL() {
        Double x = Double.parseDouble(lbl14.getText());
        Double y = gestorActividades.getHorasDia(alumnoActual, "DUAL");
        if (y != null) {
            Double resultado = x - y;

            return resultado.toString();
        } else {
            return x.toString();
        }

    }

    public String actualizarHorasRestantesFCT() {
        Double x = Double.parseDouble(lbl15.getText());
        Double y = gestorActividades.getHorasDia(alumnoActual, "FCT");
        if (y != null) {
            Double resultado = x - y;

            return resultado.toString();
        } else {
            return x.toString();
        }

    }

}
