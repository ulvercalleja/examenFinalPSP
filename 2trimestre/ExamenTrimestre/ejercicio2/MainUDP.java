package ejercicio2;

public class MainUDP {
    public static void main(String[] args) {
        int puertoReceptor = Integer.parseInt(args[0]);
        int puertoEnviador = Integer.parseInt(args[1]);
        ServidorUDP server = new ServidorUDP(puertoEnviador);
        ActualizadorUDP enviador = new ActualizadorUDP(puertoReceptor);
        server.agregarObservador(enviador);
        server.ReceptorDatagramas();
    }
}

