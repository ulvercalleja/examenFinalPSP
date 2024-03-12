package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorHTTP {
    private static final int RESOURCE_POSITION = 1;
    private int puerto;
    private final int CIUDAD = 2;

    public ServidorHTTP(int puerto) {
        this.puerto = puerto;
    }

    public interface ObservadorServidor {
        void actualizar(String datosRecibidos, int pacificado);
    }

    private List<ObservadorServidor> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorServidor observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorServidor observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(String datosRecibidos, int pacificado) {
        for (ObservadorServidor observador : observadores) {
            observador.actualizar(datosRecibidos, pacificado);
        }
    }

    public void gestionConsultaHTTP() {
        try {
            while (true) {
                ServerSocket serverSocket = new ServerSocket(puerto);
                Socket connCliente = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                connCliente.getInputStream()));
                String header = reader.readLine();
                System.out.println(header);
                // GET ________ HTTP/1.1
                String info = extraeInformacion(header);
                String datosRecibidos[] = info.split("/");
                int pacificado = comprobarCapitalPacificada(datosRecibidos[CIUDAD]);
                notificarObservadores(datosRecibidos[CIUDAD], pacificado);
                serverSocket.close();
                OutputStream out = connCliente.getOutputStream();
                String paginaWeb = crearPaginaWeb();

                if (pacificado == 1) {
                    // Send the HTTP response header
                    String response = "HTTP/1.1 200 PACIFICADO\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + paginaWeb.length() + "\r\n\r\n";
                    out.write(response.getBytes());
                    // Send the HTML content
                    out.write(paginaWeb.getBytes());
                    out.flush();
                } else if (pacificado == 2) {
                    String response = "HTTP/1.1 200 SALVAJE\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + paginaWeb.length() + "\r\n\r\n";
                    out.write(response.getBytes());
                    // Send the HTML content
                    out.write(paginaWeb.getBytes());
                    out.flush();
                } else {
                    String response = "HTTP/1.1 404 NO ENCONTRADO\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + paginaWeb.length() + "\r\n\r\n";
                    out.write(response.getBytes());
                    // Send the HTML content
                    out.write(paginaWeb.getBytes());
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extraeInformacion(String header) {
        return header.split(" ")[RESOURCE_POSITION];
    }

    private int comprobarCapitalPacificada(String ciudad) {
        Map<String, Boolean> ciudades = new HashMap<>();
        ciudades.put("MADRID", true);
        ciudades.put("Barcelona", true);
        ciudades.put("LISBOA", true);
        ciudades.put("PARIS", true);
        ciudades.put("LONDRES", true);
        ciudades.put("NuevaYork", false);
        ciudades.put("BERLIN", true);
        ciudades.put("ROMA", true);
        ciudades.put("AMSTERDAM", true);
        ciudades.put("DUBLIN", true);
        ciudades.put("PRAGA", true);
        ciudades.put("BRUSELAS", true);
        ciudades.put("VIENA", true);
        ciudades.put("BUDAPEST", true);
        ciudades.put("ESTOCOLMO", true);
        ciudades.put("TOKIO", true);
        ciudades.put("BEIJING", true);
        ciudades.put("Sidney", false);
        ciudades.put("CIUDADDEMEXICO", true);
        ciudades.put("BUENOSAIRES", true);
        ciudades.put("Valencia", false);
        ciudades.put("Malaga", false);
        ciudades.put("Osaka", false);
        ciudades.put("Liverpool", false);
        ciudades.put("Marsella", false);
        ciudades.put("Florencia", false);
        ciudades.put("SanFrancisco", false);
        ciudades.put("Melbourne", false);
        ciudades.put("LasVegas", false);
        ciudades.put("Boston", false);
        String ciudadMayusculas = ciudad.toUpperCase();
        if (ciudades.containsKey(ciudad) || ciudades.containsKey(ciudadMayusculas)) {

            if (ciudades.get(ciudad) == null) {
                if (ciudades.get(ciudadMayusculas) != null) {
                    if (ciudades.get(ciudadMayusculas).booleanValue()) {
                        return 1;
                    } else {
                        return 2;
                    }
                } else {
                    return 3;
                }
            } else {
                if (ciudades.get(ciudad).booleanValue()) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {
            return 3;
        }
    }

    private String crearPaginaWeb() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Esta todo Bien</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("</ul>");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }
}
