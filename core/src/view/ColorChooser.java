package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by paubreton on 15/02/15.
 */
public class ColorChooser extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel        contentPane;
    private JPanel        colorPane;
    private JPanel        buttonsPane;
    private JColorChooser colorchooser;
    private JButton       buttonOK;
    private JButton       buttonCancel;
    private Color         currentColor;

    public ColorChooser(Color bg) {
        initComponents(bg);

        getRootPane().setDefaultButton(this.buttonOK);
        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }

    public void initComponents(Color bg) {
        this.setTitle("Color chooser");

        this.contentPane = new JPanel();
        this.buttonsPane = new JPanel(new GridLayout(1,2));
        this.colorPane   = new JPanel();

        this.setContentPane(contentPane);


        this.colorchooser = new JColorChooser(bg);
        this.currentColor = bg;


        this.buttonOK     = new JButton("Ok");
        this.buttonCancel = new JButton("Cancel");

        this.contentPane.add(this.colorPane);
        this.contentPane.add(this.buttonsPane);
        this.colorPane.add(this.colorchooser);
        this.buttonsPane.add(this.buttonOK);
        this.buttonsPane.add(this.buttonCancel);

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
