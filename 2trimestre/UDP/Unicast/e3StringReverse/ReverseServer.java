package UDP.Unicast.e3StringReverse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* Implementa un servicio que devuelva la cadena recibida pero dada la vuelta. */

public class ReverseServer {

    private static final String IP = "10.0.2.15";
    private static final int MEMORIA_BUFFER = 1024;

    public static void main(String[] args) {
        int puerto = 0;

        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona al menos un número como argumento:");
        }

        try {
            DatagramSocket socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0")); //Escuchamos puerto

            byte[] bufferEntrada = new byte[MEMORIA_BUFFER]; //Array de bytes que contiene los datos que se enviarán

            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                socket.receive(paqueteEntrada); //Recibe el paquete
                
                String mensajeRecibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());

                System.out.println("Recibido: " + mensajeRecibido);

                //Sale paquete de vuelta
                InetAddress direccionCliente = paqueteEntrada.getAddress();

                int puertoCliente = paqueteEntrada.getPort();
        
                String mensajeReverse = new StringBuilder(mensajeRecibido).reverse().toString();
                
                byte[] bufferSalida = mensajeReverse.getBytes();

                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                
                socket.send(paqueteSalida);

                System.out.println("Enviado de vuelta: " + new String(paqueteSalida.getData(), 0, paqueteSalida.getLength()));
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
