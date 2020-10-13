
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 *
 * @author Noah
 */
class Persona extends Thread {
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    private int x;
    private int y;
    private int z;
    public Persona(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        try {
            sk = new Socket("192.168.0.162", 10578);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id + " envía saludo");
            dos.writeUTF("hola");
            String respuesta="";
            respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve saludo: " + respuesta);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class Cliente {

    public static void main(String[] args) {
        ArrayList<Thread> clients = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Persona persona = new Persona(i);
            clients.add(persona);
            persona.start();
        }
        
        
    }
}
