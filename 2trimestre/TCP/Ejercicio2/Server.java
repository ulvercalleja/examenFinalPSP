package TCP.Ejercicio2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/* Implementar un servidor TCP y un cliente TCP en Java. El servidor debe 
escuchar en el puerto 1234 y cuando reciba una conexión de un cliente, ejecutar 
el comando "ls /var" en el sistema operativo. Luego, el servidor enviará al cliente
 la salida del comando línea por línea. Por otro lado, el cliente se conectará al 
 servidor en localhost (127.0.0.1) en el puerto 1234, recibirá la salida del comando 
 del servidor y la mostrará por consola.*/
 
public class Server {
     public static void main(String[] args) {
        ServerSocket server;
        try {
            server = new ServerSocket(1234);
            while (true) {
                Socket socket = server.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String[] comandos = { "ls", "/var" };
                ProcessBuilder processBuilder = new ProcessBuilder(comandos);
                Process proceso = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                    out.writeUTF(linea);
                }
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
