package UDP.Broadcast.e1PracticaPrueba;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/* Elige una dirección en el rango ```172.20.0.0/16````. Configura cada equipo en ese rango
Comprueba la comunicación haciendo ping
Ahora genera un código de servidor que escriba por pantalla el mensaje que recibe, la dirección ip y el puerto.
Después escribe un cliente que haga envíos por broadcast */
public class PruebaBroadcastServer {
    private static final int MEMORIA_BUFFER = 65535;
    public static void main(String[] args) {
        
        int puerto = 1024;

        try {

            DatagramSocket socket =  new DatagramSocket(puerto);

            System.out.println("Servidor escuchando en el puerto " + puerto);

            byte[] bufferEntrada = new byte[MEMORIA_BUFFER];

            while (true) {
                
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                socket.receive(paqueteEntrada);
                
                InetAddress ipCliente = paqueteEntrada.getAddress();

                System.out.println(ipCliente + ": " + new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()));
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
