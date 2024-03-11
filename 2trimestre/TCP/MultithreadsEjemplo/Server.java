package TCP.MultithreadsEjemplo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* El ejercicio consiste en implementar un servidor TCP multihilo y un cliente 
TCP en Java. El servidor debe escuchar en el puerto 1234 y esperar conexiones entrantes. 
Cuando un cliente se conecta, el servidor crea un nuevo hilo para manejar la comunicación 
con ese cliente. Cada hilo recibe un mensaje del cliente, lo modifica agregando su índice 
y convirtiendo el mensaje a mayúsculas, y luego lo envía de vuelta al cliente. El servidor 
puede manejar múltiples clientes simultáneamente gracias al uso de hilos.

El cliente se conecta al servidor en localhost (127.0.0.1) en el puerto 1234, envía 
el mensaje "Hola mundo de los sockets!!!" al servidor y espera la respuesta. Una vez que 
recibe la respuesta del servidor, la imprime por consola.

El servidor y el cliente utilizan streams de entrada y salida de datos para enviar y recibir 
mensajes en formato UTF-8.*/
public class Server {
    public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(1234);
			while(true) {
				// Espera cliente
				Socket socket = server.accept();
				
				new Thread(()->{
					
					try {
						DataInputStream in = new DataInputStream ( socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						
						String s = in.readUTF();
						for (int x = 0; x < 32; x++) {
							for (int i = 0; i < 1; i++) {
								out.writeUTF(x + s.toUpperCase());
							}
						}
						in.close();
						out.close();
						socket.close();
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
