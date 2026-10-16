package Controlador;

import Modelo.Ruleta;
import Modelo.TipoApuesta;

public class RuletaController {

    private final Ruleta ruleta;
    private final SessionController session;

    public RuletaController(Ruleta ruleta, SessionController session) {
        this.ruleta = ruleta;
        this.session = session;
    }

    public int jugarRonda(TipoApuesta tipo, int monto) {
        return session.jugarRonda(tipo, monto);
    }

    public boolean evaluarResultado(int numero, TipoApuesta tipo) {
        return ruleta.evaluarResultado(numero, tipo);
    }

    public boolean esRojo(int numero) {
        return ruleta.esRojo(numero);
    }

    public int getTotalApostado() {
        return ruleta.calcularTotalApostado();
    }

    public int getTotalAciertos() {
        return ruleta.calcularTotalAciertos();
    }

    public int getGananciaNeta() {
        return ruleta.calcularGananciaNeta();
    }

    public double getPorcentajeAciertos() {
        return ruleta.calcularPorcentajeAciertos();
    }

    public int getRondasJugadas() {
        return ruleta.getHistorialSize();
    }

}
