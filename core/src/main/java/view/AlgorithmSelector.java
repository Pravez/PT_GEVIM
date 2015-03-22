package view;

import algorithm.Property;
import view.frames.ColorChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public AlgorithmSelector() {

        this.setTitle("Selectionner un Algorithme à appliquer");
        initComponents();
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


        this.pack();
        this.setVisible(true);
    }

    private void initComponents(){

        colorMax.setBackground(Color.red);
        colorMin.setBackground(Color.green);

        colorMax.setVisible(false);
        colorMin.setVisible(false);
        minColorLabel.setVisible(false);
        maxColorLabel.setVisible(false);

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
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String)itemEvent.getItem()) {
                        case "Positionnement Aléatoire":
                            colorMax.setVisible(false);
                            colorMin.setVisible(false);
                            minColorLabel.setVisible(false);
                            maxColorLabel.setVisible(false);
                            changeSize("Random");
                            break;
                        case "Positionnement Circulaire":
                            colorMax.setVisible(false);
                            colorMin.setVisible(false);
                            minColorLabel.setVisible(false);
                            maxColorLabel.setVisible(false);
                            changeSize("Circle");
                            break;
                        case "Coloration des Sommets":
                            colorMax.setVisible(true);
                            colorMin.setVisible(true);
                            minColorLabel.setVisible(true);
                            maxColorLabel.setVisible(true);
                            changeSize("Color");
                            break;
                    }
                }
            }
        });

        this.baseDimension = new Dimension(150,200);

    }

    private void onOK() {
        cancelled = false;
        dispose();
    }

    private void onCancel() {
        cancelled = true;
        dispose();
    }

    public Object getSelectedAlgorithm(){
        return algorithms.getSelectedItem();
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
            case "indice" : return Property.VALUE;
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
}
