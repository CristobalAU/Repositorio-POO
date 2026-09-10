package org.example;
import javax.swing.*;

public class VentanaRegistro {

    private final JFrame frame =
            new JFrame("Registro - Casino Black Cat");

    private final JLabel lblUsuario =
            new JLabel("Usuario");

    private final JTextField txtUsuario =
            new JTextField();

    private final JLabel lblClave =
            new JLabel("Clave");

    private final JPasswordField txtClave =
            new JPasswordField();

    private final JLabel lblNombre =
            new JLabel("Nombre Completo");

    private final JTextField txtNombre =
            new JTextField();

    private final JButton btnRegistrar =
            new JButton("Registrar");

    public VentanaRegistro() {

        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblUsuario.setBounds(50, 40, 120, 25);
        txtUsuario.setBounds(180, 40, 180, 25);

        lblClave.setBounds(50, 80, 120, 25);
        txtClave.setBounds(180, 80, 180, 25);

        lblNombre.setBounds(50, 120, 120, 25);
        txtNombre.setBounds(180, 120, 180, 25);

        btnRegistrar.setBounds(160, 180, 120, 30);

        frame.add(lblUsuario);
        frame.add(txtUsuario);

        frame.add(lblClave);
        frame.add(txtClave);

        frame.add(lblNombre);
        frame.add(txtNombre);

        frame.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarUsuario());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void registrarUsuario() {

        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());
        String nombre = txtNombre.getText();

        if (usuario.isEmpty() ||
                clave.isEmpty() ||
                nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Debe completar todos los campos"
            );

            return;
        }

        Usuario nuevoUsuario =
                new Usuario(usuario, clave, nombre);

        VentanaLogin.USUARIOS.add(nuevoUsuario);

        JOptionPane.showMessageDialog(
                frame,
                "Usuario registrado correctamente"
        );

        frame.dispose();

        VentanaLogin login = new VentanaLogin();
        login.mostrarVentana();
    }
}
