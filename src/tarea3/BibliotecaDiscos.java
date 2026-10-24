package tarea3;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
Biblioteca de Discos Musicales
 */

public class BibliotecaDiscos extends JFrame {

    String[] titulos = new String[5];
    int[] anios = new int[5];
    int cantidad = 0;

    JTextField txtBuscar;
    JButton btnBuscar;
    JLabel lblTituloEncontrado;
    JLabel lblAnioActual;
    JTextField txtNuevoAnio;
    JButton btnActualizar;

    public BibliotecaDiscos() {
        initComponents();
        cargarDatosAutomaticamente(); 
    }

    private void initComponents() {
        setTitle("Biblioteca de Discos Musicales");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtBuscar = new JTextField();
        btnBuscar = new JButton("Buscar");
        lblTituloEncontrado = new JLabel("Álbum: -");
        lblAnioActual = new JLabel("Año actual: -");
        txtNuevoAnio = new JTextField();
        btnActualizar = new JButton("Actualizar Registro");

        JPanel bBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        bBuscar.add(btnBuscar);
        JPanel bActualizar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        bActualizar.add(btnActualizar);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(Util.fila("Buscar Álbum:", txtBuscar));
        contenido.add(Util.izq(bBuscar));
        contenido.add(Util.izq(lblTituloEncontrado));
        contenido.add(javax.swing.Box.createVerticalStrut(4));
        contenido.add(Util.izq(lblAnioActual));
        contenido.add(javax.swing.Box.createVerticalStrut(12));
        contenido.add(Util.fila("Nuevo Año:", txtNuevoAnio));
        contenido.add(Util.izq(bActualizar));
        setContentPane(contenido);

        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());

        setSize(430, 300);
        setLocationRelativeTo(null);
    }

 
    private void cargarDatosAutomaticamente() {
        File archivo = Util.archivoTemp("discos.csv");
        if (!archivo.exists()) {
            crearArchivoInicial(archivo);
        }
        cantidad = 0;
        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNextLine() && cantidad < titulos.length) {
                String linea = sc.nextLine();
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                titulos[cantidad] = partes[0].trim();
                anios[cantidad] = Integer.parseInt(partes[1].trim());
                cantidad++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al leer discos.csv: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void crearArchivoInicial(File archivo) {
        String[] t = {"Thriller", "Abbey Road", "Rumours", "Back in Black", "Nevermind"};
        int[] a = {1982, 1969, 1977, 1980, 1991};
        try (FileWriter fw = new FileWriter(archivo, false)) {
            for (int i = 0; i < t.length; i++) {
                fw.write(t[i] + "," + a[i] + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo crear discos.csv.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int buscarIndice(String titulo) {
        int encontrado = -1;
        for (int i = 0; i < cantidad; i++) {
            if (titulos[i].equalsIgnoreCase(titulo.trim())) {
                encontrado = i;
                break;
            }
        }
        return encontrado;
    }

    private void buscar() {
        int pos = buscarIndice(txtBuscar.getText());
        if (pos == -1) {
            lblTituloEncontrado.setText("Álbum: -");
            lblAnioActual.setText("Año actual: álbum no encontrado");
        } else {
            lblTituloEncontrado.setText("Álbum: " + titulos[pos]);
            lblAnioActual.setText("Año actual: " + anios[pos]);
        }
    }

    private void actualizar() {
        int pos = buscarIndice(txtBuscar.getText());
        if (pos == -1) {
            JOptionPane.showMessageDialog(this, "Primero escriba el título de un álbum existente.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int nuevoAnio;
        try {
            nuevoAnio = Integer.parseInt(txtNuevoAnio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un año válido (número entero).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        anios[pos] = nuevoAnio;


        File archivo = Util.archivoTemp("discos.csv");
        try (FileWriter fw = new FileWriter(archivo, false)) {
            for (int i = 0; i < cantidad; i++) {
                fw.write(titulos[i] + "," + anios[i] + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo escribir discos.csv: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lblTituloEncontrado.setText("Álbum: " + titulos[pos]);
        lblAnioActual.setText("Año actual: " + anios[pos]);
        txtNuevoAnio.setText("");
        JOptionPane.showMessageDialog(this, "Registro actualizado y archivo guardado.");
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new BibliotecaDiscos().setVisible(true));
    }
}
