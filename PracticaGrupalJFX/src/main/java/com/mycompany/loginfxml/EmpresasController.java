
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import models.Empresa;
import models.Profesor;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class EmpresasController implements Initializable {

    EmpresaDAOHib gestorEmpresas = new EmpresaDAOHib();
    Empresa empresaActual;
    @FXML
    private MenuItem mCerrar;
    @FXML
    private TextField txtObservaciones;
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
    private TableView<Empresa> tabla;
    @FXML
    private TableColumn<Empresa, String> cNombre;
    @FXML
    private TableColumn<Empresa, String> cObservcaiones;
    @FXML
    private TableColumn<Empresa, String> cTelefono;
    @FXML
    private TableColumn<Empresa, String> cEmail;
    @FXML
    private TableColumn<Empresa, String> cResponsable;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<Profesor> comboProfesores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        cEmail.setCellValueFactory(new PropertyValueFactory("email"));
        cResponsable.setCellValueFactory(new PropertyValueFactory("responsable"));
        cObservcaiones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        actualizarTabla();
        llenarCombo();
    }

    private void actualizarTabla() {

        List<Empresa> empresas = new ArrayList();
        empresas = gestorEmpresas.getAll();

        ObservableList<Empresa> empresas1 = FXCollections.observableArrayList();
        empresas1.addAll(empresas);

        tabla.getItems().clear();
        tabla.getItems().addAll(empresas1);
    }

    public void llenarCombo() {
        ObservableList<Profesor> profesores = FXCollections.observableArrayList();
        for (Profesor profesor : traerProfesores()) {
            profesores.add(profesor);
        }
        comboProfesores.setItems(profesores);
        comboProfesores.setConverter(new StringConverter<Profesor>() {
            @Override
            public String toString(Profesor object) {
                if (object != null) {
                    return object.getNombre();
                } else {
                    return "";
                }
            }

            @Override
            public Profesor fromString(String param) {
                try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
                    Query q = s.createSQLQuery("from Profesor where nombre=:nombre");
                    q.setParameter("nombre", param);
                    return (Profesor) q.list().get(0);
                }
            }
        });
    }

    public ArrayList<Profesor> traerProfesores() {
        ArrayList<Profesor> profesores = new ArrayList<>();
        try ( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("from Profesor");
            profesores = (ArrayList<Profesor>) q.list();
        }
        return profesores;
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void añadirEmpresa(ActionEvent event) {
        empresaActual.setNombre(txtNombre.getText());
        empresaActual.setTelefono(txtTelefono.getText());
        empresaActual.setEmail(txtEmail.getText());
        empresaActual.setResponsable(comboProfesores.getValue().getNombre());
        empresaActual.setObservaciones(txtObservaciones.getText());
        
        gestorEmpresas.save(empresaActual);

        actualizarTabla();

        info.setText("Empresa "+empresaActual.getNombre() +" añadida con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
    }

    @FXML
    private void actualizarEmpresa(ActionEvent event) {
        empresaActual.setNombre(txtNombre.getText());
        empresaActual.setTelefono(txtTelefono.getText());
        empresaActual.setEmail(txtEmail.getText());
        empresaActual.setResponsable(comboProfesores.getValue().getNombre());
        empresaActual.setObservaciones(txtObservaciones.getText());
        
        gestorEmpresas.update(empresaActual);

        actualizarTabla();

        info.setText("Empresa "+empresaActual.getNombre()+"  con éxito!");
        info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
    }

    @FXML
    private void borrarEmpresa(ActionEvent event) {
        Empresa empresa = tabla.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar Alumno");
        alert.setHeaderText("¿Estas seguro de borrar la empresa "+empresaActual.getNombre()+"?");
        alert.setContentText("No hay vuelta atrás. Avisado estás!!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            gestorEmpresas.delete(empresa);
            info.setText("Empresa " + empresaActual.getNombre() + " borrada con éxito!");
            info.setStyle("-fx-background-color:#00FF00; -fx-text-fill:white; -fx-font-weight: bold");
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
        actualizarTabla();
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("datosAlumno");
    }

    @FXML
    private void mostrarEmpresas(MouseEvent event) {
        Empresa empresa = tabla.getSelectionModel().getSelectedItem();

        System.out.println(tabla.getSelectionModel().getSelectedIndex());

        if (empresa != null) {

            txtNombre.setText(empresa.getNombre());
            txtEmail.setText(empresa.getEmail());
            txtTelefono.setText(empresa.getTelefono());
            txtObservaciones.setText(empresa.getObservaciones());

            empresaActual = empresa;

        }
    }

}
