
package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Antonio
 */
@Entity
public class Actividad implements Serializable {
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;
    private String fecha;
    private String tipo_practica;
    private Double horas_dia;
    private String nombre;
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name="alumno_id")
    private Alumno alumno;

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo_practica() {
        return tipo_practica;
    }

    public void setTipo_practica(String tipo_practica) {
        this.tipo_practica = tipo_practica;
    }

    public Double getHoras_dia() {
        return horas_dia;
    }

    public void setHoras_dia(Double horas_dia) {
        this.horas_dia = horas_dia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", fecha=" + fecha + ", tipo_practica=" + tipo_practica + ", horas_dia=" + horas_dia + ", nombre=" + nombre + ", observaciones=" + observaciones + ", alumno=" + alumno.getNombre() + '}';
    }

    public Actividad() {
    }
    
}
