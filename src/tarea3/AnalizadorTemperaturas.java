package tarea3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *Analizador de Temperaturas.
 */
public class AnalizadorTemperaturas extends JFrame {

    double[] temperaturas = new double[6];
    int indice = 0;
    String ruta = "temp/temperaturas.txt";

    JButton btnCargarDatos;
    JButton btnCalcularEstadisticas;
    JTextArea txtAreaResultados;

    public AnalizadorTemperaturas() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Lector y Analizador de Temperaturas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnCargarDatos = new JButton("Cargar Datos");
        btnCalcularEstadisticas = new JButton("Calcular Estadísticas");
        txtAreaResultados = new JTextArea(8, 30);
        txtAreaResultados.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botones.add(btnCargarDatos);
        botones.add(btnCalcularEstadisticas);

        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(botones, BorderLayout.NORTH);
        contenido.add(new JScrollPane(txtAreaResultados), BorderLayout.CENTER);
        setContentPane(contenido);

        btnCargarDatos.addActionListener(e -> cargarDatos());
        btnCalcularEstadisticas.addActionListener(e -> calcularEstadisticas());

        setSize(460, 330);
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo:\n" + ruta, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        indice = 0;
        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNextLine() && indice < temperaturas.length) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) {
                    continue;
                }
                temperaturas[indice] = Double.parseDouble(linea.replace(',', '.'));
                indice++;
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            indice = 0;
            return;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El archivo contiene un valor que no es numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            indice = 0;
            return;
        }
        if (indice < temperaturas.length) {
            JOptionPane.showMessageDialog(this, "El archivo solo tiene " + indice + " registro(s); se esperaban 6.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            indice = 0;
            return;
        }
        JOptionPane.showMessageDialog(this, "Los datos fueron cargados en memoria.");
    }

    private void calcularEstadisticas() {
        if (indice < temperaturas.length) {
            JOptionPane.showMessageDialog(this, "Primero debe presionar \"Cargar Datos\".", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double suma = 0;
        int calidos = 0;
        int frescos = 0;
        for (int i = 0; i < temperaturas.length; i++) {
            suma += temperaturas[i];
            if (temperaturas[i] >= 25.0) {
                calidos++;
            } else {
                frescos++;
            }
        }
        double promedio = suma / temperaturas.length;

        txtAreaResultados.setText("");
        txtAreaResultados.append("Análisis Climático\n");
        txtAreaResultados.append("------------------\n");
        txtAreaResultados.append("Promedio de Temperatura: " + Util.num(promedio) + "\n");
        txtAreaResultados.append("Días Cálidos (>= 25°): " + calidos + " Días Frescos (< 25°): " + frescos + "\n");
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new AnalizadorTemperaturas().setVisible(true));
    }
}
