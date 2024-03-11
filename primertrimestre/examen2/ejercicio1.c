/* Enunciado: Hay dos procesos, uno hijo y otro padre. El padre enviará numeros 
aleatorios al hijo por un pipe hasta que el hijo diga que pare a través de una señal.
El hijo contará los números que sean primos y le enviará, por otro pipe, la suma al padre.

El padre hará lo siguiente:
    - Estará enviando números aleatorios entre el 100 y el 999 hasta que reciba la señal SIGUSR1.
    - Cuando reciba la señal, leerá por un pipe de lectura con el hijo un número y lo imprimirá por pantalla.
    - Luego espera que el hijo finalice.

El hijo hará lo siguiente: 
    - Leerá por el pipe números y los imprimirá por pantalla.
    - De estos números, sumará aquellos que sean primos.
    - Leerá números hasta que encuentre algún número múltiplo de 10.
    - Cuando reciba un número múltiplo de 10, parará la cuenta. Enviará una señal al padre y escribirá en el pipe
      la suma que ha calculado, luego finalizará.*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <stdbool.h>

#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define MAX_NUM 999
#define MIN_NUM 100
#define LECTOR 0
#define ESCRITOR 1

bool senalRecibida = false;

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

int esMultiplo10(int num) {
    if (num % 10 == 0) {
        return 1;
    } else {
        return 0;
    }
}

void sigusr1_handler(int signum) {
    senalRecibida = true;
    printf("He recibido la señal \n");
}

int main() {

    int pipe1[2];
    int pipe2[2];
    pid_t hijo;
    int num_generado;
    int sumaPrimos;

    if (pipe(pipe1) == -1 || pipe(pipe2) == -1) {
        perror("Error al crear el pipe");
        exit(EXIT_FAILURE);
    }

    hijo = fork();

    if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
    } else if (hijo == NUM_HIJO) { // Proceso hijo
        
        close(pipe1[ESCRITOR]);
        close(pipe2[LECTOR]);

        while (read(pipe1[LECTOR], &num_generado,sizeof(num_generado))) { // mientras pueda seguir leyendo
            sleep(1);
            printf("Soy hijo y el numero recibido es: %d !!!\n", num_generado);

            if (esPrimo(num_generado)) {
                sumaPrimos ++;
            } else if (esMultiplo10(num_generado)){
                kill(getppid(), SIGUSR1); // Enviar señal SIGUSR1 al padre
                write(pipe2[ESCRITOR], &sumaPrimos, sizeof(sumaPrimos));
                exit(EXIT_SUCCESS);
            }
        }

        close(pipe1[LECTOR]);
        close(pipe2[ESCRITOR]);
    } else { // Proceso padre
        close(pipe1[LECTOR]);
        close(pipe2[ESCRITOR]);

        signal(SIGUSR1, sigusr1_handler); // Ignorar la señal SIGUSR1

        srand(time(NULL));
        while (!senalRecibida) {
            sleep(1);
            // Generar y mostrar un número aleatorio en el rango especificado
            num_generado = rand() % (MAX_NUM - MIN_NUM + 1) + MIN_NUM;
            printf("Soy padre y he generado: %d !!!\n", num_generado);
            write(pipe1[ESCRITOR], &num_generado, sizeof(num_generado));
        }

        int status;
        
        waitpid(hijo, &status, 0);

        read(pipe2[LECTOR], &sumaPrimos, sizeof(sumaPrimos));
        printf("Soy padre y la suma de primos es: %d !!!\n", sumaPrimos);
        close(pipe1[ESCRITOR]); // Cerrar extremo de escritura del pipe
        close(pipe2[LECTOR]);
    }
    return 0;
}