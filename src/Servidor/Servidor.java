package Servidor;



import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Servidor extends JFrame{


    static int ID_USUARIO = 1;
    
    private JTextArea mensajesChat;
    private Socket sk;
    private Ventana ventana;
            
    private static int clientes;
    private static int vecinos;
    private static int iteraciones;
    
    public Servidor(){
        
        Ventana v = new Ventana();
        clientes = v.getN();
        vecinos = v.getV();
        iteraciones = v.getS();
        
        System.out.println("Simulación de: " + clientes + " clientes divididos en " + clientes/vecinos + " grupos y se realizaran " + iteraciones + " iteraciones.");
               
    }
    public static void main(String[] args) {
        Servidor s = new Servidor();
        int puerto = 1235;
        ServerSocket servidor = null; 
        Socket sk = null;
        ArrayList<Mensajes> vectorMensajes = new ArrayList<>();
        ArrayList<Integer> grupos = new ArrayList<Integer>();
        DataOutputStream dataOutputStream;
        Ventana vent = new Ventana();
        
        try {
            servidor = new ServerSocket(puerto);
            
            String datos;
            datos = String.valueOf(clientes) +" "+ String.valueOf(iteraciones);
            
            //Envio datos principales a socket auxiliar para asignar en el proceso Clientes
            sk = servidor.accept();
            dataOutputStream = new DataOutputStream(sk.getOutputStream());
            dataOutputStream.writeUTF(datos);
            //LinkedList Clientes;  IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
            //Clientes = new LinkedList<ConexionCliente>(); IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
            // Bucle infinito para esperar conexiones
            while (true) {
                System.out.println("Servidor a la espera de conexiones.");
                sk = servidor.accept();
                
                int grupo = AsignacionGrupoTrabajo(ID_USUARIO, vecinos);
                String paqueteUsuario = String.valueOf(ID_USUARIO) + "," + String.valueOf(grupo);
                System.out.println("Cliente con la ID " + ID_USUARIO + " del grupo " + grupo + " conectado.");
                if(!grupos.contains(grupo)){
                    vectorMensajes.add(new Mensajes());
                    grupos.add(grupo);
                }
               
                ConexionCliente cc = new ConexionCliente(sk, paqueteUsuario, vectorMensajes.get(grupo - 1));
                //Clientes.add(cc); IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
                //cc.setClientes(Clientes); IMPLEMENTACION DE LA LINKEDLIST, MISMO FUNC. QUE OBSERVER
                cc.start();
                
                ID_USUARIO++;
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally{
            try {
                sk.close();
                servidor.close();
                System.out.println("SADAS");
            } catch (IOException ex) {
                System.err.println("Error al cerrar el servidor: " + ex.getMessage());
            }
        } 
                
        
    }
    
    private static int AsignacionGrupoTrabajo(int idUsuario, int numVecinos) {
        int grupo = 1;
        double division;
        
        division = Math.ceil((float)idUsuario / numVecinos);
        grupo = (int) division;

        return grupo;
    }
                
}
