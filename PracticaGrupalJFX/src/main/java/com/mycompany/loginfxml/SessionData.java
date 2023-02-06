
package com.mycompany.loginfxml;

import models.Alumno;
import models.Profesor;

/**
 *
 * @author Antonio
 */
public class SessionData {
    
    private static Alumno alumno;
    private static Profesor profesor;

    public static Alumno getAlumno() {
        return alumno;
    }

    public static void setAlumno(Alumno usuario) {
        SessionData.alumno = usuario;
    }

    public static Profesor getProfesor() {
        return profesor;
    }

    public static void setProfesor(Profesor profesor) {
        SessionData.profesor = profesor;
    }
    
    
    
}
