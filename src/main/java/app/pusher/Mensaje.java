/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pusher;

import java.io.Serializable;

/**
 *
 * @author ariel
 */
public class Mensaje implements Serializable{

    private String nombreUsuario;
    private String mensaje;

    public Mensaje() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "{" + "nombreUsuario:" + nombreUsuario + ", mensaje:" + mensaje + '}';
    }
    
    
    
    
    

}
