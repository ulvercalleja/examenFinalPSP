
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class UDPServer {

    private final int port;

    public UDPServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String received = new String(request.getData(), 0, request.getLength());
                // Suponiendo que 'received' es tu String de entrada y quieres generar 10
                // contraseñas
                List<String> passwords = PasswordUtils.generatePasswords(received, 10);

                // Formatear las contraseñas para imprimir una por línea
                StringBuilder responseBuilder = new StringBuilder();
                for (String password : passwords) {
                    responseBuilder.append(password).append(",\n"); // Añade una contraseña y un salto de línea
                }

                byte[] response = responseBuilder.toString().getBytes();
                DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(),
                        request.getPort());
                socket.send(reply);
            }
        }
    }
}
