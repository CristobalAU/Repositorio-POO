package org.example;

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

    private final JButton btnCerrarSesion =
            new JButton("Cerrar Sesión");

    public VentanaMenu(SessionController controller) {

        this.controller = controller;

        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblBienvenida.setBounds(50, 30, 350, 30);
        lblSaldo.setBounds(50, 65, 350, 30);

        btnJugar.setBounds(110, 115, 230, 35);
        btnPerfil.setBounds(110, 165, 230, 35);
        btnCerrarSesion.setBounds(110, 215, 230, 35);

        frame.add(lblBienvenida);
        frame.add(lblSaldo);
        frame.add(btnJugar);
        frame.add(btnPerfil);
        frame.add(btnCerrarSesion);

        btnJugar.addActionListener(e -> {
            frame.dispose();
            new VentanaRuleta(controller).mostrarVentana();
        });

        btnPerfil.addActionListener(e -> {
            frame.dispose();
            new VentanaPerfil(controller).mostrarVentana();
        });

        btnCerrarSesion.addActionListener(e -> {
            controller.cerrarSesion();
            frame.dispose();
            new VentanaLogin(controller).mostrarVentana();
        });
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
