package UDP.Unicast.e1EjemploSimple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* Crea un programa servidor que reciba por parámetro un número que representa 
 el puerto en el que escuchará, cuando reciba un mensaje lo escribirá por pantalla. 
 
 Crea un programa cliente que reciba por parámetro una cadena representando la 
 dirección ip, un número representando el puerto y una última cadena representando el 
 texto a enviar. El programa enviará la información usando UDP.*/

public class EjemploServer {

    private static final String IP = "10.0.2.15";
    private static final int MEMORIA_BUFFER = 65535;

    public static void main(String[] args) {

        int puerto = 0;

        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona al menos un número como argumento:");
        }

        try {
            DatagramSocket socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0")); //Escuchamos puerto (0.0.0.0 es cualquier ip)

            byte[] buffer = new byte[MEMORIA_BUFFER]; //Array de bytes que contiene los datos que se enviarán

            DatagramPacket paquete = new DatagramPacket(buffer, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                socket.receive(paquete); //Recibe el paquete
                
                System.out.println(new String(paquete.getData(), 0, paquete.getLength()));
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}