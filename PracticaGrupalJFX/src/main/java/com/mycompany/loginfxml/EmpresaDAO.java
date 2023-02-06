
package com.mycompany.loginfxml;

import java.util.List;
import models.Empresa;

/**
 *
 * @author anton
 */
public interface EmpresaDAO {
    
    void save(Empresa e);
    void update(Empresa e);
    Empresa get(Integer id);
    void delete(Empresa e);
    List<Empresa> getAll();
}
