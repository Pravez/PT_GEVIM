package view;

import algorithm.Property;
import view.frames.ColorChooser;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.*;
import java.util.Vector;

public class AlgorithmSelector extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox algorithms;
    private JPanel colorMin;
    private JPanel colorMax;
    private JComboBox propertyChooser;

    public AlgorithmSelector() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Vector<String>algoNames = new Vector<>();
        algoNames.add("Positionnement Aléatoire");
        algoNames.add("Positionnement Circulaire");
        algoNames.add("Coloration des Sommets");
        ComboBoxModel<String> algoModel = new DefaultComboBoxModel<>(algoNames);
        algorithms.setModel(algoModel);

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
        algorithms.addComponentListener(new ComponentAdapter() {
        });
        algorithms.addFocusListener(new FocusAdapter() {
        });
        algorithms.addMouseListener(new MouseAdapter() {
        });

        colorMax.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor(colorMax);
            }
        });
        colorMin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onColor(colorMin);
            }
        });
    }

    private void onOK() {

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

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
