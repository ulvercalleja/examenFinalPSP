package com.example;

import java.io.IOException;

import com.example.LectorParaSpam.ObservadorMensajes;

/* Crea un programa que reciba como parámetro dos rutas y dos cadenas de texto
La primera ruta es la ruta de un fichero con mensajes
La segunda ruta es la ruta de un fichero con direcciones de correo
Las dos últimas cadenas representan el usuario de envío smtp y la contraseña. Ejemplo de ejecución
java examen.E2 data/mensajes.txt data/dirs.txt jorge.duenas@educa.org abc1234
El programa enviará el mensaje de la línea 1 del fichero mensajes a la dirección 1 del fichero dirs, , desde la cuenta jorge.duenas@educa.org y password abc1234.
Luego lo mismo con la línea dos, línea tres, etc. 
Crea una clase LectorParaSpam que reciba en el constructor las dos rutas. El programa leerá la información de los dos ficheros en el método comenzarLectura. Esta clase implementa el patrón Observer. Al leer cada línea avisará a sus observadores con el correo y el mensaje (Produce un aviso por cada pareja email-mensaje).
Crea una clase EnviadorSpam, en el constructor recibe la cuenta que envía el correo y la contraseña. También recibe en el constructor un LectorParaSpam, observará la información que le pasa el LectorParaSpam en sus notificaciones, cada vez le notificará una dirección de destino y un mensaje.
Ten en cuenta que antes de llamar al método comenzarLectura de LectorParaSpam los observadores tienen que estar creados y observando.
*/
public class Main {
    public static void main(String[] args) throws IOException {

        String rutaMensajes = "/home/ulver/Desktop/examen2PSP/PracticaExamen/Ejercicio2/ejercicio2/src/main/java/com/example/mensajes.txt";
        String rutaCorreos = "/home/ulver/Desktop/examen2PSP/PracticaExamen/Ejercicio2/ejercicio2/src/main/java/com/example/correos.txt";
        String correo = "ulver.calleja";
        String password = "HAYQUECAMBIARESTO";
        LectorParaSpam lector1 = new LectorParaSpam(rutaMensajes, rutaCorreos);
        EnviadorSpam enviador1 = new EnviadorSpam(correo, password, lector1);
        // EnviadorSpam enviador2 = new EnviadorSpam(correo, password, lector1);

        lector1.añadirObservador(enviador1);

        lector1.comenzarLectura();
    }
}
