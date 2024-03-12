package primertrimestre.filosofos;

public class Mesa {
     
    private boolean[] tenedores;
     
    public Mesa(int numTenedores){
        this.tenedores = new boolean[numTenedores];
    }

    /*Cuando un filósofo quiera coger el tenedor de la izquierda y de la derecha,
    tendremos que decirle cual es la posición concretamente (mirar la imagen de arriba). */

    public int tenedorIzquierda(int i){ //El tenedor de su izquierda, coincide con el índice del filósofo.
        return i;                         
    }

    public int tenedorDerecha(int i){ // El tenedor de su derecha, es una posición menos del índice del filósofo. En el caso del 0, obtendremos el ultimo índice del array.
        if(i == 0){
            return this.tenedores.length - 1;
        } else {
            return i - 1;
        }
    }

    /* Tenemos que parar el proceso y empezar a comer, es hora de coger tenedores. Comprobaremos si el tenedor de la izquierda 
    o derecha están ocupados, de ser así, esperaremos con wait(). Sino están ocupados, pondremos a true las dos posiciones del array. 
    Para dejar los tenedores, pondremos a false los tenedores y notificaremos a los provesos parados.*/

    public synchronized void cogerTenedores(int comensal){
         
        while(tenedores[tenedorIzquierda(comensal)] || tenedores[tenedorDerecha(comensal)]){
            try {   
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        tenedores[tenedorIzquierda(comensal)] = true;
        tenedores[tenedorDerecha(comensal)] = true;
    }
     
    public synchronized void dejarTenedores(int comensal){
        tenedores[tenedorIzquierda(comensal)] = false;
        tenedores[tenedorDerecha(comensal)] = false;
        notifyAll();
    }
}
