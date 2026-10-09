
package org.example;

import javax.swing.*;

public class VentanaRegistro {

    private final SessionController controller;

    private final JFrame frame =
            new JFrame("Registro - Casino Black Cat");

    private final JLabel lblUsuario =
            new JLabel("Usuario:");

    private final JTextField txtUsuario =
            new JTextField();

    private final JLabel lblClave =
            new JLabel("Contraseña:");

    private final JPasswordField txtClave =
            new JPasswordField();

    private final JLabel lblNombre =
            new JLabel("Nombre completo:");

    private final JTextField txtNombre =
            new JTextField();

    private final JButton btnRegistrar =
            new JButton("Registrarse");

    private final JButton btnVolver =
            new JButton("Volver al Login");

    public VentanaRegistro(SessionController controller) {

        this.controller = controller;

        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblUsuario.setBounds(40, 40, 130, 25);
        txtUsuario.setBounds(180, 40, 200, 25);

        lblClave.setBounds(40, 85, 130, 25);
        txtClave.setBounds(180, 85, 200, 25);

        lblNombre.setBounds(40, 130, 130, 25);
        txtNombre.setBounds(180, 130, 200, 25);

        btnRegistrar.setBounds(120, 190, 200, 35);
        btnVolver.setBounds(120, 245, 200, 35);

        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(lblNombre);
        frame.add(txtNombre);
        frame.add(btnRegistrar);
        frame.add(btnVolver);

        btnRegistrar.addActionListener(e -> registrar());

        btnVolver.addActionListener(e -> {
            frame.dispose();
            new VentanaLogin(controller).mostrarVentana();
        });
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void registrar() {

        String username = txtUsuario.getText().trim();
        String password = new String(txtClave.getPassword());
        String nombre = txtNombre.getText().trim();

        if (username.isEmpty() ||
                password.isEmpty() ||
                nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Debe completar todos los campos"
            );
            return;
        }

        for (Usuario usuario : VentanaLogin.USUARIOS) {

            if (usuario.getUsername().equals(username)) {

                JOptionPane.showMessageDialog(
                        frame,
                        "El nombre de usuario ya existe"
                );
                return;
            }
        }

        Usuario nuevoUsuario =
                new Usuario(username, password, nombre);

        VentanaLogin.USUARIOS.add(nuevoUsuario);

        JOptionPane.showMessageDialog(
                frame,
                "Usuario registrado correctamente"
        );

        frame.dispose();
        new VentanaLogin(controller).mostrarVentana();
    }
}