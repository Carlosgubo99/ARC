/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Noah
 */
public class Ventana extends JDialog{
    
    private JTextField tfClientes;
    private JTextField tfVecinos;
    private JTextField tfIteraciones;
    
    /**
     * Constructor de la ventana de configuracion inicial
     * 
     * @param padre Ventana padre
     */
    public Ventana() {
        
        this.setLayout(new GridLayout(4,4,8,8));
        
        JLabel lbClientes = new JLabel("Clientes:");
        JLabel lbVecinos = new JLabel("Vecinos por grupo:");
        JLabel lbIteraciones = new JLabel("Iteraciones:");
        
        tfClientes = new JTextField("100");
        tfVecinos = new JTextField("10");
        tfIteraciones = new JTextField("3");
        
        JButton btAceptar = new JButton("Aceptar");
        this.add(lbClientes);
        this.add(tfClientes);
        this.add(lbVecinos);
        this.add(tfVecinos);
        this.add(lbIteraciones);
        this.add(tfIteraciones);
        this.add(btAceptar);
        
        
        btAceptar.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        

        
        this.pack(); // Le da a la ventana el minimo tamaño posible
        this.setLocation(450, 200); // Posicion de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Deshabilita el boton de cierre de la ventana 
        this.setVisible(true);
    }
    
    
   public int getN(){
        return Integer.parseInt(this.tfClientes.getText());
    }
    
    public int getV(){
        return Integer.parseInt(this.tfVecinos.getText());
    }
    
    public int getS(){
        return Integer.parseInt(this.tfIteraciones.getText());
    }


}
