package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ruleta {

    private final List<Resultado> resultados = new ArrayList<>();

    private final Random rng = new Random();

    private static final int[] NUMEROS_ROJOS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

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

    public void agregarResultado(Resultado resultado) {

        if (resultado != null) {
            resultados.add(resultado);
        }
    }

    public List<Resultado> getResultados() {
        return new ArrayList<>(resultados);
    }

    public int getHistorialSize() {
        return resultados.size();
    }

    public int calcularTotalApostado() {

        int total = 0;

        for (Resultado resultado : resultados) {
            total += resultado.getMontoApostado();
        }

        return total;
    }

    public int calcularTotalAciertos() {

        int aciertos =0;

        for (Resultado resultado : resultados) {
            if (resultado.isGano()) {
                aciertos++;
            }
        }

        return aciertos;
    }

    public int calcularGananciaNeta() {

        int ganancia = 0;

        for (Resultado resultado : resultados) {
            ganancia += resultado.getGanancia();
        }

        return ganancia;
    }

    public double calcularPorcentajeAciertos() {

        if (resultados.isEmpty()) {
            return 0.0;
        }

        return (calcularTotalAciertos() * 100.0) / resultados.size();
    }
}

