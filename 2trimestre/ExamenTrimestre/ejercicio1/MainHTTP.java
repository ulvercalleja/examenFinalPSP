package ejercicio1;

/* HTTP y Observer. Servido HTTP monothread.
Tienes una estructura de datos con ciudades, capitales en mayúsculas y una información de si han
sido pacificadas o no.
Implementa un servicio para consultar si han sido pacificadas, el puerto de escucha se recibe por
parámetro.
/consulta/Madrid/
/consulta/Albacete/
/consulta/Boston/
Cada vez que se haga una consulta el sistema responderá con HTTP indicando el estado de esa
ciudad. Pueden suceder 3 cosas:
• 200 - Pacificada
• 200 - Salvaje
• 404 - No encontrado
Este servidor implementa un observer. Este observer notificará a sus observadores cuando se
consulte una ciudad que es capital y que sí ha sido pacificada. Este observer escribirá por la
consola “Ha sido consultada una capital pacificada: Nombredecapital” */

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

