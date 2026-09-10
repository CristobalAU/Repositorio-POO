package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class VentanaLogin {

    public static final List<Usuario> USUARIOS = new ArrayList<>();

    private final JFrame frame =
            new JFrame("Login - Casino Black Cat");

    private final JLabel lblUsuario = new JLabel("Usuario");
    private final JTextField txtUsuario = new JTextField();

    private final JLabel lblClave = new JLabel("Clave");
    private final JPasswordField txtClave = new JPasswordField();

    private final JButton btnIngresar = new JButton("Ingresar");

    private final JButton btnRegistrar =
            new JButton("Registrarse");

    public VentanaLogin() {

        if (USUARIOS.isEmpty()) {
            USUARIOS.add(new Usuario("admin", "1234", "Administrador"));
            USUARIOS.add(new Usuario("jugador", "1234", "Jugador"));
        }

        frame.setSize(400,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblUsuario.setBounds(50, 40, 100, 25);
        txtUsuario.setBounds(150, 40, 100, 25);

        lblClave.setBounds(50, 80, 100, 25);
        txtClave.setBounds(150, 80, 100, 25);

        btnIngresar.setBounds(150, 130, 100, 30);
        btnRegistrar.setBounds(150, 170, 100, 30);

        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(btnIngresar);
        frame.add(btnRegistrar);

        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {

        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        String nombre = validarCredenciales(usuario, clave);

        if (!nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Bienvenido " + nombre
            );

            frame.dispose();

            VentanaRuleta ventanaRuleta = new VentanaRuleta(nombre);
            ventanaRuleta.mostrarVentana();


        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "Usuario o contraseña incorrectos"
            );
        }
    }

    private String validarCredenciales(String u, String p) {

        for (Usuario usuario : USUARIOS) {

            if (usuario.validarCredenciales(u, p)) {

                return usuario.getNombre();
            }
        }

        return "";
    }

    private void abrirRegistro() {

        frame.dispose();

        VentanaRegistro registro = new VentanaRegistro();
        registro.mostrarVentana();
    }

}
