package tarea3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Manifiesto pasajeros
 */
public class ManifiestoPasajeros extends JFrame {

    // ===== Variables globales (arreglo e índice) =====
    String[] pasajeros = new String[5];
    int indice = 0;

    // ===== Objetos del formulario =====
    JTextField txtNombre;
    JTextArea txtAreaLista;
    JButton btnRegistrarPasajero;
    JButton btnExportarManifiesto;

    public ManifiestoPasajeros() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Manifiesto de Pasajeros de Vuelo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtNombre = new JTextField();
        txtAreaLista = new JTextArea(8, 25);
        txtAreaLista.setEditable(false);
        btnRegistrarPasajero = new JButton("Registrar Pasajero");
        btnExportarManifiesto = new JButton("Exportar Manifiesto");

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(Util.fila("Nombre del pasajero:", txtNombre), BorderLayout.NORTH);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botones.add(btnRegistrarPasajero);
        botones.add(btnExportarManifiesto);
        norte.add(botones, BorderLayout.SOUTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(norte, BorderLayout.NORTH);
        contenido.add(new JScrollPane(txtAreaLista), BorderLayout.CENTER);
        setContentPane(contenido);

        btnRegistrarPasajero.addActionListener(e -> registrarPasajero());
        btnExportarManifiesto.addActionListener(e -> exportarManifiesto());
        getRootPane().setDefaultButton(btnRegistrarPasajero);

        setSize(480, 360);
        setLocationRelativeTo(null);
    }

    /** Botón "Registrar Pasajero" */
    private void registrarPasajero() {
        if (indice >= pasajeros.length) {
            JOptionPane.showMessageDialog(this, "El vuelo está completo (5 pasajeros). Presione \"Exportar Manifiesto\".");
            return;
        }
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del pasajero.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        pasajeros[indice] = nombre;
        indice++;
        txtAreaLista.append("- " + nombre + "\n");
        txtNombre.setText("");
        txtNombre.requestFocus();
    }

    /** Botón "Exportar Manifiesto" */
    private void exportarManifiesto() {
        if (indice < pasajeros.length) {
            JOptionPane.showMessageDialog(this, "Faltan " + (pasajeros.length - indice) + " pasajero(s) por registrar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        File archivo = Util.archivoTemp(Util.NOMBRE + "_manifiesto.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Manifiesto Oficial de Vuelo");
            pw.println();
            for (int i = 0; i < pasajeros.length; i++) {
                pw.println("- " + pasajeros[i]);
            }
            JOptionPane.showMessageDialog(this, "Manifiesto exportado en:\n" + archivo.getPath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo escribir el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new ManifiestoPasajeros().setVisible(true));
    }
}
