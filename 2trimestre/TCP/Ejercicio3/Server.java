package TCP.Ejercicio3;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* Implementar un cliente y un servidor TCP en Java para transferir un archivo. 
El servidor debe escuchar en el puerto 1234 y esperar conexiones entrantes. Una 
vez que se establece la conexión con un cliente, el servidor envía un archivo llamado 
"blocNotas.pdf" ubicado en el directorio "./TCP/Ejercicios/Ejercicio3/" al cliente. 
El cliente se conecta al servidor en localhost (127.0.0.1) en el puerto 1234, recibe 
el archivo y lo guarda como "blocNotasRecibido.pdf" en el directorio "./TCP/Ejercicios/Ejercicio3/".*/

public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        try {
            server = new ServerSocket(1234);
            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado.");                
                String rutaDocumento = "./TCP/Ejercicios/Ejercicio3/blocNotas.pdf";                
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());                
                FileInputStream fis = new FileInputStream(rutaDocumento);
                byte[] buffer = new byte[4096];
                int bytesLeidos;
                while ((bytesLeidos = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }
                
                // Cerrar recursos
                fis.close();
                out.close();
                socket.close();
                System.out.println("Archivo enviado al cliente y conexión cerrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
