package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Carta;

/**
 *
 * @author LorenLynchMcrae
 */
public class CartaDAOMySQL implements CartaDAO{

    private static final String GET_QUERY = "select * from carta where idproducto=?";
    private static final String INSERT_QUERY = """
                INSERT INTO `carta` 
                        (`idproducto`, `nombre`, `tipo`, `precio`, `disponibilidad`) 
                VALUES  (NULL, ?, ?, ?,?);""";
    private static final String GETALL_QUERY = "SELECT * FROM carta ORDER BY idproducto";
    private static final String DELETE_ID_QUERY = "DELETE FROM carta WHERE idproducto = ?";
    private static final String UPDATE_QUERY = """
                UPDATE `carta` SET 
                    `nombre` = ?,
                    `tipo` = ?,
                    `precio` = ?,
                    `disponibilidad` = ?,
                WHERE `carta`.`idproducto` = ?""";
    private static final String GETALL_BY_NOMBRE_QUERY = "SELECT * FROM carta WHERE nombre = ?";
    private static final String GETALL_PRODUCT_NAMES_QUERY = "SELECT DISTINCT nombre  FROM desayuno.carta";
    private static final String GET_ID_BY_NOMBRE = "SELECT carta.idproducto from desayuno.carta where carta.nombre = ?";
    private static final String GET_NOMBRE_BY_ID = "SELECT carta.nombre from desayuno.carta where carta.idproducto = ?";
    
    private static Connection conexion = Conexion.getConexion();

    public CartaDAOMySQL(){
    }
    
    @Override
    public Carta get(Integer id) {
        
        try(var pst = conexion.prepareStatement(GET_QUERY)){
            
            pst.setInt(1, id);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                var carta = new Carta();
                carta.setIdproducto(resultado.getInt("idproducto") );
                carta.setNombre( resultado.getString("nombre") );
                carta.setTipo(resultado.getString("tipo") );
                carta.setPrecio( resultado.getInt("precio") );
                carta.setDisponibilidad(resultado.getString("disponibilidad"));
                return carta;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void add(Carta c) {
        
        try( var pst = conexion.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)){
            
            pst.setString(1, c.getNombre() );
            pst.setString(2, c.getTipo());
            pst.setInt(3, c.getPrecio());
            pst.setString(4, c.getDisponibilidad() );
            
            if (pst.executeUpdate() > 0){

                var keys = pst.getGeneratedKeys();
                keys.next();
                //por aqui adkjfañkjsdfka jñefjqñsdrjerfñkajdfñoa
                //c.setId(keys.getInt(1));
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Carta c) {
        try(var pst = conexion.prepareStatement(UPDATE_QUERY)){
            
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getTipo());
            pst.setInt(3, c.getPrecio());
            pst.setString(4, c.getDisponibilidad());
            
            if( pst.executeUpdate()== 0){
                Logger.getLogger(CartaDAOMySQL.class.getName()).severe("Carta no existe.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<Carta> getAll() {
        var salida = new ArrayList<Carta>();
        
        try( var pst = conexion.prepareStatement(GETALL_QUERY)){
            
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var carta = new Carta();
                carta.setIdproducto(resultado.getInt("idproducto") );
                carta.setNombre( resultado.getString("nombre") );
                carta.setTipo(resultado.getString("tipo") );
                carta.setPrecio( resultado.getInt("precio") );
                carta.setDisponibilidad(resultado.getString("disponibilidad"));
                salida.add(carta);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public void delete(Carta c) {
        deleteById(c.getIdproducto());
    }

    @Override
    public void deleteById(Integer id) {
        try( var pst = conexion.prepareStatement(DELETE_ID_QUERY)){
        
            pst.setInt(1, id);
            
            if(pst.executeUpdate()==0){
                Logger.getLogger(CartaDAOMySQL.class.getName()).warning("Carta no existe");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @Override
    public ArrayList<Carta> getAllByNombre(String nombre) {
             var salida = new ArrayList<Carta>();
        
        try( var pst = conexion.prepareStatement(GETALL_BY_NOMBRE_QUERY)){
            
            pst.setString(1, nombre);
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var carta = new Carta();
                carta.setIdproducto(resultado.getInt("idproducto") );
                carta.setNombre( resultado.getString("nombre") );
                carta.setTipo(resultado.getString("tipo") );
                carta.setPrecio( resultado.getInt("precio") );
                carta.setDisponibilidad(resultado.getString("disponibilidad"));
                salida.add(carta);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public ArrayList<String> getAllProductNames() {
    var salida = new ArrayList<String>();
        
        try( var pst = conexion.prepareStatement(GETALL_PRODUCT_NAMES_QUERY)){
            
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                salida.add(resultado.getString("nombre"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }

    @Override
    public Integer getIdByNombre(String nombre) {
       
        try(var pst = conexion.prepareStatement(GET_ID_BY_NOMBRE)){
            
            pst.setString(1, nombre);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                Integer idBuscado = resultado.getInt("idproducto");
                
                return idBuscado;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getNombreById(Integer id) {
    
       
        try(var pst = conexion.prepareStatement(GET_NOMBRE_BY_ID)){
            
            pst.setInt(1, id);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                String nombre = resultado.getString("nombre");
                
                return nombre;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartaDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
