package view;

import javax.swing.*;

// A panel containing a label and text field. Adapted from CACoding (courtesy Paul Gries).
// TODO: question: do we need this?

public class LabelTextPanel extends JPanel {
    public LabelTextPanel(JLabel label, JTextField field) {
        this.add(label);
        this.add(field);
    }
}
