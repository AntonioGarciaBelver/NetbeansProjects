package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    

    public static Connection conexion;
    
    static {
        try {

            var conf = new Properties();

            try {
                conf.load(new FileReader("config.properties"));

                conexion = DriverManager.getConnection(conf.getProperty("url"), conf.getProperty("user"), conf.getProperty("password"));
                System.out.println("Conexión realizada con éxito");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static Connection getConexion() {
        return conexion;
    }

}
