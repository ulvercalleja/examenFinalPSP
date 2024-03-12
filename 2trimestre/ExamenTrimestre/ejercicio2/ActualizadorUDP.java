package ejercicio2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ejercicio2.ServidorUDP.ObservadorUDP;

public class ActualizadorUDP implements ObservadorUDP {
    private final String BANCO = "B";
    private final String OKUPA = "O";
    private final String RUTAARCHIVO = "./ejercicio2/bancos.txt";

    private static DatagramSocket socket;
    private int puertoEnviador;

    public ActualizadorUDP(int puertoEnviador) {
        this.puertoEnviador = puertoEnviador;
    }

    public void EnviadorPorUDP(String socoro) {
        if (socket == null) {
            try {
                socket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        byte[] datos = socoro.getBytes();
        try {
            DatagramPacket dPacket = new DatagramPacket(datos, datos.length,
                    InetAddress.getByName("192.168.77.255"), puertoEnviador);
            try {
                socket.send(dPacket);
            } catch (IOException e) {
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String datosRecibidos[]) {
        String socoro = " ¡ALARMA!\n";
        try {
            BufferedWriter escritortimeStamp = new BufferedWriter(new FileWriter(RUTAARCHIVO));
            for (int i = 0; i < datosRecibidos.length; i++) {
                if (datosRecibidos[i].toUpperCase().equals(OKUPA)) {
                    EnviadorPorUDP(socoro);
                } else if (datosRecibidos[i].toUpperCase().equals(BANCO)) {
                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new Date());
                    escritortimeStamp.append(timeStamp + "\n");
                    EnviadorPorUDP(socoro);
                }
            }
            escritortimeStamp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

