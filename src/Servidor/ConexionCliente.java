/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Esta clase gestiona el envio de datos entre el servidor y el cliente al que atiende.
 * 
 * @author Daniel Rocher y Alejandro Mir
 */

public class ConexionCliente extends Thread implements Observer{
    private String paquete;
    private Socket sk; 
    private Mensajes mensajes;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;
    //private LinkedList<ConexionCliente> clientes; IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
    
    public ConexionCliente (Socket socket, String paquete, Mensajes mensajes){
        this.sk = sk;
        this.mensajes = mensajes;
        this.paquete = paquete;
        
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
            salidaDatos.writeUTF(paquete);
        } catch (IOException ex) {
            System.err.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
        }
    }
    /*public void setClientes(LinkedList<ConexionCliente> clientes) {
    this.clientes = clientes;   IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
}*/
    @Override
    public void run(){
        String mensaje;
        boolean conectado = true;
        // Se apunta a la lista de observadores de mensajes
        mensajes.addObserver(this);
            
        while (conectado) {
            try {
                entradaDatos = new DataInputStream(sk.getInputStream());

                mensaje = entradaDatos.readUTF();
                
                /*for(ConexionCliente c:clientes) {
                    if(this.getId() != c.getId())   IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
                        c.salidaDatos.writeUTF(mensaje);
            }*/
                mensajes.setMensaje(mensaje);
            } catch (IOException ex) {
                System.out.println("Cliente con la IP " + sk.getInetAddress().getHostName() + " desconectado.");
                conectado = false; 
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException ex2) {
                    System.err.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
                }
            }
        }   
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            // Envia el mensaje al cliente
            salidaDatos.writeUTF(arg.toString());
            
        } catch (IOException ex) {
            System.err.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
}
