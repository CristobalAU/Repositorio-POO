package org.example;

import javax.swing.*;

public class VentanaPerfil {

    private final SessionController controller;

    private final JFrame frame =
            new JFrame("Perfil - Casino Black Cat");

    private final JLabel lblNombre = new JLabel();
    private final JLabel lblSaldo = new JLabel();

    private final JButton btnCambiarNombre =
            new JButton("Cambiar Nombre");

    private final JButton btnDepositar =
            new JButton("Depositar");

    private final JButton btnVolver =
            new JButton("Volver al Menú");

    public VentanaPerfil(SessionController controller) {

        this.controller = controller;

        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblNombre.setBounds(50, 30, 350, 30);
        lblSaldo.setBounds(50, 65, 350, 30);

        btnCambiarNombre.setBounds(110, 115, 230, 35);
        btnDepositar.setBounds(110, 165, 230, 35);
        btnVolver.setBounds(110, 215, 230, 35);

        frame.add(lblNombre);
        frame.add(lblSaldo);
        frame.add(btnCambiarNombre);
        frame.add(btnDepositar);
        frame.add(btnVolver);

        btnCambiarNombre.addActionListener(e -> cambiarNombre());
        btnDepositar.addActionListener(e -> depositar());

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
        lblNombre.setText(
                "Nombre: " + controller.getNombreUsuario()
        );

        lblSaldo.setText(
                "Saldo disponible: $" + controller.getSaldo()
        );
    }

    private void cambiarNombre() {

        String nuevoNombre = JOptionPane.showInputDialog(
                frame,
                "Ingrese su nuevo nombre:"
        );

        if (nuevoNombre == null) {
            return;
        }

        if (controller.actualizarNombre(nuevoNombre)) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Nombre actualizado correctamente"
            );
            actualizarDatos();
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "El nombre no puede estar vacío"
            );
        }
    }

    private void depositar() {

        String entrada = JOptionPane.showInputDialog(
                frame,
                "Ingrese el monto a depositar:"
        );

        if (entrada == null) {
            return;
        }

        try {
            int monto = Integer.parseInt(entrada.trim());

            if (controller.depositar(monto)) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Depósito realizado correctamente"
                );
                actualizarDatos();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "El monto debe ser mayor que 0"
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Debe ingresar un número válido"
            );
        }
    }
}