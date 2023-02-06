package com.mycompany.aplicaciondesayunoscrud;

import controller.CartaDAOMySQL;
import controller.PedidoDAOMySQL;
import controller.Conexion;
import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author Antonio y Loren
 */
public class Main {

    public static Connection conexion;
    static CartaDAOMySQL daoCarta = new CartaDAOMySQL();
    static PedidoDAOMySQL daoPedido = new PedidoDAOMySQL();
    
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(views.VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(views.VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(views.VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(views.VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new views.VentanaPrincipal(daoCarta, daoPedido).setVisible(true);
            }
        });      
       }
    }
    
