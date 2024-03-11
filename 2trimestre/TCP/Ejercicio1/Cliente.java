package TCP.Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 1234);
            DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream  out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("hola mundoñññ!!\n");
            int contadorConsonantes=in.read();
            int contadorVocales=in.read();
            System.out.println("Contador de Consonantes Recibido: "+contadorConsonantes);
            System.out.println("Contador de Vocales Recibido: "+contadorVocales);
            out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
