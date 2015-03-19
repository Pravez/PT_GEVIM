package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElementsEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField elementsSize;
    private JPanel elementsColor;
    private JTextField elementsName;
    private JCheckBox validNames;
    private JCheckBox validSize;
    private JCheckBox validColor;

    private Color selectedColor;
    private int selectedSize;
    private String selectedName;

    private boolean alreadyValidated;
    private boolean cancelled;

    public ElementsEditor(Component parent) {

        this.setTitle("Editeur d'elements");

        initComponents();
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(parent);
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

    private void initComponents(){
        validNames.setSelected(true);
        validColor.setSelected(true);
        validSize.setSelected(true);

        selectedColor = Color.BLACK;
        selectedSize = 15;
        selectedName = "element";

        elementsColor.setBackground(Color.black);
        elementsSize.setText("15");
        elementsName.setText("element");
        alreadyValidated = false;

        elementsColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(elementsColor);
            }
        });
    }

    private void onOK() {
        cancelled = false;

        if(!verifyModifications()) {
            if (!alreadyValidated && Integer.parseInt(this.elementsSize.getText()) < 5) {
                JOptionPane.showMessageDialog(this, "Attention vous editez des noeuds, il se pourrait qu'ils soient trop petits.", "Information", JOptionPane.INFORMATION_MESSAGE);
                alreadyValidated = true;
            } else {
                dispose();
            }
        }
    }

    private void onCancel() {
        cancelled = true;
        dispose();
    }

    private void onColor(JPanel j){
        ColorChooser cc = new ColorChooser(j.getBackground());
        j.setBackground(cc.getColor());
    }

    private boolean verifyModifications(){
        boolean mustBeVerified = false;

        if(validNames.isSelected()) {
            selectedName = this.elementsName.getText();
        }
        if(validColor.isSelected()) {
            selectedColor = this.elementsColor.getBackground();
        }

        if(validSize.isSelected() && Integer.parseInt(this.elementsSize.getText())<=0){
            JOptionPane.showMessageDialog(this, "La taille doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
            this.elementsSize.setText("15");
            mustBeVerified = true;
        }

        if(!mustBeVerified && validSize.isSelected()){
            selectedSize = Integer.parseInt(this.elementsSize.getText());
        }

        return mustBeVerified;
    }


    public boolean isColorModified() {return validColor.isSelected();}
    public boolean isLabelModified() {return validNames.isSelected();}
    public boolean isSizeModified() {return validSize.isSelected();}
    public int getNewSize () {return selectedSize;}
    public Color getNewColor () {return selectedColor;}
    public String getNewName(){return selectedName;}

    public boolean isCancelled() {
        return cancelled;
    }
}
