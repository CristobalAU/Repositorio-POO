package Vista;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.Estadisticas;
import Modelo.TipoApuesta;

import javax.swing.*;

public class VentanaMenu {

    private final SessionController controller;

    private final JFrame frame =
            new JFrame("Menú - Casino Black Cat");

    private final JLabel lblBienvenida = new JLabel();
    private final JLabel lblSaldo = new JLabel();

    private final JButton btnJugar =
            new JButton("Jugar Ruleta");

    private final JButton btnPerfil =
            new JButton("Ver Perfil");

    private final JButton btnHistorial =
            new JButton("Ver Historial");

    private final JButton btnEstadisticas =
            new JButton("Ver Estadísticas");

    private final JButton btnCerrarSesion =
            new JButton("Cerrar Sesión");

    public VentanaMenu(SessionController controller) {

        this.controller = controller;

        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblBienvenida.setBounds(50, 30, 350, 30);
        lblSaldo.setBounds(50, 65, 350, 30);

        btnJugar.setBounds(110, 115, 230, 35);
        btnPerfil.setBounds(110, 165, 230, 35);
        btnHistorial.setBounds(110, 215, 230, 35);
        btnEstadisticas.setBounds(110, 265, 230, 35);
        btnCerrarSesion.setBounds(110, 315, 230, 35);

        frame.add(lblBienvenida);
        frame.add(lblSaldo);
        frame.add(btnJugar);
        frame.add(btnPerfil);
        frame.add(btnHistorial);
        frame.add(btnEstadisticas);
        frame.add(btnCerrarSesion);

        btnJugar.addActionListener(e -> {
            frame.dispose();
            new VentanaRuleta(controller).mostrarVentana();
        });

        btnPerfil.addActionListener(e -> {
            frame.dispose();
            new VentanaPerfil(controller).mostrarVentana();
        });

        btnHistorial.addActionListener(e -> {

            ResultadoController resultadoController =
                    new ResultadoController(controller);

            VentanaHistorial ventanaHistorial =
                    new VentanaHistorial(resultadoController);

            ventanaHistorial.mostrar();
        });

        btnEstadisticas.addActionListener(e -> {
            mostrarEstadisticas();
        });

        btnCerrarSesion.addActionListener(e -> {
            controller.cerrarSesion();
            frame.dispose();
            new VentanaLogin(controller).mostrarVentana();
        });
    }

    private void mostrarEstadisticas() {

        RuletaController ruletaController =
                new RuletaController(controller.getRuleta(), controller);

        Estadisticas estadisticas =
                ruletaController.obtenerEstadisticas();

        int totalApostado =
                ruletaController.getTotalApostado();

        int gananciaNeta =
                ruletaController.getGananciaNeta();

        TipoApuesta tipoMasJugado =
                estadisticas.getTipoMasJugado();

        String mensaje =
                "Rondas jugadas: " + estadisticas.getTotalJugadas() +
                        "\nMonto total apostado: $" + totalApostado +
                        "\nTotal de victorias: " + estadisticas.getVictorias() +
                        "\nPorcentaje de victorias: " +
                        String.format("%.2f", estadisticas.getPorcentajeVictorias()) + "%" +
                        "\nGanancia/pérdida neta: $" + gananciaNeta +
                        "\nRacha máxima de victorias: " +
                        estadisticas.getRachaMaxima() +
                        "\nTipo de apuesta más utilizado: " +
                        (tipoMasJugado != null ? tipoMasJugado : "Ninguno");

        JOptionPane.showMessageDialog(
                frame,
                mensaje,
                "Estadísticas - Casino Black Cat",
                JOptionPane.INFORMATION_MESSAGE
        );


    }

    public void mostrarVentana() {

        lblBienvenida.setText(
                "Bienvenido " + controller.getNombreUsuario()
        );

        lblSaldo.setText(
                "Saldo disponible: $" + controller.getSaldo()
        );

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
