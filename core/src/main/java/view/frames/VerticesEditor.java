package view.frames;

import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe visuelle d'édition de plusieurs {@link data.Vertex}
 */
public class VerticesEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField verticesLabel;
    private JTextField verticesSize;
    private JComboBox verticesShape;
    private JCheckBox colorModified;
    private JCheckBox labelModified;
    private JCheckBox sizeModified;
    private JCheckBox shapeModified;
    private JPanel verticesColor;
    private JTextField verticesIndex;
    private JCheckBox indexModified;
    private boolean alreadyValidated;

    private String newLabel;
    private int newSize;
    private Color newColor;
    private Vertex.Shape newShape;
    private int newIndex;

    private boolean notModified;

    /**
     * Constructeur de base
     * @param parent Le {@link java.awt.Component} appellant la fenêtre
     */
    public VerticesEditor(Component parent) {

        this.setTitle("Editeur de plusieurs noeuds");

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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode d'initialisation des principaux composants de la fenêtre
     */
    private void initComponents(){

        colorModified.setSelected(true);
        labelModified.setSelected(true);
        shapeModified.setSelected(true);
        sizeModified.setSelected(true);
        indexModified.setSelected(true);

        notModified = false;


        newLabel = "vertex";
        newColor = Color.BLACK;
        newShape = Vertex.Shape.SQUARE;
        newSize = 15;
        newIndex = 1;

        verticesLabel.setText(newLabel);
        verticesSize.setText(String.valueOf(newSize));
        verticesShape.setSelectedIndex(0);
        verticesColor.setBackground(newColor);
        verticesIndex.setText(String.valueOf(newIndex));

        alreadyValidated = false;

        verticesColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(verticesColor);
            }
        });



    }

    /**
     * Méthode appellée à l'appui du bouton OK. Elle appelle une méthode de vérification des données, et si
     * tout est bon, elle valide
     */
    private void onOK() {

        if(!verifyModifications()) {
            if (!alreadyValidated && Integer.parseInt(this.verticesSize.getText()) < 5) {
                JOptionPane.showMessageDialog(this, "Attention, votre noeud peut s'avérer être trop petit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                alreadyValidated = true;
            } else {
                dispose();
            }

        }
    }

    /**
     * Méthode appellée à l'appui du bouton Annuler. Elle ne "sauvegarde" aucune modification
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
     * Méthode de vérification des modifications. Si les modifications sont correctes, par exemple éviter de
     * rentrer des caractères à la place de nombre ...
     * @return True si tout est bon, false sinon
     */
    private boolean verifyModifications(){
        boolean mustBeVerified = false;

        if(labelModified.isSelected()) {
            newLabel = this.verticesLabel.getText();
        }
        if(colorModified.isSelected()) {
            newColor = this.verticesColor.getBackground();
        }

        try{
            if(sizeModified.isSelected() && Integer.parseInt(this.verticesSize.getText())<=0){
                JOptionPane.showMessageDialog(this, "La taille doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
                this.verticesSize.setText("15");
                mustBeVerified = true;
            }

            if(!mustBeVerified && sizeModified.isSelected()){
                newSize = Integer.parseInt(this.verticesSize.getText());
            }

            if(indexModified.isSelected()){
                newIndex = Integer.parseInt(this.verticesIndex.getText());
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Merci de rentrer une valeur entiere.", "Erreur", JOptionPane.ERROR_MESSAGE);
            mustBeVerified = true;
        }

        if(shapeModified.isSelected()){
            switch((String)this.verticesShape.getSelectedItem()){
                case "Cercle":
                    this.newShape = Vertex.Shape.CIRCLE;
                    break;
                case "Carré":
                    this.newShape = Vertex.Shape.SQUARE;
                    break;
                case "Croix":
                    this.newShape = Vertex.Shape.CROSS;
                    break;
                case "Triangle":
                    this.newShape = Vertex.Shape.TRIANGLE;
                    break;
                default:
                    break;
            }
        }

        return mustBeVerified;
    }

    public String getNewLabel() {
        return newLabel;
    }

    public int getNewSize() {
        return newSize;
    }

    public Color getNewColor() {
        return newColor;
    }

    public Vertex.Shape getNewShape() {
        return newShape;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public boolean isColorModified(){
        return colorModified.isSelected();
    }

    public boolean isLabelModified(){
        return labelModified.isSelected();
    }

    public boolean isSizeModified(){
        return sizeModified.isSelected();
    }

    public boolean isShapeModified(){
        return shapeModified.isSelected();
    }

    public boolean isIndexModified() {
        return indexModified.isSelected();
    }

    public boolean isNotModified() {
        return notModified;
    }


}
