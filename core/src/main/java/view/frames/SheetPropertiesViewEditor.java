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
    JLabel jl_defaultVertexesColor;
    JLabel jl_defaultEdgesColor;

    JLabel jl_defaultVertexesSize;
    JLabel jl_defaultEdgesThickness;
    JLabel jl_defaultVertexesShape;

    // Attributes for modifications
    private JPanel defaultBackgroundColor;
    private JPanel defaultVertexesColor;
    private JPanel defaultEdgesColor;

    private JTextField defaultVertexesSize;
    private JTextField defaultEdgesThickness;

    private JComboBox<Vertex.Shape> defaultVertexesShape;

    private Sheet tab;

    public SheetPropertiesViewEditor(Sheet tab) {

        // Initialisation des attributs de propriété
        this.tab = tab;
        this.defaultBackgroundColor = new JPanel();
        this.defaultVertexesColor = new JPanel();
        this.defaultEdgesColor = new JPanel();
        this.defaultVertexesSize = new JTextField();
        this.defaultEdgesThickness = new JTextField();
        this.defaultVertexesShape = new JComboBox<>();


        this.defaultBackgroundColor.setBackground(Color.LIGHT_GRAY); /// En attente d'implémentation dans la classe Tab
        this.defaultVertexesColor.setBackground(this.tab.getDefaultVertexesColor());
        this.defaultEdgesColor.setBackground(this.tab.getDefaultVertexesColor());

        this.defaultVertexesSize.setText(String.valueOf(this.tab.getDefaultVertexesSize()));
        this.defaultEdgesThickness.setText(String.valueOf(this.tab.getDefaultEdgesThickness()));

        this.defaultVertexesShape.addItem(Vertex.Shape.SQUARE);
        this.defaultVertexesShape.addItem(Vertex.Shape.CIRCLE);
        this.defaultVertexesShape.addItem(Vertex.Shape.TRIANGLE);
        this.defaultVertexesShape.addItem(Vertex.Shape.CROSS);

        switch(this.tab.getDefaultVertexesShape()){
            case SQUARE:
                this.defaultVertexesShape.setSelectedIndex(0);
                break;
            case CIRCLE:
                this.defaultVertexesShape.setSelectedIndex(1);
                break;
            case TRIANGLE:
                this.defaultVertexesShape.setSelectedIndex(2);
                break;
            case CROSS:
                this.defaultVertexesShape.setSelectedIndex(3);
                break;
            default:
                break;
        }

        // Initialisation des différents éléments graphiques, d'organisation,et labels
        this.jl_defaultBackgroundColor = new JLabel("Background color (not implemented)");
        this.jl_defaultVertexesColor = new JLabel("Vertexes color : ");
        this.jl_defaultEdgesColor = new JLabel("Edges color : ");
        this.jl_defaultVertexesSize = new JLabel("Vertexes size : ");
        this.jl_defaultVertexesShape = new JLabel("Vertexes shape : ");
        this.jl_defaultEdgesThickness = new JLabel("Edges thickness : ");

        contentLayout = new GridBagLayout();
        contentPane = new JPanel();
        constraintsLayout = new GridBagConstraints();

        buttonOK = new JButton();
        buttonCancel = new JButton();

        buttonOK.setText("Ok");
        buttonCancel.setText("Cancel");

        // Propriétés de la fenêtre et positionnement de ses éléments
        setTitle("Sheet Properties");
        this.setPreferredSize(new Dimension(500,500));
        setLocationRelativeTo(null);
        setModal(true);
        setContentPane(this.contentPane);
        contentPane.setLayout(contentLayout);
        //contentPane.add(jl_defaultBackgroundColor,constraintsLayout); à traiter...
        //contentPane.add(defaultBackgroundColor,constraintsLayout);

        constraintsLayout.fill = GridBagConstraints.HORIZONTAL;
        constraintsLayout.gridx=0;
        constraintsLayout.gridy=0;
        contentPane.add(jl_defaultVertexesSize,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVertexesSize,constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=1;
        contentPane.add(jl_defaultVertexesShape,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVertexesShape, constraintsLayout);

        constraintsLayout.gridx=0;
        constraintsLayout.gridy=2;
        contentPane.add(jl_defaultVertexesColor,constraintsLayout);
        constraintsLayout.gridx=1;
        contentPane.add(defaultVertexesColor,constraintsLayout);

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

        this.defaultVertexesColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor(defaultVertexesColor);
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

        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode appellée à l'appui du bouton OK, qui enregistre toutes les données modifiées.
     */
    private void onOK() {
        this.tab.setDefaultVertexesSize(Integer.valueOf(this.defaultVertexesSize.getText()));
        this.tab.setDefaultVertexesColor(this.defaultVertexesColor.getBackground());
        this.tab.setDefaultEdgesColor(this.defaultEdgesColor.getBackground());
        this.tab.setDefaultEdgesThickness(Integer.valueOf(defaultEdgesThickness.getText()));
        //this.tab.setDefaultBackgroundColor(this.defaultBackgroundColor.getBackground());   ||** Background color à implémenter dans Tab !!

        switch(this.defaultVertexesShape.getSelectedItem().toString()){
            case "CIRCLE":
                this.tab.setDefaultVertexesShape(Vertex.Shape.CIRCLE);
                break;
            case "SQUARE":
                this.tab.setDefaultVertexesShape(Vertex.Shape.SQUARE);
                break;
            case "CROSS":
                this.tab.setDefaultVertexesShape(Vertex.Shape.CROSS);
                break;
            case "TRIANGLE":
                this.tab.setDefaultVertexesShape(Vertex.Shape.TRIANGLE);
                break;
            default:
                System.out.println("DEFAULT");
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