package models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Antonio y Loren
 */
public class Pedido implements Serializable {

    private Integer idpedido;
    private Date fecha;
    private String cliente;
    private Integer producto;
    private Integer estado;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Pedido() {
        
    }

    public Pedido(String cliente, Integer producto) {
        this.fecha = new Date();
        this.cliente = cliente;
        this.producto = producto;
    } 

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Pedido{" + "idpedido=" + idpedido + ", fecha=" + fecha + ", cliente=" + cliente + ", producto=" + producto + '}';
    }

    
}

