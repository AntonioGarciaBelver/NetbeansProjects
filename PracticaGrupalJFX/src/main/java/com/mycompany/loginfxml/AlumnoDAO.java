
package com.mycompany.loginfxml;

import java.util.ArrayList;
import java.util.List;
import models.Alumno;
import models.Profesor;

/**
 *
 * @author anton
 */
public interface AlumnoDAO {
    
    void save(Alumno a);
    void update(Alumno a);
    Alumno get(Integer id);
    void delete(Alumno a);
    List<Alumno> getAll();
    List<Alumno> getAlumnosDatos(Profesor p);
    void crearContraseña(Alumno a, String pwd);
    
}
