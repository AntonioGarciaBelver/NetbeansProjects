/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import models.Carta;

/**
 *
 * @author LorenLynchMcrae
 */
public interface CartaDAO {
     
    Carta get(Integer id);
    
    void add(Carta c);
    
    void update(Carta c);
    
    ArrayList<Carta> getAll();
    
    void delete(Carta c);
    
    void deleteById(Integer id);
    
    ArrayList<Carta> getAllByNombre(String nombre);
    
    ArrayList<String> getAllProductNames();
    
    Integer getIdByNombre(String nombre);
    
    String getNombreById(Integer id);
}
