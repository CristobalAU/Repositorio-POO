package Controlador;

import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;
import Modelo.ApuestaBase;

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

    public int jugarRonda(ApuestaBase apuesta) {

        if (usuarioActual == null ||
                apuesta == null ||
                apuesta.getMonto() <=0 ||
                apuesta.getMonto() > usuarioActual.getSaldo()) {

            throw new IllegalArgumentException(
                    "Apuesta inválida o saldo insuficiente"
            );
        }

        Resultado resultado = ruleta.jugar(apuesta);

        usuarioActual.setSaldo(
                usuarioActual.getSaldo() + resultado.getGanancia()
        );

        usuarioActual.agregarResultado(resultado);

        ruleta.agregarResultado(resultado);

        return resultado.getNumero();
    }

    public Ruleta getRuleta() {
        return ruleta;
    }
}
