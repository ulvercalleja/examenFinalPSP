package UDP.Unicast.e2Eco;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* Crea un programa con parámetros similares al anterior. Esta vez el servidor 
se ejecutará con un bucle while true. Será un servicio de eco, envía al cliente 
la información que se le ha mandado. El cliente envía información, y espera una 
respuesta en el mismo puerto.*/

public class EcoServer {

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

            byte[] buffer = new byte[MEMORIA_BUFFER]; //Array de bytes que contiene los datos que se enviarán

            DatagramPacket paqueteEntrada = new DatagramPacket(buffer, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                socket.receive(paqueteEntrada); //Recibe el paquete
                
                System.out.println("Recibido: " + new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()));
                
                InetAddress direccionCliente = paqueteEntrada.getAddress();

                int puertoCliente = paqueteEntrada.getPort();
                
                //Sale paquete de vuelta
                DatagramPacket paqueteSalida = new DatagramPacket(buffer, buffer.length, direccionCliente, puertoCliente);

                socket.send(paqueteSalida);

                System.out.println("Enviado de vuelta: " + new String(paqueteSalida.getData(), 0, paqueteSalida.getLength()));
                socket.close();
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}