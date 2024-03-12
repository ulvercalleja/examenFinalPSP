package ejercicio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServidorUDP {
    private final int NUMEROFINREPORTES = 5;
    private final int NUMEROFINCADENA = 4;

    private DatagramSocket socket;
    private int puerto;

    public ServidorUDP(int puerto) {
        this.puerto = puerto;
    }

    public interface ObservadorUDP {
        void actualizar(String[] mensajeDividido);
    }

    private List<ObservadorUDP> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorUDP observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorUDP observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(String[] mensajeDividido) {
        for (ObservadorUDP observador : observadores) {
            observador.actualizar(mensajeDividido);
        }
    }

    public void ReceptorDatagramas() {
        try {
            if (socket == null) {
                socket = new DatagramSocket(puerto);
            }
            byte[] paquete = new byte[1024];
            DatagramPacket dPacket = new DatagramPacket(paquete, paquete.length);
            while (true) {
                try {
                    socket.receive(dPacket);
                    String mensaje = new String(dPacket.getData(), 0, dPacket.getLength());
                    String mensajeDividido[] = mensaje.split(" ");
                    mensajeDividido[NUMEROFINCADENA] = mensajeDividido[NUMEROFINCADENA].strip();
                    if (mensajeDividido.length == NUMEROFINREPORTES) {
                        notificarObservadores(mensajeDividido);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

