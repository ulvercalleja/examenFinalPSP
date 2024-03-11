package primertrimestre.examen1.ejercicio4;

public class Ejercicio4 implements Runnable{
    private static final long MIN_TIEMPO_DESCANSO = 500;
    private static final long MAX_TIEMPO_DESCANSO = 1000;
    private static final int MIN_PROB_FALLO = 0;
    private static final int MAX_PROB_FALLO = 10;
    private static final int EXPLOSION = 1;

    boolean todoCorrecto = true;
    static int cohetesFallidos;
    int kmRecorrido;
    int kmTotales;
    int idCohete;
    Object salida;

    public Ejercicio4(int kmTotales, int idCohete, Object salida) {
        kmRecorrido = 0;
        this.kmTotales = kmTotales;
        this.idCohete = idCohete;
        this.salida = salida;
    }

    @Override
    public void run() {
        synchronized(salida){
            try {
                salida.wait();
            } catch (Exception e) {
                System.out.println("ERROR esperando: " + e.getMessage());
            }
        }
        
        System.out.println(String.format("¡¡Soy el cohete %d inicio mi carrera!!", idCohete));

        while (kmRecorrido < kmTotales && todoCorrecto) {
            try {
                System.out.println(String.format("¡¡Soy el cohete %d he recorrido %d / %d!!", idCohete, kmRecorrido, kmTotales));
                int probFallo = (int) (Math.random()*10 + 1); //Calculo probabilidad de fallo

                if (probFallo <= EXPLOSION) {
                    todoCorrecto = false;
                    System.out.println(String.format("¡¡Soy el cohete %d y... ¡¡He explotado!!", idCohete));
                    cohetesFallidos++;
                }

                int tiempoDeDescanso = (int) (Math.random() * (MAX_TIEMPO_DESCANSO - MIN_TIEMPO_DESCANSO + 1) + MIN_TIEMPO_DESCANSO);
                Thread.sleep(tiempoDeDescanso);
                System.out.println(String.format("¡¡Soy el cohete %d he dormido %d ms!!", idCohete, tiempoDeDescanso));
                
                kmRecorrido += 50;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (todoCorrecto) {
            System.out.println(String.format("Soy el cohete %d HE LLEGADO A MI DESTINO!!", idCohete));
        }
    }

    public static int getCohetesFallidos() {
        return cohetesFallidos;
    }
        
}
