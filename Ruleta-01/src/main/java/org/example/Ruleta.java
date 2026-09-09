package org.example;

import java.util.Random;
import java.util.Scanner;

public class Ruleta {
    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;
    public static Random rng = new Random();

    public static int[] numerosRojos = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion(in);
            ejecutarOpcion(opcion, in);
        } while (opcion !=3);

        in.close();
    }

    public static void mostrarMenu() {
        System.out.println("==== CASINO BLACK CAT ====");
        System.out.println("1. Iniciar Ronda");
        System.out.println("2. Ver estadística");
        System.out.println("3. Salir");
    }

    public static int leerOpcion(Scanner in) {
        if (in.hasNextInt()) {
            int opcion = in.nextInt();
            in.nextLine();
            return opcion;
        } else {
            in.nextLine();
            return -1;
        }

    }

    public static void ejecutarOpcion(int opcion, Scanner in) {

        switch (opcion) {

            case 1:
                iniciarRonda(in);
                break;

            case 2:
                mostrarEstadisticas();
                break;

            case 3:
                System.out.println("Saliendo...");
                break;

            default:
                System.out.println(
                        "La opcion que selecciono no es valida. " +
                                "Intente nuevamente."
                );
                break;
        }
    }


    public static void iniciarRonda(Scanner in) {

        System.out.println();
        System.out.println("===== NUEVA RONDA =====");

        char tipo = leerTipoApuesta(in);

        int monto = leerMonto(in);

        int numero = girarRuleta();

        boolean acierto = evaluarResultado(numero, tipo);

        registrarResultado(numero, monto, acierto);

        mostrarResultado(numero, tipo, monto, acierto);
    }


    public static char leerTipoApuesta(Scanner in) {

        while (true) {

            System.out.println();
            System.out.println("Seleccione su tipo de apuesta:");
            System.out.println("R = Rojo");
            System.out.println("N = Negro");
            System.out.println("P = Par");
            System.out.println("I = Impar");
            System.out.print("Ingrese su apuesta: ");

            String entrada = in.nextLine().toUpperCase();

            if (entrada.length() == 1) {

                char tipo = entrada.charAt(0);

                if (tipo == 'R' ||
                        tipo == 'N' ||
                        tipo == 'P' ||
                        tipo == 'I') {

                    return tipo;
                }
            }

            System.out.println(
                    "Apuesta invalida. " +
                            "Debe ingresar R, N, P o I."
            );
        }
    }


    public static int leerMonto(Scanner in) {

        while (true) {

            System.out.print("Ingrese el monto a apostar: ");

            if (in.hasNextInt()) {

                int monto = in.nextInt();

                in.nextLine();

                if (monto > 0) {

                    return monto;
                }

            } else {

                in.nextLine();
            }

            System.out.println(
                    "Monto invalido. " +
                            "Debe ingresar un numero mayor que 0."
            );
        }
    }


    public static int girarRuleta() {

        return rng.nextInt(37);
    }


    public static boolean evaluarResultado(
            int numero,
            char tipo) {

        switch (tipo) {

            case 'R':

                return numero != 0 &&
                        esRojo(numero);

            case 'N':

                return numero != 0 &&
                        !esRojo(numero);

            case 'P':

                return numero != 0 &&
                        numero % 2 == 0;

            case 'I':

                return numero != 0 &&
                        numero % 2 != 0;

            default:

                return false;
        }
    }


    public static boolean esRojo(int n) {

        for (int numeroRojo : numerosRojos) {

            if (numeroRojo == n) {

                return true;
            }
        }

        return false;
    }


    public static void registrarResultado(
            int numero,
            int apuesta,
            boolean acierto) {

        if (historialSize < MAX_HISTORIAL) {

            historialNumeros[historialSize] = numero;

            historialApuestas[historialSize] = apuesta;

            historialAciertos[historialSize] = acierto;

            historialSize++;
        }
    }


    public static void mostrarResultado(
            int numero,
            char tipo,
            int monto,
            boolean acierto) {

        String color;

        if (numero == 0) {

            color = "Verde";

        } else if (esRojo(numero)) {

            color = "Rojo";

        } else {

            color = "Negro";
        }


        System.out.println();
        System.out.println("===== RESULTADO =====");

        System.out.println("Numero obtenido: " + numero);

        System.out.println("Color: " + color);

        System.out.println("Tipo de apuesta: " + tipo);

        System.out.println("Monto apostado: $" + monto);


        if (acierto) {

            System.out.println("RESULTADO: ¡GANASTE!");

        } else {

            System.out.println("RESULTADO: PERDISTE.");
        }

        System.out.println("=====================");
    }


    public static void mostrarEstadisticas() {

        int totalApostado = 0;

        int totalAciertos = 0;

        int gananciaNeta = 0;


        for (int i = 0; i < historialSize; i++) {

            totalApostado =
                    totalApostado + historialApuestas[i];


            if (historialAciertos[i]) {

                totalAciertos++;

                gananciaNeta =
                        gananciaNeta + historialApuestas[i];

            } else {

                gananciaNeta =
                        gananciaNeta - historialApuestas[i];
            }
        }


        double porcentajeAciertos = 0;


        if (historialSize > 0) {

            porcentajeAciertos =
                    (totalAciertos * 100.0) / historialSize;
        }


        System.out.println();
        System.out.println("===== ESTADISTICAS =====");

        System.out.println(
                "Rondas jugadas: " + historialSize
        );

        System.out.println(
                "Monto total apostado: $" + totalApostado
        );

        System.out.println(
                "Total de aciertos: " + totalAciertos
        );

        System.out.printf(
                "Porcentaje de aciertos: %.2f%%%n",
                porcentajeAciertos
        );

        System.out.println(
                "Ganancia/perdida neta: $" + gananciaNeta
        );

        System.out.println("========================");
    }
}

