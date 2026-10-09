package org.example;

import javax.swing.*;

public class VentanaMenu {

    private final JFrame frame = new JFrame("Menú - Casino Black Cat");

    public VentanaMenu(String nombre) {
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblBienvenida = new JLabel("Bienvenido " + nombre);
        lblBienvenida.setBounds(100, 40, 300, 30);

        JButton btnJugar = new JButton("Jugar Ruleta");
        btnJugar.setBounds(100, 100, 230, 35);

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBounds(100, 160, 230, 35);

        frame.add(lblBienvenida);
        frame.add(btnJugar);
        frame.add(btnCerrarSesion);

        btnJugar.addActionListener(e -> {
            frame.dispose();
            new VentanaRuleta(nombre).mostrarVentana();
        });

        btnCerrarSesion.addActionListener(e -> {
            frame.dispose();
            new VentanaLogin().mostrarVentana();
        });
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
