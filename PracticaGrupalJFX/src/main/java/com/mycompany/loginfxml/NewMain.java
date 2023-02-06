
package com.mycompany.loginfxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Alumno;
import models.Profesor;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author anton
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AlumnoDAOHib gestorAlumno = new AlumnoDAOHib();
        ActividadDAOHib gestorActividades = new ActividadDAOHib();
        ProfesorDAOHib gestorProfesor = new ProfesorDAOHib();
        
        System.out.println(gestorAlumno.getAll());

        System.out.println("---------------------------------");
        
        //System.out.println(SessionData.getAlumno());
                
        Alumno a = gestorAlumno.get(39);
        Profesor p = gestorProfesor.get(3);
        a.setProfesor(p);
        System.out.println(a);
        System.out.println(p);
        System.out.println(p.getId());
        
        System.out.println("*************************************");
        
        System.out.println(gestorAlumno.getAlumnosDatos(p));
        
        System.out.println("*************************************");
        
        System.out.println(gestorActividades.getHorasDia(a, "FCT"));
   
    }
    
}
