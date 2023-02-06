
package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Alumno;
import models.Profesor;
import org.hibernate.Session;
import org.hibernate.query.Query;
/**
 * FXML Controller class
 *
 * @author anton
 */
public class LoginProfesorController implements Initializable {


    @FXML
    private Label info;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox chkSesion;
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
    private void aceptar(ActionEvent event) {
        
        try ( Session s = HibernateUtil.getSessionFactory().openSession()) {

            Query q = s.createQuery("from Profesor where email=:param and password=:pwd");
            q.setParameter("param", txtUser.getText());
            q.setParameter("pwd", txtPassword.getText());

            ArrayList<Profesor> resultado = (ArrayList<Profesor>) q.list();
      
            if (resultado.size() > 0) {
                info.setText("Usuario existe");
                info.setStyle("-fx-background-color:green; -fx-text-fill:white;");

                SessionData.setProfesor(resultado.get(0));

                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(LoginAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                info.setText("PROFESOR NO EXISTE");
                info.setStyle("-fx-background-color:red; -fx-text-fill:white;");
            }
        }
    }

}
