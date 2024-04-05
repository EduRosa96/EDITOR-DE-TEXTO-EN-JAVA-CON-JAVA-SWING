package EditorTexto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditorTextoERR extends JFrame implements ActionListener {

    private JPanel panel1;
    private JTextField textField1;

    public EditorTextoERR() {
         panel1 = new JPanel();
         panel1.setLayout(null);
         getContentPane().add(panel1);
         panel1.setBackground(new java.awt.Color(68, 244, 131));
         add(panel1);

    }
}
