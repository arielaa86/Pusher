/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pusher;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import java.util.Collections;
import javax.swing.JTextArea;

/**
 *
 * @author ariel
 */
public class PusherUtil {

    private String nombreUsuario;

    private String app_id;
    private String key;
    private String secret;
    private String cluster;

    public PusherUtil() {

        nombreUsuario = "";

        app_id = "";
        key = "";
        secret = "";
        cluster = "";
        
        

    }

    public void escucharMensajes(JTextArea textarea) {

        PusherOptions options = new PusherOptions();
        options.setCluster(cluster);
        Pusher pusher = new Pusher(key, options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {

            @Override
            public void onEvent(PusherEvent pe) {

                String texto = textarea.getText();

                Gson gson = new Gson();

                String mensajeJson = pe.getData().substring(0, pe.getData().length() - 1).replace("{\"message\":", "");

                Mensaje mensaje = gson.fromJson(mensajeJson, Mensaje.class);

                String linea = mensaje.getNombreUsuario() + ": " + mensaje.getMensaje();

                if (texto.isEmpty()) {
                    textarea.setText(linea);
                } else {
                    textarea.setText(texto + "\n" + linea);
                }

            }

        });

        pusher.connect();

    }

    public void enviarMensaje(String mensaje) {

        com.pusher.rest.Pusher pusher = new com.pusher.rest.Pusher(app_id, key, secret);
        pusher.setCluster(cluster);
        pusher.setEncrypted(true);

        Mensaje nuevo = new Mensaje();

        nuevo.setNombreUsuario(nombreUsuario);
        nuevo.setMensaje(mensaje);

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", nuevo));

    }

    private void enviarMensajeAdmin(String mensaje) {

        com.pusher.rest.Pusher pusher = new com.pusher.rest.Pusher(app_id, key, secret);
        pusher.setCluster(cluster);
        pusher.setEncrypted(true);

        Mensaje nuevo = new Mensaje();

        nuevo.setNombreUsuario("-----");
        nuevo.setMensaje(mensaje);

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", nuevo));

    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {

        Mensaje nuevo = new Mensaje();

        if (this.nombreUsuario.isEmpty()) {

            this.nombreUsuario = nombreUsuario;

            nuevo.setMensaje(this.nombreUsuario + " se ha unido a la conversación :-----");

        } else {

            nuevo.setMensaje(this.nombreUsuario + " ha cambiado su nombre a: " + nombreUsuario + " :-----");

            this.nombreUsuario = nombreUsuario;

        }

        enviarMensajeAdmin(nuevo.getMensaje());

    }

}
