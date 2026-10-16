package Modelo;

public class Resultado {

    private final int numero;
    private final TipoApuesta tipoApuesta;
    private final int montoApostado;
    private final boolean gano;
    private final int ganancia;

    public Resultado(int numero,
                     TipoApuesta tipoApuesta,
                     int montoApostado,
                     boolean gano,
                     int ganancia) {

        this.numero = numero;
        this.tipoApuesta = tipoApuesta;
        this.montoApostado = montoApostado;
        this.gano = gano;
        this.ganancia = ganancia;
    }

    public int getNumero() {
        return numero;
    }

    public TipoApuesta getTipoApuesta() {
        return tipoApuesta;
    }

    public int getMontoApostado() {
        return montoApostado;
    }

    public boolean isGano() {
        return gano;
    }

    public int getGanancia() {
        return ganancia;
    }

    @Override
    public String toString() {
        return "Número: " + numero
                + " | Apuesta: " + tipoApuesta
                + " | Monto: " + montoApostado
                + " | Ganó: " + (gano ? "Sí" : "No")
                + " | Ganancia: " + ganancia;
    }
}
