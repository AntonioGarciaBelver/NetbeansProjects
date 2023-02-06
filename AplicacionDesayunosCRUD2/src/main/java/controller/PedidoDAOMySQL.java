
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Pedido;

/**
 *
 * @author loren
 */
public class PedidoDAOMySQL implements PedidoDAO{

    private static final String GET_QUERY = "select * from pedido where idpedido=?";
    private static final String INSERT_QUERY = """
                INSERT INTO `pedido` 
                        (`idpedido`, `fecha`, `cliente`, `producto`, `estado`) 
                VALUES  (NULL, ?, ?, ?,?);""";
    private static final String GETALL_QUERY = "SELECT * FROM pedido ORDER BY idpedido";
    private static final String DELETE_ID_QUERY = "DELETE FROM pedido WHERE idpedido = ?";
    private static final String UPDATE_QUERY = """
                UPDATE `pedido` SET 
                    `fecha` = ?,
                    `cliente` = ?,
                    `producto` = ?,
                    `estado` = ?                           
                WHERE `pedido`.`idpedido` = ?""";
    private static final String GETALL_BY_NOMBRE_QUERY = "SELECT * FROM pedido WHERE cliente = ?";
    private static final String GET_ESTADO_BY_ID = "SELECT estado from pedido where idpedido = ?";
    private static final String GET_PEDIDOS_DIA= "SELECT * FROM pedido WHERE fecha like ? ";
    private static final String GET_PEDIDOS_PENDIENTES= "SELECT * FROM pedido WHERE estado = 0";
    private static final String GET_PEDIDOS_WITH_NOMBRE= "SELECT * FROM pedido WHERE cliente = ? ";
    private static final String GANANCIAS_MES= """
                                               SELECT sum(precio) 
                                               FROM desayuno.carta, desayuno.pedido 
                                               WHERE date_sub(now(), interval 30 day) 
                                               AND pedido.producto = carta.idproducto
                                               """;
    private static final String PEDIDO_POPULAR= """
                                                SELECT carta.nombre, count(pedido.producto)
                                                FROM desayuno.carta, desayuno.pedido
                                                WHERE carta.idproducto = pedido.producto
                                                group by producto
                                                order by count(producto) desc limit 1
                                               """;
    private static final String MEJOR_CLIENTE= """
                                               SELECT pedido.cliente, count(pedido.cliente)
                                               FROM desayuno.pedido
                                               group by cliente
                                               order by count(cliente) desc limit 1
                                               """;
    private static final String TOTAL_CLIENTES="SELECT COUNT(cliente) FROM pedido";
    
    private static Connection conexion = Conexion.getConexion();

    public PedidoDAOMySQL(){
    }

    @Override
    public Pedido get(Integer id) {
        try(var pst = conexion.prepareStatement(GET_QUERY)){
            
            pst.setInt(1, id);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                var pedido = new Pedido();
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha( resultado.getDate("fecha") );
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                return pedido;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void add(Pedido p) {
        try( var pst = conexion.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)){
            
            java.util.Date fechaRecibida = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(fechaRecibida.getTime());
                    
            pst.setDate(1, sqlDate);
            pst.setString(2, p.getCliente() );
            pst.setInt(3, p.getProducto() );
            pst.setInt(4, p.getEstado());
            
            if (pst.executeUpdate() > 0){

                var keys = pst.getGeneratedKeys();
                keys.next();
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void update(Pedido p) {
        
        try(var pst = conexion.prepareStatement(UPDATE_QUERY)){
            
           java.util.Date date = p.getFecha();
           
           java.sql.Date dateSQL = new java.sql.Date(date.getTime());
                        
            pst.setDate(1, dateSQL);
            pst.setString(2, p.getCliente());
            pst.setInt(3, p.getProducto());
            pst.setInt(4, p.getEstado());
            pst.setInt(5, p.getIdpedido());
            if( pst.executeUpdate()== 0){
                Logger.getLogger(PedidoDAOMySQL.class.getName()).severe("Pedido no existe.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Pedido> getAll() {
        var salida = new ArrayList<Pedido>();
        
        try( var pst = conexion.prepareStatement(GETALL_QUERY)){
            
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var pedido = new Pedido();
                java.sql.Date fechaRecibida = resultado.getDate("fecha");
                java.util.Date utilDate = new java.util.Date(fechaRecibida.getTime());
                        
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha(utilDate);
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                salida.add(pedido);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public void delete(Pedido p) {
        deleteById(p.getIdpedido());
    }

    @Override
    public void deleteById(Integer id) {
        try( var pst = conexion.prepareStatement(DELETE_ID_QUERY)){
        
            pst.setInt(1, id);
            
            if(pst.executeUpdate()==0){
                Logger.getLogger(PedidoDAOMySQL.class.getName()).warning("Pedido no existe");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public ArrayList<Pedido> getAllByNombre(String nombre) {
        var salida = new ArrayList<Pedido>();
        
        try( var pst = conexion.prepareStatement(GETALL_BY_NOMBRE_QUERY)){
            
            pst.setString(1, nombre);
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var pedido = new Pedido();
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha( resultado.getDate("fecha") );
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                salida.add(pedido);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public Integer getEstado(Integer id) {
        try(var pst = conexion.prepareStatement(GET_ESTADO_BY_ID)){
            
            pst.setInt(1, id);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                Integer estado = resultado.getInt("estado");
                
                return estado;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Pedido> getPedidosDia() {
        var salida = new ArrayList<Pedido>();
        
        try( var pst = conexion.prepareStatement(GET_PEDIDOS_DIA)){
            
            java.util.Date fechaDia = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(fechaDia.getTime());
            pst.setString(1, "%"+sqlDate.toString()+"%");
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var pedido = new Pedido();
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha( resultado.getDate("fecha") );
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                salida.add(pedido);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public ArrayList<Pedido> getPedidosWithNombre(String nombre) {
        
        var salida = new ArrayList<Pedido>();
        
        try( var pst = conexion.prepareStatement(GET_PEDIDOS_WITH_NOMBRE)){
            
            pst.setString(1, nombre);
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var pedido = new Pedido();
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha( resultado.getDate("fecha") );
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                salida.add(pedido);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public ArrayList<Pedido> getPedidosPendientes() {
        var salida = new ArrayList<Pedido>();
        
        try( var pst = conexion.prepareStatement(GET_PEDIDOS_PENDIENTES)){
            
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var pedido = new Pedido();
                pedido.setIdpedido(resultado.getInt("idpedido") );
                pedido.setFecha( resultado.getDate("fecha") );
                pedido.setCliente(resultado.getString("cliente") );
                pedido.setProducto( resultado.getInt("producto") );
                pedido.setEstado(resultado.getInt("estado"));
                salida.add(pedido);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public Integer gananciasMes() {
        try(var pst = conexion.prepareStatement(GANANCIAS_MES)){
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                Integer estado = resultado.getInt("sum(precio)");
                
                return estado;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String mejorCliente() {
        try(var pst = conexion.prepareStatement(MEJOR_CLIENTE)){
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                String cliente = resultado.getString("cliente");
                
                return cliente;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @Override
    public Integer totalClientes() {
        try(var pst = conexion.prepareStatement(TOTAL_CLIENTES)){
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                Integer clientes = resultado.getInt("count(cliente)");
                
                return clientes;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String productoPopular() {
    try(var pst = conexion.prepareStatement(PEDIDO_POPULAR)){
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                String producto = resultado.getString("nombre");
                
                return producto;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    

    
}
