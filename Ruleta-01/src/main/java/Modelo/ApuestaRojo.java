package Modelo;

public class ApuestaRojo extends ApuestaBase {

    public ApuestaRojo(int monto) {
        super(monto, "Rojo");
    }

    @Override
    public boolean acierta(int numero, String color) {
        return numero != 0 && color.equalsIgnoreCase("Rojo");
    }
}
