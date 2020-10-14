package Cliente;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class Persona extends Thread {
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    private int x, y, z;
    private int iterActual, nIteraciones;
    private String coordenadas;
    private ArrayList<String> mensajes = new ArrayList<>(); //Array con los mensajes
    private DataOutputStream sDatos;
    private int grupoCliente;
    private DataOutputStream salida;
    
     public Persona(String host, int puerto, int nIteraciones) {
        this.nIteraciones = nIteraciones;
        try{
            sk = new Socket(host, puerto);
            
            DataInputStream dis;
        try {
            dis = new DataInputStream(sk.getInputStream());
            String paquete = dis.readUTF(); //recibimos el paquete de entrada
            String[] tokens = paquete.split(","); //los dividimos por cada coma que aparece en el paquete
            this.id = Integer.parseInt(tokens[0]); //id del cliente
            this.grupoCliente = Integer.parseInt(tokens[1]); //grupo al que pertenece el cliente
            System.out.println("Quieres conectarte a " + host + " en el puerto " + puerto + " con la ID de usuario: " + id + ".");

        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run() {
         
        DataInputStream entrada = null;
        String mensaje;
        
        try { //input de entrada
            entrada = new DataInputStream(sk.getInputStream());
        } catch (IOException ex) {
            System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println("El socket no se creó correctamente. ");
        }

        boolean conectado = true;
        iterActual = 0;
        
        // Bucle de S iteraciones que recibe mensajes del servidor
        while (conectado && iterActual < nIteraciones) {
            try {
                //Método de creación de coordenadas aleatorias
                String coordenadas = GeneracionCoordenadas();
                //Enviamos las coordenadas al servidor mediante un datagrama de salida
                EnviarMensajeServidor(coordenadas);
                int nMensajes = 0; //En el caso de tardar mas de un cierto tiempo usaremos un timeout
                //sk.setSoTimeout(20000);
                while(nMensajes < 10){ 
                    mensaje = entrada.readUTF();
                    System.out.println(mensaje);                    
                    nMensajes++;
                    
                }
                           
            } catch (IOException ex) {
                System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                System.err.println("El socket no se creo correctamente. ");
                conectado = false;
            }
            iterActual++;
        }
    } 
    
    public void EnviarMensajeServidor(String coordenadas) {
        this.coordenadas = coordenadas;
        try {
            //Creamos el datagrama de salida
            salida = new DataOutputStream(sk.getOutputStream());
        } catch (IOException ex) {
            System.err.println("Error al crear el stream de salida : " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println("El socket no se creo correctamente. ");
        }
    
        try{
            System.out.println("Envio: " + id + ": " + coordenadas); //Separado por :
            salida.writeUTF(id + ": " + coordenadas );
            
        } catch (IOException ex) {
            System.err.println("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }   
    
    private String GeneracionCoordenadas() {
        Random random = new Random();
        String coordenadas;
         //Coordenadas del 1 al 10 para cada parámetro aleatorias   
        x = random.nextInt(10) + 1;
        y = random.nextInt(10) + 1;
        z = random.nextInt(10) + 1;       
           
        coordenadas = "(" + x + "," + y + "," + z + ")";
        
        return coordenadas;
    }
}