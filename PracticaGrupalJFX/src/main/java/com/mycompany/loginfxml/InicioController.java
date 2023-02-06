
package com.mycompany.loginfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class InicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void profesor(ActionEvent event) throws IOException {
        App.setRoot("loginProfesor");
    }

    @FXML
    private void alumno(ActionEvent event) throws IOException {
        App.setRoot("loginAlumno");
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }

}
