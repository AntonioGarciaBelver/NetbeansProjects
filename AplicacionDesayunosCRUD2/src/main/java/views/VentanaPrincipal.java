/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controller.CartaDAO;
import controller.PedidoDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Carta;
import models.Pedido;

/**
 *
 * @author Loren y Antonio
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    static PedidoDAO datosPedido;
    static CartaDAO datosCarta;
    private Integer idPedidoActual = null;
    private NombreFiltrado nf = new NombreFiltrado(this,true);
    private String nombreFiltrar;

    public void setNombreFiltrar(String nombreFiltrar) {
        this.nombreFiltrar = nombreFiltrar;
    }
    
    private static final int COL_ID = 0;
    private static final int COL_FECHA = 1;
    private static final int COL_CLIENTE = 2;
    private static final int COL_PRODUCTO = 3;    
    private static final int COL_RECOGIDO = 4;

    public CartaDAO getDatosCarta() {
        return datosCarta;
    }
   
    
    public VentanaPrincipal(CartaDAO datosExternosCarta, PedidoDAO datosExternosPedido) {
        datosPedido = datosExternosPedido;     
        datosCarta = datosExternosCarta;
        initComponents();
        
        actualizarTabla();
        
    }

    //Distintos métodos de rellenar las tablas
    
    private void actualizarTabla() {
        modeloTablaPedido();
        ArrayList<Pedido> pedidos = datosPedido.getAll();
        
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        
        for(var pp : pedidos) {
            String recogido;
            if(pp.getEstado()==0){
                recogido = "Pendiente";
            }else{
                recogido = "Recogido";
            }
            Object fila[] = {
                pp.getIdpedido(),
                pp.getFecha(),
                pp.getCliente(),
                datosCarta.getNombreById(pp.getProducto()),
                recogido
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);    
        }
    }
    
    public void tablaPedidosDia(){
        
        modeloTablaPedido();
        ArrayList<Pedido> pedidosDia=  datosPedido.getPedidosDia();
        String productos = datosCarta.getNombreById(WIDTH);
        
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        
        for(var pp : pedidosDia) {
            String recogido;
            if(pp.getEstado()==0){
                recogido = "Pendiente";
            }else{
                recogido = "Recogido";
            }
            Object fila[] = {
                pp.getIdpedido(),
                pp.getFecha(),
                pp.getCliente(),
                datosCarta.getNombreById(pp.getProducto()),
                recogido
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);    
        }
    }
    
    public void tablaPedidoPor(String nombre){
        
        modeloTablaPedido();
        ArrayList<Pedido> pedidosDia=  datosPedido.getPedidosWithNombre(nombre);
        String productos = datosCarta.getNombreById(WIDTH);
        
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        
        for(var pp : pedidosDia) {
            String recogido;
            if(pp.getEstado()==0){
                recogido = "Pendiente";
            }else{
                recogido = "Recogido";
            }
            Object fila[] = {
                pp.getIdpedido(),
                pp.getFecha(),
                pp.getCliente(),
                datosCarta.getNombreById(pp.getProducto()),
                recogido
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);    
        }
    }
    
    public void mostrarProductos(){
        
        modeloTablaCarta();
        ArrayList<Carta> carta=  datosCarta.getAll();
        
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        
        for(var cc : carta) {
            String disponible;
            if(cc.getDisponibilidad().equals("s")){
                disponible = "Disponible";
            }else{
                disponible = "No disponible";
            }
            
            Object fila[] = {
                cc.getIdproducto(),
                cc.getNombre(),
                cc.getTipo(),
                cc.getPrecio(),
                disponible
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);    
        }
    }
    
    public void mostrarPedidosPendientes(){
        
        modeloTablaPedido();
          ArrayList<Pedido> pedidosDia=  datosPedido.getPedidosPendientes();
        
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        
        for(var pp : pedidosDia) {
            String recogido;
            if(pp.getEstado()==0){
                recogido = "Pendiente";
            }else{
                recogido = "Recogido";
            }
            Object fila[] = {
                pp.getIdpedido(),
                pp.getFecha(),
                pp.getCliente(),
                datosCarta.getNombreById(pp.getProducto()),
                recogido
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);    
        }
    }
    
    public void mostrarEstadistica(String estadistica, String resultado){
        
        modeloTablaEstadistica();
        ( (DefaultTableModel) tabla.getModel() ).setRowCount(0);
        Object fila[] = {
                estadistica,
                resultado
            };
            ( (DefaultTableModel) tabla.getModel() ).addRow(fila);  
    }
    
    
    private void modeloTablaPedido(){
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Cliente", "Producto", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }
    
    private void modeloTablaCarta(){
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Tipo", "Precio", "Disponibilidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }
    
    private void modeloTablaEstadistica(){
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Estadística", "Resultado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }
    
    //Fin de los métodos para rellenar las tablas
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        checkBoxRecogido = new javax.swing.JCheckBox();
        menuBarBarra = new javax.swing.JMenuBar();
        jMenuArchivo = new javax.swing.JMenu();
        menuItemSalir = new javax.swing.JMenuItem();
        menuItemVolver = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuFiltros = new javax.swing.JMenu();
        menuItemPedHoy = new javax.swing.JMenuItem();
        menuItemRecPend = new javax.swing.JMenuItem();
        menuItemPedCliente = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuItemGanancias = new javax.swing.JMenuItem();
        menuItemMejorCliente = new javax.swing.JMenuItem();
        menuItemTotalClientes = new javax.swing.JMenuItem();
        menuItemProductoPopular = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Cliente", "Producto", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevo);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBorrar);

        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.GridLayout(3, 2, 15, 5));

        jLabel2.setText("Cliente");
        jPanel2.add(jLabel2);

        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });
        jPanel2.add(txtCliente);

        jLabel1.setText("Producto");
        jPanel2.add(jLabel1);
        jPanel2.add(txtProducto);

        checkBoxRecogido.setText("Recogido");
        jPanel2.add(checkBoxRecogido);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jMenuArchivo.setText("Archivo");

        menuItemSalir.setText("Salir");
        menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(menuItemSalir);

        menuItemVolver.setText("Volver");
        menuItemVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVolverActionPerformed(evt);
            }
        });
        jMenuArchivo.add(menuItemVolver);

        jMenuItem1.setText("Ver Carta");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuItem1);

        menuBarBarra.add(jMenuArchivo);

        jMenuFiltros.setText("Filtros");

        menuItemPedHoy.setText("Pedidos hoy");
        menuItemPedHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPedHoyActionPerformed(evt);
            }
        });
        jMenuFiltros.add(menuItemPedHoy);

        menuItemRecPend.setText("Recogida pendiente");
        menuItemRecPend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRecPendActionPerformed(evt);
            }
        });
        jMenuFiltros.add(menuItemRecPend);

        menuItemPedCliente.setText("Pedido por...");
        menuItemPedCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPedClienteActionPerformed(evt);
            }
        });
        jMenuFiltros.add(menuItemPedCliente);

        menuBarBarra.add(jMenuFiltros);

        jMenu1.setText("Estadística");

        menuItemGanancias.setText("Ganancias mes");
        menuItemGanancias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemGananciasActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemGanancias);

        menuItemMejorCliente.setText("Mejor Cliente");
        menuItemMejorCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMejorClienteActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemMejorCliente);

        menuItemTotalClientes.setText("Total de clientes");
        menuItemTotalClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTotalClientesActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemTotalClientes);

        menuItemProductoPopular.setText("Producto popular");
        menuItemProductoPopular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProductoPopularActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemProductoPopular);

        menuBarBarra.add(jMenu1);

        setJMenuBar(menuBarBarra);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItemSalirActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        Integer pos_actual = tabla.getSelectedRow();
        
        DefaultTableModel modeloTabla = (DefaultTableModel)tabla.getModel();
        
        txtCliente.setText((String) modeloTabla.getValueAt(pos_actual, COL_CLIENTE));        
        
        txtProducto.setText((String) modeloTabla.getValueAt(pos_actual, COL_PRODUCTO) );  

        Integer recogido = datosPedido.getEstado((Integer) modeloTabla.getValueAt(pos_actual, COL_ID));
        
        if(recogido != 0){
            checkBoxRecogido.setSelected(true);
        }else{
            checkBoxRecogido.setSelected(false);
        }
        
        idPedidoActual = (Integer) modeloTabla.getValueAt(pos_actual, COL_ID);
    }//GEN-LAST:event_tablaMouseClicked

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed

        if( formularioVacio() ) return;

        Integer opcion = JOptionPane.showConfirmDialog(null, "¿Deseas borrar?", 
                "Borrar Pedido",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if( opcion == JOptionPane.OK_OPTION) {
            datosPedido.deleteById(idPedidoActual);
            actualizarTabla();
            idPedidoActual = null;
            borrarFormulario();
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if( formularioVacio() ) return;
        
        Pedido pedido = datosPedido.get(idPedidoActual);

        completarPedidoDesdeFormulario(pedido);

        datosPedido.update(pedido);

        actualizarTabla();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        if( formularioVacio() ) return;

        Pedido pedido = new Pedido();
        completarPedidoDesdeFormulario(pedido);

        datosPedido.add(pedido);

        actualizarTabla();

        borrarFormulario();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void menuItemVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVolverActionPerformed
        modeloTablaPedido();
        actualizarTabla();
        txtCliente.setText("");
        txtProducto.setText("");
        checkBoxRecogido.setSelected(false);
    }//GEN-LAST:event_menuItemVolverActionPerformed

    private void menuItemPedClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPedClienteActionPerformed
        nf.setVisible(true);
        nombreFiltrar = nf.getNombre();
        tablaPedidoPor(nombreFiltrar);
    }//GEN-LAST:event_menuItemPedClienteActionPerformed

    private void menuItemPedHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPedHoyActionPerformed
        
        tablaPedidosDia();
    }//GEN-LAST:event_menuItemPedHoyActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        modeloTablaCarta();
        mostrarProductos();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteActionPerformed

    private void menuItemRecPendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRecPendActionPerformed
        modeloTablaPedido();
        mostrarPedidosPendientes();
    }//GEN-LAST:event_menuItemRecPendActionPerformed

    private void menuItemGananciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemGananciasActionPerformed
        modeloTablaEstadistica();
        mostrarEstadistica("Ganancias 30 días", Integer.toString(datosPedido.gananciasMes()));
    }//GEN-LAST:event_menuItemGananciasActionPerformed

    private void menuItemMejorClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMejorClienteActionPerformed
        modeloTablaEstadistica();
        mostrarEstadistica("Mejor Cliente", datosPedido.mejorCliente());
    }//GEN-LAST:event_menuItemMejorClienteActionPerformed

    private void menuItemTotalClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTotalClientesActionPerformed
        modeloTablaEstadistica();
        mostrarEstadistica("Total de clientes", Integer.toString(datosPedido.totalClientes()));
    }//GEN-LAST:event_menuItemTotalClientesActionPerformed

    private void menuItemProductoPopularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProductoPopularActionPerformed
        modeloTablaEstadistica();
        mostrarEstadistica("Producto más popular", datosPedido.productoPopular());
    }//GEN-LAST:event_menuItemProductoPopularActionPerformed

    private void completarPedidoDesdeFormulario(Pedido pedido) {

        pedido.setCliente( txtCliente.getText() );
        Integer producto = datosCarta.getIdByNombre(txtProducto.getText());
        pedido.setProducto(producto );
        if(checkBoxRecogido.isSelected()){
            pedido.setEstado(1);
        }else{
            pedido.setEstado(0);
        }
    }    
    
    private void borrarFormulario() {
        txtCliente.setText("");
        txtProducto.setText("");        
    }

    private boolean formularioVacio(){
          Boolean vacio =  "".equals(txtCliente.getText());
          if(vacio){
            JOptionPane.showMessageDialog(null, "Debes completar todos los campos",
            "Campos vacios", JOptionPane.INFORMATION_MESSAGE);
          }
          return vacio;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox checkBoxRecogido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenuArchivo;
    private javax.swing.JMenu jMenuFiltros;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBarBarra;
    private javax.swing.JMenuItem menuItemGanancias;
    private javax.swing.JMenuItem menuItemMejorCliente;
    private javax.swing.JMenuItem menuItemPedCliente;
    private javax.swing.JMenuItem menuItemPedHoy;
    private javax.swing.JMenuItem menuItemProductoPopular;
    private javax.swing.JMenuItem menuItemRecPend;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JMenuItem menuItemTotalClientes;
    private javax.swing.JMenuItem menuItemVolver;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
