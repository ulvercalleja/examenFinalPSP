package PracticaExamen.Ejercicio3;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class PrimosHTTP {
    int n;
    int m;
    ServerSocket serverSocket;
    int contador;

    public PrimosHTTP(int n, int m, ServerSocket serverSocket) {
        this.n = n;
        this.m = m;
        this.serverSocket = serverSocket;
    }

    public interface ObservadorHTTP {
        void actualizar(int numero, int contador);
    }

    private List<ObservadorHTTP> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorHTTP observador){
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorHTTP observador){
        observadores.remove(observador);
    }

    public void notificarObservadores(int numeroPrimo, int contador){
        for (ObservadorHTTP observador : observadores) {
            observador.actualizar(numeroPrimo, contador);
        }
    }

    public void generarNumerosPrimos(int n, int m) {
        for (int i = 0; i < m; i++) {
            int numeroPrimo = generarNumeroPrimo(n);
            notificarObservadores(numeroPrimo,contador);
            contador++;
            n = numeroPrimo;
        }
    }

    public static int generarNumeroPrimo(int n) {
        int candidato = n + 1;
        while (!esPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }

    private static boolean esPrimo(int candidato) {
        if (candidato <= 1) {
            return false;
        }
        for (int i = 2; i * i <= candidato; i++) {
            if (candidato % i == 0) {
                return false;
            }
        }
        return true;
    }
}
