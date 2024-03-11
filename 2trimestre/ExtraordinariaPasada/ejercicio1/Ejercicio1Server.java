package ExtraordinariaPasada.ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Ejercicio1Server {
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
                
                String respuesta = contarCaracteres(mensajeRecibido);
                
                byte[] bufferSalida = respuesta.getBytes();

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

    public static String contarCaracteres(String texto) {
        int vocales = 0, consonantes = 0, restoCaracteres = 0;
    
        // Convertimos el texto a minúsculas para simplificar las comparaciones.
        texto = texto.toLowerCase();
    
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
    
            // Comprobamos si el carácter es una vocal.
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vocales++;
            }
            // Comprobamos si el carácter es una letra (consonante).
            else if ((c >= 'a' && c <= 'z')) {
                consonantes++;
            }
            // Si no es ni vocal ni consonante, incrementamos el resto de caracteres.
            else {
                restoCaracteres++;
            }
        }
    
        // Construimos el string de resultado y lo retornamos.
        return vocales + ":" + consonantes + ":" + restoCaracteres;
    }
}


