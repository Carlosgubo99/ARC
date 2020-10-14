package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 *
 * @author Daniel Rocher y Carlos Guardiola
 */


public class Cliente {

    public static void main(String[] args) {
        ArrayList<Thread> clients = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            clients.add(new Persona(i));
        }
        for (Thread thread : clients){
            thread.start();
        }
        
        
    }
}
