package org.example;

import java.util.Random;

public class Ruleta {
    public static final int MAX_HISTORIAL = 100;

    private final int[] historialNumeros =
            new int[MAX_HISTORIAL];

    private final int[] historialApuestas =
            new int[MAX_HISTORIAL];

    private final boolean[] historialAciertos =
            new boolean[MAX_HISTORIAL];

    private int historialSize = 0;

    private int saldo;

    private final Random rng = new Random();

    private static final int[] NUMEROS_ROJOS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    public Ruleta() {
        this(0);
    }

    public Ruleta(int saldoInicial) {
        this.saldo = Math.max(0, saldoInicial);
        this.historialSize = 0;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        }
    }

    public int getHistorialSize() {
        return historialSize;
    }

    public int girarRuleta() {
        return rng.nextInt(37);
    }

    public boolean evaluarResultado(int numero, TipoApuesta tipo) {

        if (numero < 1 || numero > 36 || tipo == null) {
            return false;
        }

        switch (tipo) {
            case ROJO:
                return esRojo(numero);

            case NEGRO:
                return !esRojo(numero);

            case PAR:
                return numero % 2 == 0;

            case IMPAR:
                return numero % 2 != 0;

            default:
                return false;
        }
    }

    public boolean esRojo(int numero) {

        for (int rojo : NUMEROS_ROJOS) {
            if (rojo == numero) {
                return true;
            }
        }

        return false;
    }

    public void registrarResultado(
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

    public int calcularTotalApostado() {

        int total = 0;

        for (int i = 0; i < historialSize; i++) {
            total += historialApuestas[i];

        }

        return total;
    }

    public int calcularTotalAciertos() {

        int aciertos =0;

        for (int i = 0; i < historialSize; i++) {
            if (historialAciertos[i]) {
                aciertos++;
            }
        }

        return aciertos;
    }

    public int calcularGananciaNeta() {

        int ganancia = 0;

        for (int i = 0; i < historialSize; i++) {
            if (historialAciertos[i]) {
                ganancia += historialApuestas[i];
            } else {
                ganancia -= historialApuestas[i];
            }
        }

        return ganancia;
    }

    public double calcularPorcentajeAciertos() {

        if (historialSize == 0) {
            return 0;
        }

        return (calcularTotalAciertos() * 100.0) / historialSize;
    }
}

