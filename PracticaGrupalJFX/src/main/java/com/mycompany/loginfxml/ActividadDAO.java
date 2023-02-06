/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.loginfxml;

import java.util.List;
import models.Actividad;
import models.Alumno;

/**
 *
 * @author anton
 */

public interface ActividadDAO {
    void save(Actividad a);
    void update(Actividad a);
    Actividad get(Integer id);
    void delete(Actividad a);
    List<Actividad> getAll();
    List<Actividad> getAllById(Alumno a);
    Double getHorasDia(Alumno a, String tipoHora);
}
