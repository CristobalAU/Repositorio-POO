package org.example;

import javax.swing.*;

public class VentanaRuleta {

    private final JFrame frame =
            new JFrame("Ruleta - Casino Black Cat");

    private final JLabel lblBienvenida =
            new JLabel();

    private final JButton btnJugar =
            new JButton("Iniciar Ronda");

    private final JButton btnEstadisticas =
            new JButton("Ver estadisticas");

    public VentanaRuleta(String nombre) {

        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblBienvenida.setText("Bienvenido " + nombre);
        lblBienvenida.setBounds(50, 30, 300, 30);

        btnJugar.setBounds(150, 100, 180, 35);
        btnEstadisticas.setBounds(150, 160, 180, 35);

        frame.add(lblBienvenida);
        frame.add(btnJugar);
        frame.add(btnEstadisticas);

        btnJugar.addActionListener(e -> iniciarRonda());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());

    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void iniciarRonda() {

        String apuesta = JOptionPane.showInputDialog(
                frame,
                "Ingrese su apuesta:\nR = Rojo\nN = Negro\nP = Par\nI = Impar"
        );

        if (apuesta == null) {
            return;
        }

        apuesta = apuesta.toUpperCase();

        if (apuesta.length() != 1) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Apuesta invalida"
            );
            return;
        }

        char tipo = apuesta.charAt(0);

        if (tipo != 'R' &&
                tipo != 'N' &&
                tipo != 'P' &&
                tipo != 'I') {

            JOptionPane.showMessageDialog(
                    frame,
                    "Debe ingresar R, N, P o I"
            );
            return;
        }

        int monto = leerMonto();

        if (monto == -1) {
            return;
        }

        int numero = Ruleta.girarRuleta();

        boolean acierto = Ruleta.evaluarResultado(numero, tipo);

        Ruleta.registrarResultado(
                numero,
                monto,
                acierto
        );

        mostrarResultado(
                numero,
                tipo,
                monto,
                acierto
        );

    }

    private int leerMonto() {

        while (true) {

            String entrada = JOptionPane.showInputDialog(
                    frame,
                    "Ingrese el monto a apostar:"
            );

            if (entrada == null) {
                return -1;
            }

            try {

                int monto = Integer.parseInt(entrada);

                if (monto > 0) {
                    return monto;
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "El monto debe ser mayor que 0"
                );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Debe ingresar un numero valido"
                );
            }
        }
    }

    private void mostrarResultado(
            int numero,
            char tipo,
            int monto,
            boolean acierto) {

        String color;

        if (numero == 0) {
            color = "Verde";
        } else if (Ruleta.esRojo(numero)) {
            color = "Rojo";
        } else {
            color = "Negro";
        }

        String mensaje =
                "Numero obtenido: " + numero +
                "\nColor: " + color +
                "\nTipo de apuesta: " + tipo +
                "\nMonto apostado: $" + monto;

        if (acierto) {
            mensaje += "\n\n¡GANASTE!";
        } else {
            mensaje += "\n\nPERDISTE.";
        }

        JOptionPane.showMessageDialog(
                frame,
                mensaje
        );

    }

    private void mostrarEstadisticas() {

        int totalApostado = Ruleta.calcularTotalApostado();
        int totalAciertos = Ruleta.calcularTotalAciertos();
        int gananciaNeta = Ruleta.calcularGananciaNeta();
        double porcentajeAciertos = Ruleta.calcularPorcentajeAciertos();

        String mensaje =
                "Rondas jugadas: " + Ruleta.historialSize +
                "\nMonto total apostado: $" + totalApostado +
                "\nTotal de aciertos: " + totalAciertos +
                "\nPorcentaje de aciertos: " +
                String.format("%.2f", porcentajeAciertos) + "%" +
                "\nGanancia/perdida neta: $" + gananciaNeta;

        JOptionPane.showMessageDialog(
                frame,
                mensaje,
                "Estadisticas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }



}
