package tarea3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *Registro de Plataforma de Streaming
 */
public class PlataformaStreaming extends JFrame {

    // ===== Objetos del formulario =====
    JTextField txtNombreArtista;
    JTextField txtPaisOrigen;
    JTextField txtOyentes;
    JButton btnCrearArtista;

    JTextField txtTitulo;
    JTextField txtInterprete;
    JTextField txtDuracion;
    JTextField txtGenero;
    JButton btnCrearCancion;

    JTextArea txtAreaResultados;

    public PlataformaStreaming() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Plataforma de Streaming");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtNombreArtista = new JTextField();
        txtPaisOrigen = new JTextField();
        txtOyentes = new JTextField();
        btnCrearArtista = new JButton("Crear Artista");

        txtTitulo = new JTextField();
        txtInterprete = new JTextField();
        txtDuracion = new JTextField();
        txtGenero = new JTextField();
        btnCrearCancion = new JButton("Crear Canción");

        txtAreaResultados = new JTextArea(10, 30);
        txtAreaResultados.setEditable(false);

        JPanel bArtista = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        bArtista.add(btnCrearArtista);
        JPanel bCancion = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        bCancion.add(btnCrearCancion);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.add(Util.izq(new JLabel("--- Datos del Artista ---")));
        formulario.add(Util.fila("Nombre:", txtNombreArtista));
        formulario.add(Util.fila("País de Origen:", txtPaisOrigen));
        formulario.add(Util.fila("Oyentes Mensuales:", txtOyentes));
        formulario.add(Util.izq(bArtista));
        formulario.add(Util.izq(new JSeparator()));
        formulario.add(Box.createVerticalStrut(8));
        formulario.add(Util.izq(new JLabel("--- Datos de la Canción ---")));
        formulario.add(Util.fila("Título:", txtTitulo));
        formulario.add(Util.fila("Intérprete (Artista):", txtInterprete));
        formulario.add(Util.fila("Duración (segundos):", txtDuracion));
        formulario.add(Util.fila("Género:", txtGenero));
        formulario.add(Util.izq(bCancion));

        JPanel resultados = new JPanel(new BorderLayout(0, 4));
        resultados.add(new JLabel("Resultados en Memoria:"), BorderLayout.NORTH);
        resultados.add(new JScrollPane(txtAreaResultados), BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(resultados, BorderLayout.CENTER);
        setContentPane(contenido);

        btnCrearArtista.addActionListener(e -> crearArtista());
        btnCrearCancion.addActionListener(e -> crearCancion());

        setSize(520, 680);
        setLocationRelativeTo(null);
    }


    private void crearArtista() {
        String nombre = txtNombreArtista.getText().trim();
        String pais = txtPaisOrigen.getText().trim();
        int oyentes;
        if (nombre.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete el nombre y el país de origen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            oyentes = Integer.parseInt(txtOyentes.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los oyentes mensuales deben ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Artista artista = new Artista();
        artista.nombre = nombre;
        artista.paisOrigen = pais;
        artista.oyentesMensuales = oyentes;

        txtAreaResultados.append("[ARTISTA CREADO]\n");
        txtAreaResultados.append("  Nombre: " + artista.nombre + "\n");
        txtAreaResultados.append("  País de Origen: " + artista.paisOrigen + "\n");
        txtAreaResultados.append("  Oyentes Mensuales: " + artista.oyentesMensuales + "\n\n");

        txtNombreArtista.setText("");
        txtPaisOrigen.setText("");
        txtOyentes.setText("");
    }

    private void crearCancion() {
        String titulo = txtTitulo.getText().trim();
        String interprete = txtInterprete.getText().trim();
        String genero = txtGenero.getText().trim();
        int duracion;
        if (titulo.isEmpty() || interprete.isEmpty() || genero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete el título, el intérprete y el género.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            duracion = Integer.parseInt(txtDuracion.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número entero de segundos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cancion cancion = new Cancion();
        cancion.titulo = titulo;
        cancion.nombreArtista = interprete;
        cancion.duracionSegundos = duracion;
        cancion.genero = genero;

        txtAreaResultados.append("[CANCIÓN CREADA]\n");
        txtAreaResultados.append("  Título: " + cancion.titulo + "\n");
        txtAreaResultados.append("  Artista: " + cancion.nombreArtista + "\n");
        txtAreaResultados.append("  Duración: " + cancion.duracionSegundos + " segundos\n");
        txtAreaResultados.append("  Género: " + cancion.genero + "\n\n");

        txtTitulo.setText("");
        txtInterprete.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new PlataformaStreaming().setVisible(true));
    }
}
