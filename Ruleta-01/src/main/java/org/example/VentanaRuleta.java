
package org.example;

import javax.swing.*;

public class VentanaRuleta {

    private final SessionController controller;

    private final JFrame frame =
            new JFrame("Ruleta - Casino Black Cat");

    private final JLabel lblBienvenida = new JLabel();
    private final JLabel lblSaldo = new JLabel();

    private final JLabel lblTipoApuesta =
            new JLabel("Tipo de apuesta:");

    private final JComboBox<TipoApuesta> cmbTipoApuesta =
            new JComboBox<>(TipoApuesta.values());

    private final JButton btnJugar =
            new JButton("Iniciar Ronda");

    private final JButton btnEstadisticas =
            new JButton("Ver Estadísticas");

    private final JButton btnVolver =
            new JButton("Volver al Menú");

    public VentanaRuleta(SessionController controller) {

        this.controller = controller;

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblBienvenida.setBounds(50, 20, 350, 30);
        lblSaldo.setBounds(50, 55, 350, 30);

        lblTipoApuesta.setBounds(50, 105, 130, 30);
        cmbTipoApuesta.setBounds(190, 105, 200, 30);

        btnJugar.setBounds(150, 160, 200, 35);
        btnEstadisticas.setBounds(150, 215, 200, 35);
        btnVolver.setBounds(150, 270, 200, 35);

        frame.add(lblBienvenida);
        frame.add(lblSaldo);
        frame.add(lblTipoApuesta);
        frame.add(cmbTipoApuesta);
        frame.add(btnJugar);
        frame.add(btnEstadisticas);
        frame.add(btnVolver);

        btnJugar.addActionListener(e -> iniciarRonda());

        btnEstadisticas.addActionListener(
                e -> mostrarEstadisticas()
        );

        btnVolver.addActionListener(e -> {
            frame.dispose();
            new VentanaMenu(controller).mostrarVentana();
        });
    }

    public void mostrarVentana() {

        actualizarDatos();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void actualizarDatos() {

        lblBienvenida.setText(
                "Bienvenido " + controller.getNombreUsuario()
        );

        lblSaldo.setText(
                "Saldo disponible: $" + controller.getSaldo()
        );
    }

    private void iniciarRonda() {

        TipoApuesta tipo =
                (TipoApuesta) cmbTipoApuesta.getSelectedItem();

        int monto = leerMonto();

        if (monto == -1) {
            return;
        }

        try {

            int numero = controller.jugarRonda(tipo, monto);

            boolean acierto = controller.getRuleta()
                    .evaluarResultado(numero, tipo);

            mostrarResultado(
                    numero,
                    tipo,
                    monto,
                    acierto
            );

            actualizarDatos();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage()
            );
        }
    }

    private int leerMonto() {

        while (true) {

            String entrada = JOptionPane.showInputDialog(
                    frame,
                    "Saldo disponible: $" + controller.getSaldo() +
                            "\nIngrese el monto a apostar:"
            );

            if (entrada == null) {
                return -1;
            }

            try {

                int monto = Integer.parseInt(entrada.trim());

                if (monto <= 0) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "El monto debe ser mayor que 0"
                    );

                } else if (monto > controller.getSaldo()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Saldo insuficiente"
                    );

                } else {
                    return monto;
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Debe ingresar un número válido"
                );
            }
        }
    }

    private void mostrarResultado(
            int numero,
            TipoApuesta tipo,
            int monto,
            boolean acierto) {

        String color;

        if (numero == 0) {
            color = "Verde";
        } else if (controller.getRuleta().esRojo(numero)) {
            color = "Rojo";
        } else {
            color = "Negro";
        }

        String mensaje =
                "Número obtenido: " + numero +
                        "\nColor: " + color +
                        "\nTipo de apuesta: " + tipo +
                        "\nMonto apostado: $" + monto;

        if (acierto) {
            mensaje += "\n\n¡GANASTE!";
        } else {
            mensaje += "\n\nPERDISTE.";
        }

        mensaje += "\nSaldo actual: $" + controller.getSaldo();

        JOptionPane.showMessageDialog(
                frame,
                mensaje
        );
    }

    private void mostrarEstadisticas() {

        Ruleta ruleta = controller.getRuleta();

        int totalApostado =
                ruleta.calcularTotalApostado();

        int totalAciertos =
                ruleta.calcularTotalAciertos();

        int gananciaNeta =
                ruleta.calcularGananciaNeta();

        double porcentajeAciertos =
                ruleta.calcularPorcentajeAciertos();

        String mensaje =
                "Rondas jugadas: " + ruleta.getHistorialSize() +
                        "\nMonto total apostado: $" + totalApostado +
                        "\nTotal de aciertos: " + totalAciertos +
                        "\nPorcentaje de aciertos: " +
                        String.format("%.2f", porcentajeAciertos) + "%" +
                        "\nGanancia/pérdida neta: $" + gananciaNeta;

        JOptionPane.showMessageDialog(
                frame,
                mensaje,
                "Estadísticas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}