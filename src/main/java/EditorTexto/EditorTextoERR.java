package EditorTexto;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorTextoERR extends JFrame {

    private JPanel panel1;
    private JButton negrita, cursiva, subrayado, color;
    private JTextPane textPane1;
    private JSpinner tamLetra;
    private JComboBox<String> fuente;

    public EditorTextoERR() {
        setTitle("Editor de Texto");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Inicializamos el panel principal
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        add(panel1);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new java.awt.Color(68, 244, 131));

        // Añadimos los botones al panel de botones
        negrita = new JButton("Negrita");
        buttonPanel.add(negrita);
        negrita.addActionListener(new BoldButtonListener());

        cursiva = new JButton("Cursiva");
        buttonPanel.add(cursiva);
        cursiva.addActionListener(new ItalicButtonListener());

        subrayado = new JButton("Subrayado");
        buttonPanel.add(subrayado);
        subrayado.addActionListener(new UnderlineButtonListener());

        color = new JButton("Color");
        buttonPanel.add(color);
        color.addActionListener(new ColorChooserUI());


        // Añadimos el panel de botones y lo añadimos al panel principal
        panel1.add(buttonPanel, BorderLayout.NORTH);

        // Inicializamos el JTextPane
        textPane1 = new JTextPane();

        // Inicializamos el JScrollPane y lo añadimos al JTextPane
        JScrollPane scrollTexto = new JScrollPane(textPane1);
        panel1.add(scrollTexto, BorderLayout.CENTER);

        // Añadimos el JSpinner para el tamaño de la letra
        tamLetra = new JSpinner(new SpinnerNumberModel(12, 1, 100, 1));
        tamLetra.addChangeListener(new SpinnerChangeListener());
        buttonPanel.add(tamLetra);

        // Crear el JComboBox de fuentes
        fuente = new FontComboBox(textPane1);
        fuente.addActionListener(new ComboBoxHandler());
        buttonPanel.add(fuente);
    }

    // Clase interna para manejar la acción del botón "Negrita"
    private class BoldButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Obtener la selección actual
            int start = textPane1.getSelectionStart();
            int end = textPane1.getSelectionEnd();

            // Obtener el documento de estilo del JTextPane
            StyledDocument doc = textPane1.getStyledDocument();

            // Crear estilo
            Style style = doc.addStyle("Negrita", null);

            // Verificar si el texto está en negrita
            AttributeSet attributes = doc.getCharacterElement(start).getAttributes();
            boolean isBold = StyleConstants.isBold(attributes);

            // Alternar el estilo de negrita
            StyleConstants.setBold(style, !isBold);

            // Aplicar el estilo al texto seleccionado
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    // Clase interna para manejar la acción del botón "Cursiva"
    private class ItalicButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Obtener la selección actual
            int start = textPane1.getSelectionStart();
            int end = textPane1.getSelectionEnd();

            // Obtener el documento de estilo del JTextPane
            StyledDocument doc = textPane1.getStyledDocument();

            // Crear estilo
            Style style = doc.addStyle("Cursiva", null);

            // Verificar si el texto está en cursiva
            AttributeSet attributes = doc.getCharacterElement(start).getAttributes();
            boolean isItalic = StyleConstants.isItalic(attributes);

            // Alternar el estilo de cursiva
            StyleConstants.setItalic(style, !isItalic);

            // Aplicar el estilo al texto seleccionado
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    // Clase interna para manejar la acción del botón "Subrayado"
    private class UnderlineButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Obtener la selección actual
            int start = textPane1.getSelectionStart();
            int end = textPane1.getSelectionEnd();

            // Obtener el documento de estilo del JTextPane
            StyledDocument doc = textPane1.getStyledDocument();

            // Crear estilo
            Style style = doc.addStyle("Subrayado", null);

            // Verificar si el texto está subrayado
            AttributeSet attributes = doc.getCharacterElement(start).getAttributes();
            boolean isUnderlined = StyleConstants.isUnderline(attributes);

            // Alternar el estilo de subrayado
            StyleConstants.setUnderline(style, !isUnderlined);

            // Aplicar el estilo al texto seleccionado
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    // Clase interna para manejar el cambio en el valor del JSpinner
    private class SpinnerChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            // Obtener el valor seleccionado del JSpinner
            int fontSize = (int) tamLetra.getValue();

            // Obtener el estilo del JTextPane
            StyledDocument doc = textPane1.getStyledDocument();
            Style style = doc.addStyle("FontStyle", null);

            // Aplicar el tamaño de letra al texto seleccionado
            int start = textPane1.getSelectionStart();
            int end = textPane1.getSelectionEnd();
            StyleConstants.setFontSize(style, fontSize);
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    // Clase interna para manejar la selección de fuentes en el JComboBox
    private class ComboBoxHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            String selectedFont = (String) comboBox.getSelectedItem();

            // Obtener el estilo del JTextPane
            StyledDocument doc = textPane1.getStyledDocument();
            Style style = doc.addStyle("FontStyle", null);

            // Aplicar el tipo de letra seleccionado al texto
            int start = textPane1.getSelectionStart();
            int end = textPane1.getSelectionEnd();
            StyleConstants.setFontFamily(style, selectedFont);
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    // Clase interna para el JComboBox de fuentes
    public class FontComboBox extends JComboBox<String> {
        private JTextPane textPane;

        public FontComboBox(JTextPane textPane) {
            this.textPane = textPane;

            // Obtener las fuentes disponibles en el sistema
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();

            // Añadir las fuentes al JComboBox
            for (String fontName : fontNames) {
                addItem(fontName);
            }

            // Establecer el renderizador de celdas personalizado
            setRenderer(new FontCellRenderer());

            // Establecer un valor prototipo para el tamaño de la celda
            setPrototypeDisplayValue("AbcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }

        // Clase interna para el renderizador de celdas personalizado
        private class FontCellRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                // Llamar a la implementación predeterminada para obtener el componente de renderizado
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Obtener el nombre de la fuente
                String fontName = (String) value;

                // Establecer el nombre de la fuente y su estilo correspondiente
                label.setFont(new Font(fontName, Font.PLAIN, 12));
                label.setText(fontName);

                return label;
            }
        }
    }

    private class ColorChooserUI implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Abre el JColorChooser
            Color newColor = JColorChooser.showDialog(EditorTextoERR.this, "Selecciona un color", textPane1.getForeground());

            // Verifica si se seleccionó un color
            if (newColor != null) {
                // Obtiene el documento de estilo del JTextPane
                StyledDocument doc = textPane1.getStyledDocument();
                // Obtiene la posición de inicio y fin del texto seleccionado
                int start = textPane1.getSelectionStart();
                int end = textPane1.getSelectionEnd();
                // Crea un estilo
                Style style = doc.addStyle("Color", null);
                // Establece el color seleccionado en el estilo
                StyleConstants.setForeground(style, newColor);
                // Aplica el estilo al texto seleccionado
                doc.setCharacterAttributes(start, end - start, style, false);
            }
        }
    }

    public static void main(String[] args) {
        EditorTextoERR editor = new EditorTextoERR();
        editor.setVisible(true);
    }
}
