package tarea3;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Util {


    public static final String NOMBRE = "sunombre";


    public static final String CARPETA_TEMP = "temp";

    public static File archivoTemp(String nombreArchivo) {
        File carpeta = new File(CARPETA_TEMP);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        return new File(carpeta, nombreArchivo);
        
        
        
    }

    /** Formatea un número con hasta 2 decimales y punto decimal (65, 24.6, 65.33). */
    public static String num(double valor) {
        DecimalFormat df = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.US));
        return df.format(valor);
    }


    public static void aplicarEstilo() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            
            

        }
    }

    public static JPanel fila(String texto, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setPreferredSize(new Dimension(170, 24));
        p.add(etiqueta, BorderLayout.WEST);
        p.add(campo, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    public static <T extends JComponent> T izq(T componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        return componente;
    }
}
