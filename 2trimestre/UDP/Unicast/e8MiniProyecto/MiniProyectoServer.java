package UDP.Unicast.e8MiniProyecto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class MiniProyectoServer {
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
            DatagramSocket socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0"));
            
            System.out.println("Servidor escuchando en el puerto " + puerto);
            
            Set<InetAddress> clientesConectados = new HashSet<>();

            while (true) {
                byte[] bufferRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
                socket.receive(paqueteRecepcion);
                String mensaje = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());

                InetAddress direccionCliente = paqueteRecepcion.getAddress();
                int puertoCliente = paqueteRecepcion.getPort();
                
                if (mensaje.equals("CON")) {
                    clientesConectados.add(direccionCliente);
                    String respuestaConexion = "Conexión establecida con " + direccionCliente;
                    byte[] bufferRespuesta = respuestaConexion.getBytes();
                    DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, direccionCliente, puertoCliente);
                    socket.send(paqueteRespuesta);
                } else if (mensaje.equals("DES")) {
                    clientesConectados.remove(direccionCliente);
                } else { // Mensaje de cliente
                    for (InetAddress cliente : clientesConectados) {
                        if (!cliente.equals(direccionCliente)) {
                            byte[] bufferRespuesta = mensaje.getBytes();
                            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, cliente, puertoCliente);
                            socket.send(paqueteRespuesta);
                        }
                    }
                }
            }
                
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
