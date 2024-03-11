package TCP.Ejercicio3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String direccionServidor = "localhost";
        int puerto = 1234;
        
        try {
            Socket socket = new Socket(direccionServidor, puerto);
            System.out.println("Conectado al servidor.");            
            String rutaArchivoDestino = "./TCP/Ejercicios/Ejercicio3/blocNotasRecibido.pdf";            
            InputStream in = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(rutaArchivoDestino);
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            while ((bytesLeidos = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }
            
            // Cerrar recursos
            fos.close();
            in.close();
            socket.close();
            System.out.println("Archivo recibido y guardado en: " + rutaArchivoDestino);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
