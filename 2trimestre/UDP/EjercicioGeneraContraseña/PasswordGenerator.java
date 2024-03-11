
import java.io.IOException;

public class PasswordGenerator {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java PasswordGenerator <puerto>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        UDPServer server = new UDPServer(port);
        try {
            server.startServer();
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
