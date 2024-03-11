/* Enunciado: Tenemos N procesos hijos creados con fork. Cada proceso busca el siguiente primo.
   Crea un programa que recibe por parámetro el números de procesos hijos que creará. Cada hijo 
   comenzará el proceso de una cadena, el hijo 1 en 100, el hijo 2 en 200, el hijo 3 en 300...
   La tarea de cada hijo es buscar el siguiente número primo. El hijo 1 buscará el primer primo a
   partir del 100, el 2 a partir del 200, etc...
   
   Cuando el proceso hijo encuentre el primero primo, parará y escribirá en su estado cuántos números
   ha recorrido hasta encontrar el primer primo.
   
   Ejemplo:
   El hijo dos comienza, el número es 200, 
   el primer primo es el 211, 
   el hijo escribirá en su estado 11
   
   El padre esperará a que terminen sus hijos, recogerá su estado y hará la suma. Despúes escribirá por pantalla el
   resultado. */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <signal.h>
#include <sys/wait.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1

// Función que verifica si un número es primo o no
int esPrimo(int num) {
    if (num < 2) {
        return 0; // No es primo
    }

    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) {
            return 0; // No es primo
        }
    }

    return 1; // Es primo
}

int encontraPrimo(int inicio) {
    while (1) {
        if (esPrimo(inicio)) return inicio;
        inicio ++;
    }
}

int main(int argc, char *argv[]) {

    int numHijos = atoi(argv[1]); //  Leer numero introducido por parametros
    int n = 100;
    int sumaTotal = 0;

    if (numHijos <= MIN_HIJOS) {
        printf("El número de hijos debe ser un entero positivo.\n");
        return 1;
    }

    for (int i = 1; i <= numHijos; i++) { // Crear hijos
        pid_t hijo = fork();
        
        if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        } else if (hijo == NUM_HIJO) { // Proceso hijo
            n = (n * i);
            int primo = encontraPrimo(n);
            printf("El hijo %d comienza en el número %d \n", i, n);
            printf("el primer primo es el %d \n", primo);

            int numerosRecorridos = primo - n;
            printf("el hijo escribirá en su estado %d \n", numerosRecorridos);

            exit(numerosRecorridos);
        }
    }

    // Proceso padre
    for (int i = 0; i < numHijos; i++) {
        int estado;
        wait(&estado);
        //El padre espera a que el hijo termine, y cada hijo que termina guarda el estado de exit
        sumaTotal += WEXITSTATUS(estado);
    }

    printf("La suma de los estados de los hijos es: %d\n", sumaTotal);

    return 0;
}