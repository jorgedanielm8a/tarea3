package tarea3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *Catálogo de Videojuegos
 */
public class CatalogoVideojuegos extends JFrame {

   
    ArrayList<Videojuego> catalogo = new ArrayList<>();

    JTextField txtTitulo;
    JTextField txtConsola;
    JTextField txtAnio;
    JButton btnAgregarJuego;
    JButton btnMostrarCatalogo;
    JButton btnEliminarUltimo;
    JTextArea txtAreaInventario;

    public CatalogoVideojuegos() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Catálogo de Videojuegos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtTitulo = new JTextField();
        txtConsola = new JTextField();
        txtAnio = new JTextField();
        btnAgregarJuego = new JButton("Agregar Juego");
        btnMostrarCatalogo = new JButton("Mostrar Catálogo");
        btnEliminarUltimo = new JButton("Eliminar Último");
        txtAreaInventario = new JTextArea(10, 30);
        txtAreaInventario.setEditable(false);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        botones.add(btnAgregarJuego);
        botones.add(btnMostrarCatalogo);
        botones.add(btnEliminarUltimo);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.add(Util.fila("Título del Juego:", txtTitulo));
        formulario.add(Util.fila("Consola:", txtConsola));
        formulario.add(Util.fila("Año de Lanzamiento:", txtAnio));
        formulario.add(Util.izq(botones));

        JPanel inventario = new JPanel(new BorderLayout(0, 4));
        inventario.add(new JLabel("Inventario Actual:"), BorderLayout.NORTH);
        inventario.add(new JScrollPane(txtAreaInventario), BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(inventario, BorderLayout.CENTER);
        setContentPane(contenido);

        btnAgregarJuego.addActionListener(e -> agregarJuego());
        btnMostrarCatalogo.addActionListener(e -> mostrarCatalogo());
        btnEliminarUltimo.addActionListener(e -> eliminarUltimo());

        setSize(520, 450);
        setLocationRelativeTo(null);
    }

   
    private void agregarJuego() {
        String titulo = txtTitulo.getText().trim();
        String consola = txtConsola.getText().trim();
        int anio;
        if (titulo.isEmpty() || consola.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete el título y la consola.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            anio = Integer.parseInt(txtAnio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Videojuego juego = new Videojuego(titulo, consola, anio);
        catalogo.add(juego);

        txtTitulo.setText("");
        txtConsola.setText("");
        txtAnio.setText("");
        txtTitulo.requestFocus();
    }

    private void mostrarCatalogo() {
        txtAreaInventario.setText("");
        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego v = catalogo.get(i);
            txtAreaInventario.append((i + 1) + ". " + v.titulo + " | " + v.consola + " | " + v.anioLanzamiento + "\n");
        }
        txtAreaInventario.append("\nTotal de juegos registrados: " + catalogo.size() + "\n");
    }

    private void eliminarUltimo() {
        if (catalogo.size() > 0) {
            Videojuego eliminado = catalogo.remove(catalogo.size() - 1);
            JOptionPane.showMessageDialog(this, "Se eliminó el juego: " + eliminado.titulo);
        } else {
            JOptionPane.showMessageDialog(this, "El catálogo está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new CatalogoVideojuegos().setVisible(true));
    }
}
