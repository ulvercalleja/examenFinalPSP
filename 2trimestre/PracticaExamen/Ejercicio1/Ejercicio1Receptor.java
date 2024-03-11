package PracticaExamen.Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio1Receptor {
    private static final int MEMORIA_BUFFER = 1024;

    private DatagramSocket socket;
    private int puerto;

    public Ejercicio1Receptor(int puerto) {
        this.puerto = puerto;
    }

    public interface ObservadorCuadrado {
        void actualizar(int alto,int ancho,char letra);
    }

    private List<ObservadorCuadrado> observadores = new ArrayList<>();

    public void añadirObservador(ObservadorCuadrado observador){
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorCuadrado observador){
        observadores.remove(observador);
    }

    public void notificarObservador(int alto, int ancho, char letra){
        for (ObservadorCuadrado observador : observadores) {
            observador.actualizar(alto, ancho, letra);
        }
    }

    public void Receptor() {
        try {
            socket = new DatagramSocket(puerto); //Escuchamos puerto

            byte[] buffer = new byte[MEMORIA_BUFFER];

            DatagramPacket paqueteEntrada = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(paqueteEntrada);

                String mensaje = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());

                String mensajeDividido[] = mensaje.split(" ");

                int alto = Integer.parseInt(mensajeDividido[0]);

                int ancho = Integer.parseInt(mensajeDividido[1]);
                    
                char letra = mensajeDividido[2].charAt(0);

                notificarObservador(alto, ancho, letra);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

