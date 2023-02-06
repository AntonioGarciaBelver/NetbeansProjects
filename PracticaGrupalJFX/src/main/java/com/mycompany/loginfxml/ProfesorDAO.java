
package com.mycompany.loginfxml;

import java.util.List;
import models.Alumno;
import models.Profesor;

/**
 *
 * @author anton
 */
public interface ProfesorDAO {
    
    void save(Profesor p);
    void update(Profesor p);
    Profesor get(Integer id);
    void delete(Profesor p);
    List<Profesor> getAll();
}
