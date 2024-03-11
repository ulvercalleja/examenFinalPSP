package UDP.Unicast.e8MiniProyecto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class MiniProyectoCliente {
    private static final int MEMORIA_BUFFER = 1024;
    static int puerto = 0;
    static String direccionIP = "";
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
    
        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
            direccionIP = args[1];
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona al menos un número como argumento:");
        }

        try {
            DatagramSocket socket = new DatagramSocket();
            
            while (true) {
                enviar(socket);
                recibir(socket);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void recibir(DatagramSocket socket){
        try {
            //Entrada del mensaje Cliente
            byte[] bufferEntrada = new byte[MEMORIA_BUFFER];

            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, MEMORIA_BUFFER); //Objeto DatagramPacket que se utilizará para recibir datos

            socket.receive(paqueteEntrada);

            System.out.println("Servidor: " + new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void enviar(DatagramSocket socket){

        try {
            InetAddress serverAddress = InetAddress.getByName(direccionIP);

            String msg = scanner.nextLine();

            byte[] bufferSalida = msg.getBytes();

            //Salida del mensaje Cliente
            DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, serverAddress, puerto); //Objeto DatagramPacket que se utilizará para enviar datos

            socket.send(paqueteSalida);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

