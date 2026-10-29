package Controlador;

import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;
import Modelo.ApuestaBase;
import Modelo.PersistenciaHistorial;
import Modelo.PersistenciaSaldo;
import java.io.IOException;

public class SessionController {

    private Usuario usuarioActual;
    private final Ruleta ruleta;

    public SessionController() {
        this.ruleta = new Ruleta();
    }

    public void iniciarSesion(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "El usuario no puede ser nulo"
            );
        }

        try {
            usuario.cargarHistorial(
                    PersistenciaHistorial.cargar(usuario.getUsername())
            );

            ruleta.cargarResultados(usuario.getHistorial());

            usuario.setSaldo(
                    PersistenciaSaldo.cargar(usuario.getUsername())
            );

            this.usuarioActual = usuario;

        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException(
                    "No se pudieron cargar los datos del usuario",
                    e
            );
        }
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

        if (usuarioActual == null || monto <= 0) {
            return false;
        }

        boolean depositoExitoso = usuarioActual.depositar(monto);

        if (depositoExitoso) {
            try {
                PersistenciaSaldo.guardar(
                        usuarioActual.getUsername(),
                        usuarioActual.getSaldo()
                );
            } catch (IOException e) {
                throw new IllegalStateException(
                        "El depósito se realizó, pero no se pudo guardar el saldo.",
                        e
                );
            }
        }

        return depositoExitoso;
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

        try {
            PersistenciaHistorial.guardar(
                    usuarioActual.getUsername(),
                    usuarioActual.getHistorial()
            );

            PersistenciaSaldo.guardar(
                    usuarioActual.getUsername(),
                    usuarioActual.getSaldo()
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "La partida se realizó con éxito, pero no se pudieron guardar los datos.",
                    e
            );
        }

        return resultado.getNumero();
    }

    public Ruleta getRuleta() {
        return ruleta;
    }
}
