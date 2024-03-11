package TCP.MultithreadsEjercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* El ejercicio consiste en implementar un servidor TCP multihilo y 
un cliente TCP en Java. El servidor espera conexiones entrantes en el 
puerto 1234 y cuando un cliente se conecta, el servidor genera un número 
aleatorio entre 0 y 100 y lo envía al cliente. El cliente se conecta al 
servidor en localhost (127.0.0.1) en el puerto 1234, solicita al usuario 
que introduzca algo por consola, envía la cadena ingresada al servidor y 
espera la respuesta. Si el servidor recibe la cadena "salir" del cliente, 
envía la cadena "Salida" al cliente y cierra la conexión.

El servidor y el cliente utilizan streams de entrada y salida de datos 
para enviar y recibir mensajes en formato UTF-8. El servidor puede manejar 
múltiples clientes simultáneamente gracias al uso de hilos. El cliente solicita 
continuamente al usuario que introduzca algo hasta que recibe la cadena "salir" 
del servidor. Una vez que recibe la cadena "Salida" del servidor, el cliente 
cierra la conexión y termina su ejecución */

public class Server {
    private static final int MAXRAND = 100;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)) {
            while (true) {
                final Socket socket = server.accept();
                new Thread(() -> {
                    try (DataInputStream in = new DataInputStream(socket.getInputStream());
                         DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                        String cadenaRecibida = in.readUTF();
                        if (!cadenaRecibida.equalsIgnoreCase("salir")) {
                            int numeroAleatorio = (int) (Math.random() * MAXRAND); // * NO %
                            out.writeUTF(String.valueOf(numeroAleatorio));
                        } else {
                            out.writeUTF("Salida");
                            out.close();
                            socket.close();//INCLUYELOOOOOOO
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
