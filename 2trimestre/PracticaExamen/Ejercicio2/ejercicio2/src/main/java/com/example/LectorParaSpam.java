package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LectorParaSpam {
    private String rutaMensajes;
    private String rutaCorreos;
    private ArrayList<ObservadorMensajes> observadores;

    public LectorParaSpam(String rutaMensajes, String rutaCorreos) {
        this.rutaMensajes = rutaMensajes;
        this.rutaCorreos = rutaCorreos;
        observadores = new ArrayList<ObservadorMensajes>();
    }

    public interface ObservadorMensajes {
        void actualizar(String info);
    }

    public void añadirObservador(ObservadorMensajes observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorMensajes observador) {
        observadores.remove(observador);
    }

    public void notificarObservador(String info) {
        for (ObservadorMensajes observador : observadores) {
            observador.actualizar(info);
        }
    }

    public void comenzarLectura() throws IOException {
        ArrayList<String> mensajes = new ArrayList();

        BufferedReader readerMensajes = new BufferedReader(new FileReader(rutaMensajes));

        String lineaMensajes = "";
        String mensaje = "";

        while ((lineaMensajes = readerMensajes.readLine()) != null) {
            mensaje = lineaMensajes;
            mensajes.add(mensaje);
        }

        ArrayList<String> correos = new ArrayList();

        BufferedReader readerCorreos = new BufferedReader(new FileReader(rutaCorreos));

        String lineaCorreos = "";
        String correo = "";

        while ((lineaCorreos = readerCorreos.readLine()) != null) {
            correo = lineaCorreos;
            correos.add(correo);
        }

        for (int i = 0; i < mensajes.size(); i++) {
            String correoAviso = correos.get(i);
            String mensajeAviso = mensajes.get(i);
            notificarObservador(correoAviso + "-" + mensajeAviso);
        }
    }
}
