package PracticaExamen.Ejercicio1;

/* Crea un programa que al ejecutarse reciba dos números enteros como parámetros. El primer entero es el puerto de escucha, el segundo es el puerto de envío.
En cada Datagrama UDP espera encontrar otros dos números y un carácter separados por espacios, el primer número representa la altura, el segundo el ancho y el carácter es el carácter utilizado a continuación. 
Habrá una clase Receptor que recibe Datagramas en el puerto de escucha, implementa el patrón Observer. Con cada datagrama avisa a los que observan con la información para generar el cuadrado y la información para enviar la respuesta.
Habrá una clase GeneradorCuadrado con un método estático que genera el cuadrado.
Habrá una clase Enviador que observa la recepción de Datagramas en Receptor, cuando llega un nuevo Datagrama usa la clase GeneradorCuadrado y la información que ha recibido para enviar por Broadcast el cuadrado.
Al recibir cada Datagrama el programa generará un cuadrado con el alto y el ancho recibidos y lo enviará en el segundo puerto a través de Broadcast UDP.
El programa estará leyendo hasta que se le envíe la cadena ‘fin’, ‘Fin’, o ‘FIN’, ‘fiN’, etc o cualquier combinación de mayúsculas y minúsculas que sean igual a ‘fin’ Envío:

Cuando lo tengas ejecuta dos comandos nc para verificar que funciona. Haz capturas de pantalla de los comandos.
Ejemplo: java Ejercicio1Receptor 4322 5678 - nc -luk 5678 - echo "3 5 a" | nc -u localhost 4322
aaaa
a  a
aaaa
 */

public class MainEjercicio1 {
    public static void main(String[] args) {
        int puertoReceptor = Integer.valueOf(args[0]);
        int puertoEnviador = Integer.valueOf(args[1]);

        Ejercicio1Receptor miReceptor = new Ejercicio1Receptor(puertoReceptor);
        Ejercicio1Enviador miEnviador = new Ejercicio1Enviador(puertoEnviador);
        
        miReceptor.añadirObservador(miEnviador);
        miReceptor.Receptor();
    }
}
