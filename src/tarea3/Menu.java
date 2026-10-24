package tarea3;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Menú principal
 */
public class Menu extends JFrame {

    public Menu() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Tarea 3 - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenido = new JPanel(new GridLayout(0, 1, 0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        contenido.add(new JLabel("Seleccione un problema:", SwingConstants.CENTER));

        agregarBoton(contenido, "1. Registro y Estadísticas de Calificaciones", () -> new RegistroNotas());
        agregarBoton(contenido, "2. Manifiesto de Pasajeros de Vuelo", () -> new ManifiestoPasajeros());
        agregarBoton(contenido, "3. Lector y Analizador de Temperaturas", () -> new AnalizadorTemperaturas());
        agregarBoton(contenido, "4. Mantenimiento de Datos (Discos)", () -> new BibliotecaDiscos());
        agregarBoton(contenido, "5. Clases y Objetos (Streaming)", () -> new PlataformaStreaming());
        agregarBoton(contenido, "6. Colecciones Dinámicas (Videojuegos)", () -> new CatalogoVideojuegos());

        setContentPane(contenido);
        setSize(400, 380);
        setLocationRelativeTo(null);
    }

    private void agregarBoton(JPanel panel, String texto, java.util.function.Supplier<JFrame> ventana) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> ventana.get().setVisible(true));
        panel.add(boton);
    }

    public static void main(String[] args) {
        Util.aplicarEstilo();
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }
}
