package org.example;

public class SessionController {

    private Usuario usuarioActual;
    private final Ruleta ruleta;

    public SessionController() {
        this.ruleta = new Ruleta();
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getNombreUsuario() {
        if (usuarioActual == null) {
            return "Invitado";
        }

        return usuarioActual.getNombre();
    }

    public int getSaldo() {
        if (usuarioActual == null) {
            return 0;
        }

        return usuarioActual.getSaldo();
    }

    public boolean depositar(int monto) {
        if (usuarioActual == null) {
            return false;
        }

        return usuarioActual.depositar(monto);
    }

    public boolean actualizarNombre(String nombre) {
        if (usuarioActual == null ||
                nombre == null ||
                nombre.trim().isEmpty()) {
            return false;
        }

        usuarioActual.setNombre(nombre);
        return true;
    }

    public int jugarRonda(TipoApuesta tipo, int monto) {

        if (usuarioActual == null ||
                tipo == null ||
                monto <=0 ||
                monto > usuarioActual.getSaldo()) {
            throw new IllegalArgumentException(
                    "Apuesta inválida o saldo insuficiente"
            );
        }

        int numero = ruleta.girarRuleta();
        boolean acierto = ruleta.evaluarResultado(numero, tipo);

        if (acierto) {
            usuarioActual.setSaldo(usuarioActual.getSaldo() + monto);
        } else {
            usuarioActual.setSaldo(usuarioActual.getSaldo() - monto);
        }

        ruleta.registrarResultado(numero, monto, acierto);

        return numero;
    }

    public Ruleta getRuleta() {
        return ruleta;
    }
}
