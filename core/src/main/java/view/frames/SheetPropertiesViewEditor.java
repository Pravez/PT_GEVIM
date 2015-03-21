package view.frames;

import data.Vertex;
import view.editor.Sheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Quentin Lemonnier
 * Classe "visuelle" qui permet de modifier toutes les données par défaut d'un {@link view.editor.Sheet} avec une interface graphique.
 */
public class SheetPropertiesViewEditor extends JDialog {

    /**
     * A AJOUTER AUX OPTIONS MODIFIABLES
     * -La taille de la Sheet
     * -Le nom de la sheet et/ou du fichier
     * -La zone d'application des algorithmes
     * -La couleur des arêtes
     * -La couleur du background du graph?
     *
     */

    private static final long serialVersionUID = 1L;

    // Graphic elements and buttons
    JPanel contentPane;
    GridBagLayout contentLayout;
    GridBagConstraints constraintsLayout;
    JButton buttonOK;
    JButton buttonCancel;

    // JLabel for each attribute
    JLabel jl_defaultBackgroundColor;
    JLabel jl_defaultVerticesColor;
    JLabel jl_defaultEdgesColor;

    JLabel jl_defaultVerticesSize;
    JLabel jl_defaultEdgesThickness;
    JLabel jl_defaultVerticesShape;

    // Attributes for modifications
    private JPanel defaultBackgroundColor;
    private JPanel defaultVerticesColor;
    private JPanel defaultEdgesColor;

    private JTextField defaultVerticesSize;
    private JTextField defaultEdgesThickness;

    private JComboBox<Vertex.Shape> defaultVerticesShape;

    private Sheet tab;

    public SheetPropertiesViewEditor(Sheet tab) {

        // Initialisation des attributs de propriété
        this.tab = tab;
        this.defaultBackgroundColor = new JPanel();
        this.defaultVerticesColor = new JPanel();
        this.defaultEdgesColor = new JPanel();
        this.defaultVerticesSize = new JTextField();
        this.defaultEdgesThickness = new JTextField();
        this.defaultVerticesShape = new JComboBox<>();


        this.defaultBackgroundColor.setBackground(Color.LIGHT_GRAY); /// En attente d'implémentation dans la classe Tab
        this.defaultVerticesColor.setBackground(this.tab.getDefaultVerticesColor());
        this.defaultEdgesColor.setBackground(this.tab.getDefaultEdgesColor());

        this.defaultVerticesSize.setText(String.valueOf(this.tab.getDefaultVerticesSize()));
        this.defaultEdgesThickness.setText(String.valueOf(this.tab.getDefaultEdgesThickness()));

        this.defaultVerticesShape.addItem(Vertex.Shape.SQUARE);
        this.defaultVerticesShape.addItem(Vertex.Shape.CIRCLE);
        this.defaultVerticesShape.addItem(Vertex.Shape.TRIANGLE);
        this.defaultVerticesShape.addItem(Vertex.Shape.CROSS);

        switch(this.tab.getDefaultVerticesShape()){
            case SQUARE:
                this.defaultVerticesShape.setSelectedIndex(0);
                break;
            case CIRCLE:
                this.defaultVerticesShape.setSelectedIndex(1);
                break;
            case TRIANGLE:
                this.defaultVerticesShape.setSelectedIndex(2);
                break;
            case CROSS:
                this.defaultVerticesShape.setSelectedIndex(3);
                break;
            default:
                break;
        }

        // Initialisation des différents éléments graphiques, d'organisation,et labels
        this.jl_defaultBackgroundColor = new JLabel("Couleur de fond :");
        this.jl_defaultVerticesColor = new JLabel("Couleur des noeuds :");
        this.jl_defaultEdgesColor = new JLabel("Couleur des arêtes :");
        this.jl_defaultVerticesSize = new JLabel("Taille des noeuds :");
        this.jl_defaultVerticesShape = new JLabel("Forme des noeuds :");
        this.jl_defaultEdgesThickness = new JLabel("Epaisseur des arêtes :");

        contentLayout = new GridBagLayout();
        contentPane = new JPanel();
        constraintsLayout = new GridBagConstraints();

        buttonOK = new JButton();
        buttonCancel = new JButton();

        buttonOK.setText("Ok");
        buttonCancel.setText("Annuler");

        // Propriétés de la fenêtre et positionnement de ses éléments
        setTitle("Propriétés");
        setModal(true);
        setContentPane(this.contentPane);
        contentPane.setLayout(contentLayout);
        //contentPane.add(jl_defaultBackgroundColor,constraintsLayout); à traiter...
        //contentPane.add(defaultBackgroundColor,constraintsLayout);

        constraintsLayout.insets = new Insets(2,2,2,2);
        constraintsLayout.fill = GridBagConstraints.HORIZONTAL;
        constraintsLayout.gridx=0;
        constraintsLayout.gridy=0;
        contentPane.add(jl_defaultVerticesSize,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVerticesSize,constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=1;
        contentPane.add(jl_defaultVerticesShape,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVerticesShape, constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=2;
        contentPane.add(jl_defaultVerticesColor,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVerticesColor,constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=3;
        contentPane.add(jl_defaultEdgesThickness,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultEdgesThickness,constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=4;
        contentPane.add(jl_defaultEdgesColor,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultEdgesColor,constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=5;
        contentPane.add(buttonCancel,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(buttonOK,constraintsLayout);

        // Setting the color
        this.defaultBackgroundColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor(defaultBackgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        this.defaultVerticesColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor(defaultVerticesColor);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        this.defaultEdgesColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor(defaultEdgesColor);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) { }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) { }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }

            @Override
            public void mouseExited(MouseEvent mouseEvent) { }
        });

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

        // call onOK() on ENTER
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setPreferredSize(new Dimension(280,225));
        setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode appellée à l'appui du bouton OK (ou de la touche ENTER), qui enregistre toutes les données modifiées.
     */
    private void onOK() {
        this.tab.setDefaultVerticesSize(Integer.valueOf(this.defaultVerticesSize.getText()));
        this.tab.setDefaultVerticesColor(this.defaultVerticesColor.getBackground());
        this.tab.setDefaultEdgesColor(this.defaultEdgesColor.getBackground());
        this.tab.setDefaultEdgesThickness(Integer.valueOf(defaultEdgesThickness.getText()));
        //this.tab.setDefaultBackgroundColor(this.defaultBackgroundColor.getBackground());   ||** Background color à implémenter dans Tab !!

        switch(this.defaultVerticesShape.getSelectedItem().toString()){
            case "Circle":
                this.tab.setDefaultVerticesShape(Vertex.Shape.CIRCLE);
                break;
            case "Square":
                this.tab.setDefaultVerticesShape(Vertex.Shape.SQUARE);
                break;
            case "Cross":
                this.tab.setDefaultVerticesShape(Vertex.Shape.CROSS);
                break;
            case "Triangle":
                this.tab.setDefaultVerticesShape(Vertex.Shape.TRIANGLE);
                break;
            default:
                break;
        }

        dispose();
    }

    /**
     * Méthode appellée à l'appui du Cancel, qui quitte le JDialog sans rien enregistrer
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Permet d'ouvrir une nouvelle fenêtre d'édition de couleurs.
     */
    private void onColor(JPanel j){
        ColorChooser cc = new ColorChooser(j.getBackground());
        j.setBackground(cc.getColor());
    }

    public Sheet getTab(){
        return tab;
    }
}