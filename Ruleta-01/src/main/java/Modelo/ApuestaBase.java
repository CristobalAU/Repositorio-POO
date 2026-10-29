package Modelo;

public abstract class ApuestaBase {

    private final int monto;
    private final String etiqueta;

    public ApuestaBase(int monto, String etiqueta) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto tiene que ser mayor a cero"
            );
        }

        this.monto = monto;
        this.etiqueta = etiqueta;
    }

    public int getMonto() {
        return monto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public abstract boolean acierta(int numero, String color);

    @Override
    public String toString() {
        return etiqueta;
    }
}
