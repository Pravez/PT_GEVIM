package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

/**
 * Created by paubreton on 15/02/15.
 */
public class ColorChooser extends JDialog {

    private JPanel contentPane;
    private JColorChooser colorchooser;
    private JButton buttonOK;
    private JButton buttonCancel;
    private Color currentColor;


    public ColorChooser(Color bg){

        initComponents(bg);

        getRootPane().setDefaultButton(this.buttonOK);
        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }

    public void initComponents(Color bg){

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        colorchooser = new JColorChooser(bg);
        this.currentColor = bg;


        buttonOK = new JButton("Ok");
        buttonCancel = new JButton("Cancel");

        contentPane.add(colorchooser);
        contentPane.add(buttonOK);
        contentPane.add(buttonCancel);



        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        this.buttonCancel.addActionListener(new ActionListener() {
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
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK(){
        this.currentColor = colorchooser.getColor();
        dispose();
    }

    private void onCancel(){
        dispose();
    }

    public Color getColor(){
        return this.currentColor;
    }
}
