package UDP.Unicast.e2Eco;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/*Crea un programa cliente que reciba por parámetro una cadena representando la 
 dirección ip, un número representando el puerto y una última cadena representando el 
 texto a enviar. El programa enviará la información usando UDP.*/

public class EcoCliente {

    private static final int MEMORIA_BUFFER = 1024;

    public static void main(String[] args) {
        String mensaje = "";
        String direccionIP = "";
        int puerto = 0;

        if (args.length == 3) {
            direccionIP = args[0];
            puerto = Integer.parseInt(args[1]);
            mensaje = args[2];
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona 3 argumentos:");
        }

        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] bufferSalida = mensaje.getBytes(); 
            
            //Salida del mensaje Cliente
            DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, InetAddress.getByName(direccionIP), puerto); //Objeto DatagramPacket que se utilizará para enviar datos

            System.out.println("Cliente enviando datos en el puerto: " + puerto + "...");

            socket.send(paqueteSalida);

            System.out.println("Mensaje enviado: " + mensaje);
            
            //Entrada del mensaje Cliente
            byte[] bufferEntrada = new byte[MEMORIA_BUFFER];

            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            socket.receive(paqueteEntrada);

            System.out.println("Recibido de vuelta: " + new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()));

            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
