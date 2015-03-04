package main.java.view.frames;

import javax.swing.*;
import java.awt.event.*;

public class TabPropertiesViewEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField defaultBackgroundColor;
    private JPanel defaultVertexColor;
    private JPanel defaultEdgesColor;

    private JTextField defaultShape;
    private JTextField defaultSize;

    private JTextField defaultVertexThickness;
    private JTextField defaultEdgesThickness;

    private JComboBox<String> vertexShape;

    public TabPropertiesViewEditor() {
        this.setTitle("Tab Properties");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }


    // EN COURS DE CONSTRUCTION
    private void initComponents() {
        this.defaultBackgroundColor = null;
        this.defaultShape = null;
        this.defaultSize = null;
        this.defaultEdgesColor = null;
        this.defaultVertexColor = null;
        this.defaultVertexThickness = null;
        this.defaultEdgesThickness = null;
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
