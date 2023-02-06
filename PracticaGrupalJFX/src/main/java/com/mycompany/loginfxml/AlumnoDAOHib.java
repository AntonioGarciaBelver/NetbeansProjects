package com.mycompany.loginfxml;

import java.util.List;
import models.Alumno;
import models.Profesor;
import org.hibernate.Transaction;

/**
 *
 * @author anton
 */
public class AlumnoDAOHib implements AlumnoDAO {

    @Override
    public void save(Alumno a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(a);
            t.commit();
        }
    }

    @Override
    public void update(Alumno a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(a);
            t.commit();
        }
    }

    @Override
    public Alumno get(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Alumno.class, id);
        }
    }

    @Override
    public void delete(Alumno a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(a);
            t.commit();
        }
    }

    @Override
    public List<Alumno> getAll() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("FROM Alumno");
            return q.list();
        }
    }

    @Override
    public List<Alumno> getAlumnosDatos(Profesor p) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("FROM Alumno WHERE tutor_id =: param");
            q.setParameter("param", p.getId());

            return q.list();
        }
    }

    @Override
    public void crearContraseña(Alumno a, String pwd) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("UPDATE Alumno "
                    + "SET password =:pwd ,login=1 "
                    + "WHERE id =:param1 ");
            q.setParameter("param1", a.getId());
            q.setParameter("pwd", pwd);
            
            Transaction t = s.beginTransaction();
            q.executeUpdate();
            t.commit();
        }
    }
    
}
