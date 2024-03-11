package UDP.Unicast.e5ChatMolesta;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ChatMolestaCliente {
    private static final int MEMORIA_BUFFER = 1024;
    public static String direccionIP = "";
    public static int puerto = 0;
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        if (args.length == 2) {
            direccionIP = args[0];
            puerto = Integer.parseInt(args[1]);
        } else {
            // Si no se proporcionaron argumentos
            System.out.println("Por favor proporciona 2 argumentos:");
        }

        try {
            //Sale paquete 

            DatagramSocket socket = new DatagramSocket();
            
            Thread hiloEnviador = new Thread(() -> {
                while (true) {
                    enviar(socket);
                }
            });

            Thread hiloRecibidor = new Thread(() -> {
                while (true) {
                    recibir(socket);
                }
            });
            
            hiloEnviador.start();
            hiloRecibidor.start();

            hiloEnviador.join();
            hiloRecibidor.join();
            socket.close();
        } catch (Exception e) {
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

            String mensaje = scanner.nextLine();

            byte[] bufferSalida = mensaje.getBytes();

            //Salida del mensaje Cliente
            DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, serverAddress, puerto); //Objeto DatagramPacket que se utilizará para enviar datos

            socket.send(paqueteSalida);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
