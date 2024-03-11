package PracticaExamen.Ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/* Crea un programa que reciba como parámetro un número de puerto por el que escuchar.
El programa será un servidor monothread web.
Cuando el programa reciba una petición en la URL habrá dos parámetros. Ejemplo visito en el navegador http://localhost:8000/30/6/ Petición:
GET /30/6/ HTTP/1.1
Host: localhost:8000
El programa extraerá de la petición get los dos números, el primero será n y el segundo m.
El primer número representa el entero inicial, el segundo número representa la cantidad de primos.
El programa devolverá una página web con ul-li con los m (6 en el ejemplo) siguientes primos a n (30 en el ejemplo).
Ejemplo:
<ul>
<li>31</li> <li>37</li> <li>41</li> <li>43</li> <li>47</li>
<li>53</li> </ul>

Cada petición será atendida por una instancia de una clase PrimosHTTP que recibe en su constructor el socket ya asociado, el número inicial y la cantidad de primos. Esta clase se puede observar. Cada vez que encuentre un primo avisará a sus observadores.

Crea una clase Logger que se instancia una única vez en el main y observa a cada una de las instancias PrimosHTTP. Cada vez que se encuentre un primo, PrimosHTTP comunicará el evento, con esta informaciónla instancia de la clase Logger guarda en el fichero /var/log/primos.txt los números que se han ido encontrando.
*/
public class MainHTTP {
    private static final int RESOURCE_POSITION = 1;

    public static void main(String[] args) {
        int puerto = 8877;
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(puerto);
                Socket connCliente = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                connCliente.getInputStream()));
                String header = reader.readLine();
                System.out.println(header);
                // GET ________ HTTP/1.1
                String info = extraeInformacion(header);
                String[] numeros = info.split("/");
                int n = Integer.parseInt(numeros[1]);
                int m = Integer.parseInt(numeros[2]);
                PrimosHTTP primos = new PrimosHTTP(m, n, serverSocket);
                Logger logeado = new Logger(connCliente, m);
                primos.agregarObservador(logeado);
                primos.generarNumerosPrimos(n, m);
                serverSocket.close();
                connCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String extraeInformacion(String header) {
        return header.split(" ")[RESOURCE_POSITION];
    }
}
