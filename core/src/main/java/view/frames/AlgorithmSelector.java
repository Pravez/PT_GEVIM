package view.frames;

import algorithm.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe visuelle permettant de sélectionner un algorithme à appliquer sur un graphe.
 */
public class AlgorithmSelector extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox algorithms;
    private JPanel colorMin;
    private JPanel colorMax;
    private JComboBox propertyChooser;
    private JLabel minColorLabel;
    private JLabel maxColorLabel;

    private Dimension baseDimension;
    private boolean cancelled;

    /**
     * Constructeur par défaut de la classe
     * @param parent Le parent qui appelle l'instance de la classe, sert à placer la fenêtre
     */
    public AlgorithmSelector(Component parent) {

        this.baseDimension = new Dimension(250,175);
        this.setSize(baseDimension);
        this.setTitle("Selectionner un Algorithme");
        initComponents();
        this.setLocationRelativeTo(parent);
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


        this.setVisible(true);
        this.pack();



    }

    /**
     * Méthode privée d'initialisation des composants internes à la classe et à toutes ses données associées
     */
    private void initComponents(){

        colorMax.setBackground(Color.red);
        colorMin.setBackground(Color.green);

        colorMax.setVisible(false);
        colorMin.setVisible(false);
        minColorLabel.setVisible(false);
        maxColorLabel.setVisible(false);
        propertyChooser.setVisible(false);

        this.colorMin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(colorMin);
            }
        });

        this.colorMax.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(colorMax);
            }
        });

        this.algorithms.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                    switch ((String) itemEvent.getItem()) {
                        case "Positionnement Aléatoire":
                            showColors(false);
                            propertyChooser.setVisible(false);
                            changeSize("Random");
                            break;
                        case "Positionnement Circulaire":
                            showColors(false);
                            propertyChooser.setVisible(false);
                            changeSize("Circle");
                            break;
                        case "Coloration des Sommets":
                            showColors(true);
                            propertyChooser.setVisible(true);
                            changeSize("Color");
                            break;
                        case "Calcul des indices" :
                            colorMax.setVisible(false);
                            colorMin.setVisible(false);
                            minColorLabel.setVisible(false);
                            maxColorLabel.setVisible(false);
                            changeSize("Random");
                            break;
                        case "Changement de la taille" :
                            colorMax.setVisible(false);
                            colorMin.setVisible(false);
                            minColorLabel.setVisible(false);
                            maxColorLabel.setVisible(false);
                            changeSize("Random");
                            break;
                }
            }
        });
    }

    private void onOK() {
        cancelled = false;
        dispose();
    }

    private void onCancel() {
        cancelled = true;
        dispose();
    }

    /**
     * Retourne l'ensemble des propriétés sélectionnés pour l'exécution d'un algorithme.
     * @return Object[4] avec 0- nom de l'algorithme, 1- Couleur minimale 2- Couleur Maximale 3-Propriété sur laquelle on aplique la coloration.
     */
    public Object[] getSelectedAlgorithmProperties(){
        Object properties[] = new Object[4];
        properties[0] = algorithms.getSelectedItem();
        properties[1] = colorMin.getBackground();
        properties[2] = colorMax.getBackground();
        properties[3] = selectedProperty();
        return properties;
    }

    /**
     * retourne la propriété sélectionnée
     * @return
     */
    private Property selectedProperty(){
        switch ((String)propertyChooser.getSelectedItem()){
            case "Nombre d'arêtes" : return Property.NBEDGES;
            case "Taille" : return Property.SIZE;
            case "Position" : return Property.POSITION;
            case "Indice" : return Property.VALUE;
            case "Aléatoire" : return Property.RANDOM;
            default : return Property.NBEDGES;
        }
    }

    private void onColor(JPanel color){

        ColorChooser cc = new ColorChooser(color.getBackground());
        color.setBackground(cc.getColor());
    }

    private void changeSize(String type){
        switch(type){
            case "Random":
                this.setSize(baseDimension);
                break;
            case "Circle":
                this.setSize(baseDimension);
                break;
            case "Color":
                this.setSize(baseDimension);
                this.setSize(this.getSize().width+200, this.getSize().height);
                break;
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void showColors(boolean showed){
        colorMax.setVisible(showed);
        colorMin.setVisible(showed);
        minColorLabel.setVisible(showed);
        maxColorLabel.setVisible(showed);
    }
}
