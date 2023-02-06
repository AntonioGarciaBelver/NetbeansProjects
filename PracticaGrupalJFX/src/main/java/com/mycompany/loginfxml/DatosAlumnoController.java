/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import models.Alumno;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class DatosAlumnoController implements Initializable {

    Alumno alumnoActual = SessionData.getAlumno();
    Empresa empresaActual = SessionData.getAlumno().getEmpresa();
    AlumnoDAOHib gestorAlumno = new AlumnoDAOHib();
    ActividadDAOHib gestorActividades = new ActividadDAOHib();
    @FXML
    private MenuItem mCerrar;
    @FXML
    private Label info;
    @FXML
    private TextField txtNombre;
    private TextField txtFechaNacimiento;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDNI;
    @FXML
    private TextField txtHorasDUAL;
    @FXML
    private TextField txtHorasFCT;
    private TextField txtHorasRestantes;
    private TextField txtObservaciones;
    @FXML
    private ComboBox<Empresa> comboEmpresa;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnVolver;
    @FXML
    private Label txtTutor;
    @FXML
    private Button btnEmpresas;
    @FXML
    private Button btnActividades;
    @FXML
    private Label lblRestanteDual;
    @FXML
    private Label lblRestanteFCT;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnVaciar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(alumnoActual.getFecha_nac());
        actualizarLabels();
        llenarCombo();
        comboEmpresa.setValue(empresaActual);
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("profesor");
    }

    private void actualizarLabels() {

        txtNombre.setText(alumnoActual.getNombre());
        txtApellido.setText(alumnoActual.getApellidos());
        txtDNI.setText(alumnoActual.getDni());
        datePicker.setValue(alumnoActual.getFecha_nac());
        txtEmail.setText(alumnoActual.getEmail());
        txtTutor.setText(alumnoActual.getProfesor().getNombre() + " " + alumnoActual.getProfesor().getApellidos());
        txtTelefono.setText(alumnoActual.getTelefono());
        txtHorasDUAL.setText(alumnoActual.getHoras_dual().toString());
        txtHorasFCT.setText(alumnoActual.getHoras_fct().toString());
        lblRestanteDual.setText(actualizarHorasRestantesDUAL());
        lblRestanteFCT.setText(actualizarHorasRestantesFCT());

    }

    public void llenarCombo() {
        ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        for (Empresa empresa : traerEmpresas()) {
            empresas.add(empresa);
        }
        comboEmpresa.setItems(empresas);
        comboEmpresa.setConverter(new StringConverter<Empresa>() {
            @Override
            public String toString(Empresa object) {
                if (object != null) {
                    return object.getNombre();
                } else {
                    return "";
                }
            }

            @Override
            public Empresa fromString(String param) {
                try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                    Query q = s.createSQLQuery("from Empresa where nombre=:nombre");
                    q.setParameter("nombre", param);
                    return (Empresa) q.list().get(0);
                }
            }
        });
    }

    public ArrayList<Empresa> traerEmpresas() {
        ArrayList<Empresa> empresas = new ArrayList<>();
        try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("from Empresa");
            empresas = (ArrayList<Empresa>) q.list();
        }
        return empresas;
    }

    @FXML
    private void añadirAlumno(ActionEvent event) {
        alumnoActual.setNombre(txtNombre.getText());
        alumnoActual.setApellidos(txtApellido.getText());
        alumnoActual.setPassword("1234");
        alumnoActual.setDni(txtDNI.getText());
        alumnoActual.setFecha_nac(datePicker.getValue());
        alumnoActual.setEmail(txtEmail.getText());
        alumnoActual.setTelefono(txtTelefono.getText());
        alumnoActual.setEmpresa(comboEmpresa.getValue());
        alumnoActual.setProfesor(alumnoActual.getProfesor());
        alumnoActual.setHoras_dual(Integer.parseInt(txtHorasDUAL.getText()));
        alumnoActual.setHoras_fct(Integer.parseInt(txtHorasFCT.getText()));
        alumnoActual.setLogin(false);
        
        if(txtObservaciones!=null){
            alumnoActual.setObservaciones(txtObservaciones.getText());
        }
        
        gestorAlumno.save(alumnoActual);

        actualizarLabels();

        info.setText("Alumno añadido " + SessionData.getAlumno().getNombre() + "  con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
    }

    @FXML
    private void actualizarAlumno(ActionEvent event) {
        alumnoActual.setNombre(txtNombre.getText());
        alumnoActual.setApellidos(txtApellido.getText());
        alumnoActual.setPassword("1234");
        alumnoActual.setDni(txtDNI.getText());
        alumnoActual.setFecha_nac(datePicker.getValue());
        alumnoActual.setEmail(txtEmail.getText());
        alumnoActual.setTelefono(txtTelefono.getText());
        alumnoActual.setEmpresa(comboEmpresa.getValue());
        alumnoActual.setProfesor(alumnoActual.getProfesor());
        alumnoActual.setHoras_dual(Integer.parseInt(txtHorasDUAL.getText()));
        alumnoActual.setHoras_fct(Integer.parseInt(txtHorasFCT.getText()));

        if(txtObservaciones!=null){
            alumnoActual.setObservaciones(txtObservaciones.getText());
        }

        gestorAlumno.update(alumnoActual);

        actualizarLabels();

        info.setText("Alumno " + SessionData.getAlumno().getNombre() + " actualizado con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");

    }

    @FXML
    private void borrarAlumno(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Borrar Alumno");
        alert.setHeaderText("¿Estas seguro de borrar el alumno"+alumnoActual.getNombre()+"?");
        alert.setContentText("No hay vuelta atrás. Avisado estás!!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            info.setText("Alumno " + alumnoActual.getNombre() + " borrado con éxito!");
            gestorAlumno.delete(alumnoActual);
            info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }

        
    }

    @FXML
    private void abrirEmpresas(ActionEvent event) throws IOException {
        App.setRoot("empresas");
    }

    @FXML
    private void abrirActividades(ActionEvent event) throws IOException {
        App.setRoot("actividadesAlumno");
    }

    public String actualizarHorasRestantesDUAL() {
        Double x = Double.parseDouble(txtHorasDUAL.getText());
        Double y = gestorActividades.getHorasDia(alumnoActual, "DUAL");
        if(y!= null){
            Double resultado = x - y;

            return resultado.toString();
        }else{
            return x.toString();
        }
        
    }
    
    public String actualizarHorasRestantesFCT() {
        Double x = Double.parseDouble(txtHorasFCT.getText());
        Double y = gestorActividades.getHorasDia(alumnoActual, "FCT");
        if(y!= null){
            Double resultado = x - y;

            return resultado.toString();
        }else{
            return x.toString();
        }
        
    }

    @FXML
    private void borrarVaciar(ActionEvent event) {
        
        txtNombre.setText("");
        txtApellido.setText("");
        txtDNI.setText("");
        datePicker.setValue(alumnoActual.getFecha_nac());
        txtEmail.setText("");
        txtTutor.setText("");
        txtTelefono.setText("");
        txtHorasDUAL.setText("");
        txtHorasFCT.setText("");
        lblRestanteDual.setText("");
        lblRestanteFCT.setText("");
        
    }

}
