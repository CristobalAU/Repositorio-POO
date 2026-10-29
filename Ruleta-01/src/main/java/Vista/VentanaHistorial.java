package Vista;

import Controlador.ResultadoController;
import Modelo.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaHistorial {

    private final JFrame frame;
    private final ResultadoController controller;

    private final DefaultTableModel modeloTabla;
    private final JTable tablaHistorial;

    public VentanaHistorial(ResultadoController controller) {

        this.controller = controller;

        frame = new JFrame("Historial - Casino Black Cat");
        frame.setSize(750, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        String[] columnas = {
                "Número",
                "Tipo de apuesta",
                "Monto",
                "Resultado",
                "Ganancia"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);

        JButton btnActualizar = new JButton("Actualizar historial");

        btnActualizar.addActionListener(e -> cargarHistorial());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnActualizar, BorderLayout.SOUTH);
    }

    private void cargarHistorial() {

        modeloTabla.setRowCount(0);

        List<Resultado> historial = controller.obtenerHistorial();

        for (Resultado resultado : historial) {

            Object[] fila = {
                    resultado.getNumero(),
                    resultado.getTipoApuesta().getEtiqueta(),
                    resultado.getMontoApostado(),
                    resultado.isGano() ? "Ganó" : "Perdió",
                    resultado.getGanancia()
            };

            modeloTabla.addRow(fila);
        }
    }

    public void mostrar() {
        cargarHistorial();
        frame.setVisible(true);
    }
}
