
package Servidor;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Objeto observable del patron observer.
 * 
 * @author Daniel Rocher y Carlos Guardiola
 */
public class Mensajes extends Observable{

    private String mensaje;
    
    public Mensajes(){
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        this.setChanged();
        // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
        this.notifyObservers(this.getMensaje());
    }
}