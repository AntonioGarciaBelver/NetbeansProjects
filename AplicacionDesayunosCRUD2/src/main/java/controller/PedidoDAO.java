package controller;

import java.util.ArrayList;
import models.Pedido;

/**
 *
 * @author LorenLynchMcrae
 */
public interface PedidoDAO {
    
    Pedido get(Integer id);
    
    void add(Pedido p);
    
    void update(Pedido p);
    
    ArrayList<Pedido> getAll();
    
    void delete(Pedido p);
    
    void deleteById(Integer id);
    
    ArrayList<Pedido> getAllByNombre(String nombre);
    
    Integer getEstado(Integer id);
    
    ArrayList<Pedido> getPedidosDia();
    
    ArrayList<Pedido> getPedidosPendientes();
    
    ArrayList<Pedido> getPedidosWithNombre(String nombre);
    
    Integer gananciasMes();
    
    String mejorCliente();
    
    String productoPopular();
    
    Integer totalClientes();
}
