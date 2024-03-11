package PracticaExamen.Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import PracticaExamen.Ejercicio1.Ejercicio1Receptor.ObservadorCuadrado;

public class Ejercicio1Enviador implements ObservadorCuadrado{

    private static DatagramSocket socket;
    private int  puertoEnviador;

    public Ejercicio1Enviador(int puertoEnviador){
        this.puertoEnviador = puertoEnviador;
    }

    public void EnviadorPorUDP (String cuadrado){
        try {
            
            socket = new DatagramSocket();

            socket.setBroadcast(true);

            byte[] buffer = cuadrado.getBytes();

            DatagramPacket paqueteEnviador = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.0.255"), puertoEnviador);

            socket.send(paqueteEnviador);

        } catch (SocketException e) {
                e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int alto, int ancho, char letra) {
        String cuadrado = GenerarCuadrado.GenerarCuadradoUDP(alto, ancho, letra);
        EnviadorPorUDP(cuadrado);
    }
    
}
