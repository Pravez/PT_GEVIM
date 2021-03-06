package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe d'édition de plusieurs éléments d'un graphe
 */
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
    private boolean notModified;

    /**
     * Constructeur par défaut
     * @param parent Le {@link java.awt.Component} l'ayant appelé
     */
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

    /**
     * Méthode d'initialisation de la plupart des composants
     */
    private void initComponents(){
        validNames.setSelected(true);
        validColor.setSelected(true);
        validSize.setSelected(true);

        notModified = false;

        selectedColor = Color.BLACK;
        selectedSize = 15;
        selectedName = "element";

        elementsColor.setBackground(selectedColor);
        elementsSize.setText(String.valueOf(selectedSize));
        elementsName.setText(selectedName);
        alreadyValidated = false;

        elementsColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(elementsColor);
            }
        });
    }

    /**
     * Méthode de validation appellée à l'appui du bouton OK. Elle appelle une méthode vérifiant l'ensemble de données,
     * et valide si l'ensemble est bon.
     */
    private void onOK() {

        if(!verifyModifications()) {
            if (!alreadyValidated && Integer.parseInt(this.elementsSize.getText()) < 5) {
                JOptionPane.showMessageDialog(this, "Attention vous editez des noeuds, il se pourrait qu'ils soient trop petits.", "Information", JOptionPane.INFORMATION_MESSAGE);
                alreadyValidated = true;
            } else {
                dispose();
            }
        }
    }

    /**
     * Méthode appelée à l'appui du bouton Annuler. N'enregistre aucune modification
     */
    private void onCancel() {
        notModified = true;
        dispose();
    }

    /**
     * Méthode appelant un sélecteur de couleurs
     * @param j Le {@link javax.swing.JPanel} dont la couleur doit être modifiée
     */
    private void onColor(JPanel j){
        ColorChooser cc = new ColorChooser(j.getBackground());
        j.setBackground(cc.getColor());
    }

    /**
     * Méthode de vérification de l'ensemble des modifications
     * @return True si tout est bon, false sinon
     */
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

    public boolean isNotModified() {
        return notModified;
    }
}
