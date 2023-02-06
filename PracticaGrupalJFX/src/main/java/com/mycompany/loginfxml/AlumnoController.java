package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class AlumnoController implements Initializable {

    ActividadDAOHib gestorActividades = new ActividadDAOHib();
    Alumno alumnoActual = SessionData.getAlumno();
    Profesor tutorActual = SessionData.getProfesor();
    Actividad actividadActual;
    @FXML
    private MenuItem mCerrar;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtObservaciones;
    @FXML
    private Spinner<Double> spinner;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnVolver;
    @FXML
    private Label info;
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
    private ComboBox<String> comboPractica;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        cTipoPractica.setCellValueFactory(new PropertyValueFactory("tipo_practica"));
        cHorasdia.setCellValueFactory(new PropertyValueFactory("horas_dia"));
        cHorasdia.setCellValueFactory((var fila) -> {
            Actividad actividad = fila.getValue();
            return new ReadOnlyObjectWrapper(actividad.getHoras_dia() + " horas");
        });

        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cObservcaiones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 6.0, 1.0, 0.25);
        spinner.setValueFactory(valueFactory);

        actualizarLabels();
        llenarCombo();
        actualizarTabla();
    }

    private void actualizarTabla() {

        List<Actividad> actvidades1 = new ArrayList();
        actvidades1 = gestorActividades.getAllById(alumnoActual);

        ObservableList<Actividad> actividades = FXCollections.observableArrayList();
        actividades.addAll(actvidades1);

        tabla.getItems().clear();
        tabla.getItems().addAll(actividades);

    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void añadirActividad(ActionEvent event) {

        actividadActual.setFecha(txtFecha.getText());
        actividadActual.setAlumno(SessionData.getAlumno());
        actividadActual.setTipo_practica(comboPractica.getValue());
        actividadActual.setHoras_dia(spinner.getValue());
        actividadActual.setNombre(txtNombre.getText());
        actividadActual.setObservaciones(txtObservaciones.getText());

        actividadActual.setFecha(txtFecha.getText());
        actividadActual.setAlumno(SessionData.getAlumno());
        actividadActual.setTipo_practica(comboPractica.getValue());
        actividadActual.setHoras_dia(spinner.getValue());
        actividadActual.setNombre(txtNombre.getText());
        actividadActual.setObservaciones(txtObservaciones.getText());

        gestorActividades.save(actividadActual);

        actualizarTabla();

        info.setText("Actividad añadida a  " + SessionData.getAlumno().getNombre() + "  con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
    }

    @FXML
    private void actualizarActividad(ActionEvent event) {
        actividadActual.setFecha(txtFecha.getText());
        actividadActual.setAlumno(SessionData.getAlumno());
        actividadActual.setTipo_practica(comboPractica.getValue());
        actividadActual.setHoras_dia(spinner.getValue());
        actividadActual.setNombre(txtNombre.getText());
        actividadActual.setObservaciones(txtObservaciones.getText());

        gestorActividades.update(actividadActual);

        actualizarTabla();

        info.setText("Actividad actualizada a  " + SessionData.getAlumno().getNombre() + "  con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");

    }

    public void llenarCombo() {
        ObservableList<String> practicas = FXCollections.observableArrayList();
        String[] disponible = {"FCT", "DUAL"};
        practicas.addAll(disponible);
        comboPractica.setItems(practicas);
    }

    @FXML
    private void borrarActividad(ActionEvent event) {

        Actividad actividad = tabla.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar Alumno");
        alert.setHeaderText("¿Estas seguro de borrar el alumno" + alumnoActual.getNombre() + "?");
        alert.setContentText("No hay vuelta atrás. Avisado estás!!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            gestorActividades.delete(actividad);
            info.setText("Pedido borrado a " + actividadActual.getNombre() + "  con éxito!");
            info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }

        actualizarTabla();

    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }

    @FXML
    private void mostrarActividad(MouseEvent event) {
        Actividad actividad = tabla.getSelectionModel().getSelectedItem();

        System.out.println(tabla.getSelectionModel().getSelectedIndex());

        if (actividad != null) {

            txtFecha.setText("" + actividad.getFecha());
            comboPractica.setValue(actividad.getTipo_practica());
            txtNombre.setText(actividad.getNombre());
            txtObservaciones.setText(actividad.getObservaciones());
            SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 6.0, actividad.getHoras_dia(), 0.25);
            spinner.setValueFactory(valueFactory);

            actividadActual = actividad;

        }
    }

    private void actualizarLabels() {

        lbl1.setText(tutorActual.getNombre());
        lbl2.setText(tutorActual.getApellidos());
        lbl3.setText(tutorActual.getEmail());

        lbl4.setText(alumnoActual.getEmpresa().getNombre());
        lbl5.setText(alumnoActual.getEmpresa().getTelefono());
        lbl6.setText(alumnoActual.getEmpresa().getEmail());
        lbl7.setText(alumnoActual.getEmpresa().getResponsable());
        lbl8.setText(alumnoActual.getEmpresa().getObservaciones());

    }

}
