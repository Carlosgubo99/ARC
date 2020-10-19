
package Cliente;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Cliente extends JFrame {
    
    private JTextArea mensajesChat;
    private Socket socketAux;
    private ArrayList<Persona> clientes = new ArrayList<>();
    private int N, S;
    
    private int puerto = 1235;
    private String host = "localhost";
    
    public Cliente(){
        super("Cliente Chat");
        // Elementos de la ventana
        mensajesChat = new JTextArea();
        mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
        mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
        mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
        JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
        
        
        // Colocacion de los componentes en la ventana
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(20, 20, 20, 20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(scrollMensajesChat, gbc);
        // Restaura valores por defecto
        gbc.gridwidth = 1;        
        gbc.weighty = 0;
        
        gbc.fill = GridBagConstraints.HORIZONTAL;        
        gbc.insets = new Insets(0, 20, 20, 20);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        // Restaura valores por defecto
        gbc.weightx = 0;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        
        this.setBounds(400, 100, 400, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
                
        
        // Se crea el socket auxiliar para recibir los datos principales
        try {
            socketAux = new Socket(host, puerto);
            DataInputStream inputStream = new DataInputStream(socketAux.getInputStream());
            String datos = inputStream.readUTF();
            
            String[] tokens = datos.split(" ");
            N = Integer.parseInt(tokens[0]);
            S = Integer.parseInt(tokens[1]);
            socketAux.close();
        } catch (UnknownHostException ex) {
            System.err.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } catch (IOException ex) {
            System.err.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }

        for(int i = 0; i < N; i++){
            clientes.add(new Persona(host, puerto, S));
        }
        /*try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
        for(int j = 0; j < N; j++){
            clientes.get(j).start();
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Cliente();
        
    }

}