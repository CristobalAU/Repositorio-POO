package Modelo;

public class ApuestaPar extends ApuestaBase {

    public ApuestaPar(int monto) {
        super(monto, "Par");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return numero != 0 && numero >= 1
                && numero <= 36 && numero % 2 == 0;
    }
}
