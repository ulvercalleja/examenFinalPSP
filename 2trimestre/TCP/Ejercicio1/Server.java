package TCP.Ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* Mensaje que se envia al server, el server devuelve el nº de caracteres y nº de 
consonantes que tenia la palabra de vuelta al cliente */

public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        try {
            server = new ServerSocket(1234);
            while (true) {

                Socket socket = server.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String s = in.readUTF();
                System.out.println(s);
                int contadorVocales=0;
                int contadorConsonantes=0;
                for(int i=0;i<s.length();i++){
                    char caracter=s.charAt(i);
                    if(caracter=='a' || caracter=='e' || caracter=='i' || caracter=='o'|| caracter=='u'){
                        contadorVocales++;
                    }else if(Character.isLetter(caracter)){
                        contadorConsonantes++;
                    }
                }
                out.write(contadorConsonantes);
                out.write(contadorVocales);
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
