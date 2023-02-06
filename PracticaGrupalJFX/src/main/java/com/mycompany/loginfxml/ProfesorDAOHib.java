
package com.mycompany.loginfxml;

import java.util.List;
import models.Alumno;
import models.Profesor;
import org.hibernate.Transaction;

/**
 *
 * @author anton
 */
public class ProfesorDAOHib implements ProfesorDAO {

    @Override
    public void save(Profesor p) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(p);
            t.commit();
        }
    }

    @Override
    public void update(Profesor p) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(p);
            t.commit();
        }
    }

    @Override
    public Profesor get(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Profesor.class, id);
        }
    }

    @Override
    public void delete(Profesor p) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(p);
            t.commit();
        }
    }

    @Override
    public List<Profesor> getAll() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Profesor");
            return q.list();
        }
    }
    
}
