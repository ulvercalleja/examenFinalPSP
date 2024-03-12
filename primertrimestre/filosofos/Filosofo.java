package primertrimestre.filosofos;

public class Filosofo extends Thread {
    private Mesa mesa;
    private int comensal; //número del filósofo
    private int indiceComensal;
     
    public Filosofo(Mesa m, int comensal){
        this.mesa = m;
        this.comensal = comensal;
        this.indiceComensal = comensal - 1;
    }

    // necesitamos crear un método run, donde realizaremos la vida del filósofo.

    public void run(){
         
        while(true){
            this.pensando();
            this.mesa.cogerTenedores(this.indiceComensal);
            this.comiendo();
            System.out.println("Filosofo " + comensal +  " deja de comer, tenedores libres " + (this.mesa.tenedorIzquierda(this.indiceComensal) + 1) + ", " + (this.mesa.tenedorDerecha(this.indiceComensal) + 1) );
            this.mesa.dejarTenedores(this.indiceComensal);
        }
    }

    // los filósofos comen y piensan, que simplemente lo representaremos como una espera de x segundos.

    public void pensando(){
        
        System.out.println("Filosofo " + comensal + " esta pensando");
        try {
            sleep((long) (Math.random() * 4000));
        } catch (InterruptedException ex) { }
         
    }
     
    public void comiendo(){
        System.out.println("Filosofo " + comensal + " esta comiendo");
        try {
            sleep((long) (Math.random() * 4000));
        } catch (InterruptedException ex) { }
    }
}
