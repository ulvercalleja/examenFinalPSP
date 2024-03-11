package UDP.Unicast.e4ChatEnvRec;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/* Implementa un chat cliente-servidor, estos programas serán monothread.
-Para facilitar la tarea primero envía el cliente, y espera respuesta.
-Para facilitar la tarea primero espera respuesta el servidor, y luego envía respuesta.
-Tanto el cliente como el servidor reciben como parámetro el puerto. El cliente también recibirá como parámetro la dirección ip.
-Ambos programas muestran un prompt al usuario para pedir y mostrar los mensajes enviados.*/

public class ChatEnvRecServer {
    private static final int MEMORIA_BUFFER = 1024;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int puerto = 0;

        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona al menos un número como argumento:");
        }

        try {
            DatagramSocket socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0"));

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                DatagramPacket packageCliente = recibir(socket);
                enviar(socket, packageCliente);
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static DatagramPacket recibir(DatagramSocket socket){
        try {
            byte[] bufferEntrada = new byte[MEMORIA_BUFFER];

            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            //Recibe el paquete
            socket.receive(paqueteEntrada);

            System.out.println("Cliente: " + new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()));

            return paqueteEntrada;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void enviar(DatagramSocket socket, DatagramPacket paqueteEntrada){
            try {
                //Sale paquete de vuelta
                InetAddress direccionCliente = paqueteEntrada.getAddress();

                int puertoCliente = paqueteEntrada.getPort();

                String respuesta = scanner.nextLine();

                byte[] bufferSalida = respuesta.getBytes();

                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                socket.send(paqueteSalida);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
