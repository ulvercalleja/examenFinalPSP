package UDP.Broadcast.e1PracticaPrueba;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PruebaBroadcastCliente {
    final static String SERVER_IP = "192.168.0.19";
    final static int SERVER_PORT = 4321;

    public static void main(String[] args) {
        try {  
            DatagramSocket socket =  new DatagramSocket();
            byte bufferSalida[] = "Hola clase!!!\n".getBytes();
            socket.setBroadcast(true);

            while(true){
                InetAddress ipServidor = InetAddress.getByName(SERVER_IP);

                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, ipServidor, SERVER_PORT);

                socket.send(paqueteSalida);
                
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
