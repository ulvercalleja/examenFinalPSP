package ejercicio1;

import ejercicio1.ServidorHTTP.ObservadorServidor;

public class ObservadorHTTP implements ObservadorServidor {

    @Override
    public void actualizar(String datos, int pacificado) {
        switch (pacificado) {
            case 1:
                System.out.println(datos + " ha sido pacificado");
                break;
            case 2:
                System.out.println(datos + " no ha sido pacificado");
                break;
            case 3:
                System.out.println(datos + " no ha sido encontrado");
                break;

            default:
                break;
        }
    }

}

