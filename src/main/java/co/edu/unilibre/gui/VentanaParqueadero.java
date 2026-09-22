package co.edu.unilibre.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import co.edu.unilibre.datos.Moto;
import co.edu.unilibre.datos.Parqueadero;
import co.edu.unilibre.datos.Registro;
import co.edu.unilibre.datos.TipoPago;
import co.edu.unilibre.interaccion.GestorParqueadero;

/**
 * VentanaParqueadero
 */
public class VentanaParqueadero extends JFrame {
    private final GestorParqueadero servicio = new GestorParqueadero();
    private final Parqueadero parqueadero = new Parqueadero();

    // Controles de Entrada
    private JTextField txtPlacaEntrada;
    private JTextField txtMarca;
    private JTextField txtPropietario;
    private JButton btnRegistrarEntradaMoto;

    // Controles de Salida
    private JTextField txtPlacaSalida;
    private JComboBox<TipoPago> cbTipoPago;
    private JButton btnRegistrarSalidaMoto;

    // Reportes y Tablas
    private JButton btnGenerarReporte;
    private DefaultTableModel modeloTablaMotos;
    private JTable tablaMotos;

    public VentanaParqueadero() {
        setTitle("Sistema de Gestión de Parqueadero de Motos");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();
        actualizarTablaMotos();
    }

    private void initComponentes() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Entrada de Moto", crearPanelEntrada());
        tabbedPane.addTab("Salida y Pago", crearPanelSalida());
        tabbedPane.addTab("Estado Actual", crearPanelEstado());

        add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior para acciones globales (Reportes)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGenerarReporte = new JButton("Generar Reporte TXT");
        btnGenerarReporte.addActionListener(e -> accionGenerarReporte());
        panelInferior.add(btnGenerarReporte);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registro de Entrada"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPlaca = new JLabel("Placa:");
        txtPlacaEntrada = new JTextField(15);

        JLabel lblMarca = new JLabel("Marca moto (opcional):");
        txtMarca = new JTextField(15);

        JLabel lblPropietario = new JLabel("Cedula Propietario:");
        txtPropietario = new JTextField(15);

        btnRegistrarEntradaMoto = new JButton("Registrar Entrada");
        btnRegistrarEntradaMoto.addActionListener(e -> accionRegistrarEntrada());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblPlaca, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtPlacaEntrada, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblMarca, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtMarca, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblPropietario, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtPropietario, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnRegistrarEntradaMoto, gbc);

        return panel;
    }

    private JPanel crearPanelSalida() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registro de Salida y Cobro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPlaca = new JLabel("Placa de la Moto:");
        txtPlacaSalida = new JTextField(15);

        JLabel lblTipoPago = new JLabel("Método de Pago:");
        cbTipoPago = new JComboBox<>(TipoPago.values());

        btnRegistrarSalidaMoto = new JButton("Procesar Salida");
        btnRegistrarSalidaMoto.addActionListener(e -> accionRegistrarSalida());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblPlaca, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtPlacaSalida, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblTipoPago, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(cbTipoPago, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnRegistrarSalidaMoto, gbc);

        return panel;
    }

    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Motos Actuales en Parqueadero"));

        String[] columnas = {"Placa", "Marca", "Propietario", "Hora de Entrada"};
        modeloTablaMotos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };

        tablaMotos = new JTable(modeloTablaMotos);
        panel.add(new JScrollPane(tablaMotos), BorderLayout.CENTER);

        return panel;
    }

    private void accionRegistrarEntrada() {
        String placa = txtPlacaEntrada.getText().trim();
        String marca = txtMarca.getText().trim();
        String propietario = txtPropietario.getText().trim();

        if (placa.isEmpty() || propietario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Moto moto = new Moto(placa, propietario);

            if (marca != null && !marca.isEmpty()) {
                moto.modificarMarca(marca);
            }

            boolean exito = servicio.registrarEntradaMoto(parqueadero, moto);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Moto registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtPlacaEntrada.setText("");
                txtMarca.setText("");
                txtPropietario.setText("");
                actualizarTablaMotos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar (Cupo lleno o placa ya ingresada).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la moto.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionRegistrarSalida() {
        String placa = txtPlacaSalida.getText().trim();
        TipoPago tipoPago = (TipoPago) cbTipoPago.getSelectedItem();

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la placa de la moto.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = servicio.registrarSalidaMoto(parqueadero, placa, tipoPago);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Salida registrada y pago procesado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtPlacaSalida.setText("");
            actualizarTablaMotos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la salida. Verifique la placa.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionGenerarReporte() {
        boolean generado = servicio.generarReporte(parqueadero);
        if (generado) {
            JOptionPane.showMessageDialog(this, "Reporte generado en 'reporte.txt'.", "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al generar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaMotos() {
        modeloTablaMotos.setRowCount(0);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Registro registro : parqueadero.obtenerMotos()) {
            Moto moto = registro.obtenerMoto();
            String marcaMoto = moto.obtenerMarca();
            if (marcaMoto == null || marcaMoto.trim().isEmpty()) {
                marcaMoto = "Sin registrar";
            }
            Object[] fila = {
                moto.obtenerPlaca(),
                marcaMoto,
                moto.obtenerPropietario(),
                registro.obtenerHora().format(formateador)
            };
            modeloTablaMotos.addRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaParqueadero().setVisible(true));
    }
}
