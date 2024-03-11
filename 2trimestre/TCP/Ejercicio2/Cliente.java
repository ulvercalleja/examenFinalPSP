package TCP.Ejercicio2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String cadena;
            in.available();
                while ((cadena = in.readUTF()) != null) {
                    System.out.println(cadena);
                }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
