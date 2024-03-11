package primertrimestre.examen1.ejercicio4;

import primertrimestre.examen1.ejercicio4.Ejercicio4;

/* La estacion espacial internacional se encuentra a 408km. Se requiere hacer una misión para enviar provisiones,
   el contrato lo ha conseguido la Agencia Espacial de Lepe (de aquí en adelante AEL).

   La AEL ha conseguido unos propulsores muy baratos pero que en cada propulsión tiene un 10% de posibilidades de fallar
   (Consigue un número aleatorio entre 0 y 1, verifíca si número es mejor que 0.1.)

   Los cohetes avanzan 0 kilometros y duermen un tiempo aleatorio entre 500 y 1000ms. En cada avance hay posibilidad de explosión.
   Si un cohete llega a la estación o explota deja de avanzar.
   
   Para el control de la misión se ha creado el centro de control en la localidad de Islantilla a las afueras de Lepe. En este centro
   de control se contarán los cohetes que han explotado y los que han llegado. Varios cohetes pueden llegar a la vez o explotar a la vez
   con lo que tendrá que estar sincronizando.
   
   El programa de simulación recibe como parámetro el número de cohetes a enviar, genera un array de cohetes y los lanza. Al finalizar el
   programa escribe cuántos cohetes han llegado, cuántos han fracasado. Escribirá si la misión ha sido un éxito o ha fracasado. La misión
   es un éxito si al menos 3 cohetes llegan.
*/

public class MainEjercicio4 {
    private static final int TOTAL_CARRERA = 408;
    private static final Integer EXITO = 3;
    private static String FRASE_CORRECTO = "La mision ha sido un exito";
    private static String FRASE_FALLO = "La mision ha sido un fracaso";
    public static void main(String[] args) {

        int numCohetes = Integer.parseInt(args[0]);

        Object salida = new Object();

        Thread [] cohetes = new Thread [numCohetes];

        System.out.println("¡¡ESTO COMIENZA YA!!");

        for (int i = 0; i < numCohetes; i++) {
            cohetes[i] = new Thread(new Ejercicio4(TOTAL_CARRERA, (int) (Math.random()*5000), salida));
        }

        for (int i = 0; i < numCohetes; i++) {
            cohetes[i].start();
        }

        synchronized(salida){
            salida.notifyAll();
            System.out.println("¡¡PUUUUUM!! *disparo*");
        }

        for (int i = 0; i < numCohetes; i++) {
            try {
                cohetes[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        int cohetesBien = numCohetes - Ejercicio4.getCohetesFallidos();
        // miramos como ha ido la mision
        if (cohetesBien >= EXITO) {
            System.out.println(FRASE_CORRECTO);
            System.out.printf("Han llegado %d cohetes de %d\n", cohetesBien, numCohetes);
        } else {
            System.out.println(FRASE_FALLO);
            System.out.printf("Han explotado %d cohetes de %d\n", Ejercicio4.getCohetesFallidos(),numCohetes);

        }

    }
}
