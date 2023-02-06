
package models;

import java.io.Serializable;

/**
 *
 * @author Antonio y Loren
 */
public class Carta implements Serializable {

    private Integer idproducto;
    private String nombre;
    private String tipo;
    private Integer precio;
    private String disponibilidad;

    public Carta() {
        
    }

    public Carta(String nombre, String tipo, Integer precio, String disponibilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
    }
    
    

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public String toString() {
        return "Carta{" + "idproducto=" + idproducto + ", nombre=" + nombre + ", tipo=" + tipo + ", precio=" + precio + ", disponibilidad=" + disponibilidad + '}';
    }

    
    
  
}

    