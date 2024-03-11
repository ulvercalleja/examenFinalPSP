package TCP.MultithreadsEjercicio1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            while (true) {
                Socket socket = new Socket("127.0.0.1", 1234);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("introduce algo");
                Scanner in = new Scanner(System.in);
                String cadena = in.nextLine();
                out.writeUTF(cadena);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String lector=reader.readLine();
                System.out.println(lector);
                if (!lector.equals(null) && lector.equalsIgnoreCase("salida")) {
                    out.close();
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
