package tarea3;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class RegistroNotas extends JFrame {

    double[] notas = new double[6];
    int indice = 0;

    JTextField txtNota;
    JButton btnAgregarNota;
    JButton btnGenerarReporte;
    JLabel lblEstado;

    public RegistroNotas() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro y Estadísticas de Calificaciones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtNota = new JTextField();
        btnAgregarNota = new JButton("Agregar Nota");
        btnGenerarReporte = new JButton("Generar Reporte");
        lblEstado = new JLabel("Notas ingresadas: 0 de 6");

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
        botones.add(btnAgregarNota);
        botones.add(javax.swing.Box.createHorizontalStrut(10));
        botones.add(btnGenerarReporte);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(Util.fila("Nota (0 - 100):", txtNota));
        contenido.add(Util.izq(botones));
        contenido.add(Util.izq(lblEstado));
        setContentPane(contenido);
        
        

        btnAgregarNota.addActionListener(e -> agregarNota());
        btnGenerarReporte.addActionListener(e -> generarReporte());
        getRootPane().setDefaultButton(btnAgregarNota);

        setSize(430, 190);
        setLocationRelativeTo(null);
        
        
    }

    private void agregarNota() {
        if (indice >= notas.length) {
            JOptionPane.showMessageDialog(this, "Ya se ingresaron las 6 notas. Presione \"Generar Reporte\".");
            return;
        }
        
        
        double nota;
        try {
            nota = Double.parseDouble(txtNota.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNota.requestFocus();
            return;
        }
        
        
        if (nota < 0 || nota > 100) {
            JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNota.requestFocus();
            return;
        }
        
        
        notas[indice] = nota;
        indice++;
        txtNota.setText("");
        txtNota.requestFocus();
        lblEstado.setText("Notas ingresadas: " + indice + " de 6");
        if (indice == notas.length) {
            JOptionPane.showMessageDialog(this, "Se ingresaron las 6 notas. Ya puede generar el reporte.");
        }
    }

    private void generarReporte() {
        if (indice < notas.length) {
            JOptionPane.showMessageDialog(this, "Faltan " + (notas.length - indice) + " nota(s) por ingresar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int aprobados = 0;
        int reprobados = 0;
        double suma = 0;
        for (int i = 0; i < notas.length; i++) {
            if (notas[i] >= 61) {
                aprobados++;
            } else {
                reprobados++;
            }
            suma += notas[i];
        }
        double promedio = suma / notas.length;

        File archivo = Util.archivoTemp(Util.NOMBRE + "_reporte_notas.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Reporte de Notas");
            pw.println("Aprobados: " + aprobados);
            pw.println("Reprobados: " + reprobados);
            pw.println("Promedio de Notas: " + Util.num(promedio));
            JOptionPane.showMessageDialog(this, "Reporte generado en:\n" + archivo.getPath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo escribir el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new RegistroNotas().setVisible(true));
    }
}
