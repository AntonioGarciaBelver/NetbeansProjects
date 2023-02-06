package com.mycompany.loginfxml;

import java.util.List;
import models.Actividad;
import models.Alumno;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author anton
 */
public class ActividadDAOHib implements ActividadDAO {

    @Override
    public void save(Actividad a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(a);
            t.commit();
        }
    }

    @Override
    public void update(Actividad a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(a);
            t.commit();
        }
    }

    @Override
    public Actividad get(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Actividad.class, id);
        }
    }

    @Override
    public void delete(Actividad a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(a);
            t.commit();
        }
    }

    @Override
    public List<Actividad> getAll() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Actividad");
            return q.list();
        }
    }

    @Override
    public List<Actividad> getAllById(Alumno a) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Actividad where alumno_id=:param");
            q.setParameter("param", a.getId());

            return q.list();
        }
    }

    @Override
    public Double getHorasDia(Alumno a, String tipoHora) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("SELECT SUM(horas_dia) "
                    + "FROM Actividad "
                    + "WHERE alumno_id =:param "
                    + "AND tipo_practica =:param1");
            q.setParameter("param", a.getId());
            q.setParameter("param1", tipoHora);
            Double resultado = (Double) q.getSingleResult();

            return resultado;
        }

    }
}
