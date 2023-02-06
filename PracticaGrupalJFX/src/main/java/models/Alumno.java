package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author anton
 */
@Entity
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String nombre;
    private String apellidos;
    private String password;
    private String dni;
    private LocalDate fecha_nac;
    private String email;
    private String telefono;
    private Integer horas_dual;
    private Integer horas_fct;
    private String observaciones;
    private Boolean login;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Profesor profesor;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private List<Actividad> actividades;

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getHoras_dual() {
        return horas_dual;
    }

    public void setHoras_dual(Integer horas_dual) {
        this.horas_dual = horas_dual;
    }

    public Integer getHoras_fct() {
        return horas_fct;
    }

    public void setHoras_fct(Integer horas_fct) {
        this.horas_fct = horas_fct;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Alumno() {
    }

    @Override
    public String toString() {
        return "Alumno{" + "id=" + id + ", nombre=" + nombre + ", apellidos="
                + apellidos + ", password=" + password + ", dni=" + dni
                + ", fecha_nac=" + fecha_nac + ", email=" + email + ", telefono="
                + telefono + ", horas_dual=" + horas_dual + ", horas_fct="
                + horas_fct + ", observaciones=" + observaciones + ", empresa="
                + empresa.getNombre() + ", profesor=" + profesor + ", actividades=" + actividades + '}';
    }

}
