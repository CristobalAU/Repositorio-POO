package org.example;

import java.util.Collections;
import java.util.List;

public class ResultadoController {

    private final SessionController session;

    public ResultadoController(SessionController session) {
        this.session = session;
    }

    public List<Resultado> obtenerHistorial() {

        Usuario usuario = session.getUsuarioActual();

        if (usuario == null) {
            return Collections.emptyList();
        }

        return usuario.getHistorial();
    }

    public int getRondasJugadas() {
        return obtenerHistorial().size();
    }

    public int getTotalApostado() {

        int total = 0;

        for (Resultado resultado : obtenerHistorial()) {
            total += resultado.getMontoApostado();
        }

        return total;
    }

    public int getTotalAciertos() {

        int aciertos = 0;

        for (Resultado resultado : obtenerHistorial()) {
            if (resultado.isGano()) {
                aciertos++;
            }
        }

        return aciertos;
    }

    public int getGananciaNeta() {

        int ganancia = 0;

        for (Resultado resultado : obtenerHistorial()) {
            ganancia += resultado.getGanancia();
        }

        return ganancia;
    }

    public double getPorcentajeAciertos() {

        int rondas = getRondasJugadas();

        if (rondas == 0) {
            return 0.0;
        }

        return (getTotalAciertos() * 100.0) / rondas;
    }
}
