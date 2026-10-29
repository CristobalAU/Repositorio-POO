
package Vista;

import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.ApuestaBase;
import Modelo.ApuestaRojo;
import Modelo.ApuestaNegro;
import Modelo.ApuestaPar;
import Modelo.ApuestaImpar;
import Modelo.Resultado;

import javax.swing.*;

public class VentanaRuleta {

    private final SessionController controller;

    private final RuletaController ruletaController;

    private final JFrame frame =
            new JFrame("Ruleta - Casino Black Cat");

    private final JLabel lblBienvenida = new JLabel();
    private final JLabel lblSaldo = new JLabel();

    private final JLabel lblTipoApuesta =
            new JLabel("Tipo de apuesta:");

    private final JComboBox<String> cmbTipoApuesta =
            new JComboBox<>(new String[]{
                    "Rojo",
                    "Negro",
                    "Par",
                    "Impar"
            });

    private final JButton btnJugar =
            new JButton("Iniciar Ronda");

    private final JButton btnVolver =
            new JButton("Volver al Menú");

    public VentanaRuleta(SessionController controller) {

        this.controller = controller;
        this.ruletaController = new RuletaController(
                controller.getRuleta(),
                controller
        );

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        lblBienvenida.setBounds(50, 20, 350, 30);
        lblSaldo.setBounds(50, 55, 350, 30);

        lblTipoApuesta.setBounds(50, 105, 130, 30);
        cmbTipoApuesta.setBounds(190, 105, 200, 30);

        btnJugar.setBounds(150, 160, 200, 35);
        btnVolver.setBounds(150, 220, 200, 35);

        frame.add(lblBienvenida);
        frame.add(lblSaldo);
        frame.add(lblTipoApuesta);
        frame.add(cmbTipoApuesta);
        frame.add(btnJugar);
        frame.add(btnVolver);

        btnJugar.addActionListener(e -> iniciarRonda());

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

    private ApuestaBase crearApuesta(String tipo, int monto) {

        switch (tipo) {
            case "Rojo":
                return new ApuestaRojo(monto);

            case "Negro":
                return new ApuestaNegro(monto);

            case "Par":
                return new ApuestaPar(monto);

            case "Impar":
                return new ApuestaImpar(monto);

            default:
                throw new IllegalArgumentException(
                        "Tipo de apuesta ínválido"
                );
        }
    }

    private void iniciarRonda() {

        String tipo = (String) cmbTipoApuesta.getSelectedItem();

        int monto = leerMonto();

        if (monto == -1) {
            return;
        }

        try {

            ApuestaBase apuesta = crearApuesta(tipo, monto);

            ruletaController.jugarRonda(apuesta);

            Resultado resultado = controller.getRuleta()
                    .getResultados()
                    .get(controller.getRuleta().getHistorialSize() -1);

            mostrarResultado(resultado);

            actualizarDatos();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage(),
                    "Apuesta inválida",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalStateException e) {

            actualizarDatos();

            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage()
                            + "\nLa partida ya fue ejecutada."
                            + "\nRevisa el historial y el saldo antes de apostar.",
                    "Error al guardar la partida",
                    JOptionPane.ERROR_MESSAGE
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

    private void mostrarResultado(Resultado resultado) {

        int numero = resultado.getNumero();

        String color = controller.getRuleta()
                .obtenerColor(numero);

        String mensaje =
                "Número obtenido: " + numero +
                        "\nColor: " + color +
                        "\nTipo de apuesta: " +
                                resultado.getTipoApuesta().getEtiqueta() +
                        "\nMonto apostado: $" +
                                resultado.getMontoApostado();

        if (resultado.isGano()) {
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

}