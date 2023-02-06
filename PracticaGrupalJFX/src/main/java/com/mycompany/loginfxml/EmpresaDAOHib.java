
package com.mycompany.loginfxml;

import java.util.List;
import models.Empresa;
import org.hibernate.Transaction;

/**
 *
 * @author anton
 */
public class EmpresaDAOHib implements EmpresaDAO {

    @Override
    public void save(Empresa e) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(e);
            t.commit();
        }
    }

    @Override
    public void update(Empresa e) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(e);
            t.commit();
        }
    }

    @Override
    public Empresa get(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Empresa.class, id);
        }
    }

    @Override
    public void delete(Empresa e) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(e);
            t.commit();
        }
    }

    @Override
    public List<Empresa> getAll() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("FROM Empresa");
            return q.list();
        }
    }
    
}
