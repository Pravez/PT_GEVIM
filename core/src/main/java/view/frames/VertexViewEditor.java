package view.frames;

import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Paul Breton
 * Classe "visuelle" qui permet de modifier toutes les données d'un {@link data.Vertex} avec une interface graphique.
 */
public class VertexViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel                  contentPane;
    private JPanel                  vertexColoration;

    private JButton                 buttonOK;
    private JButton                 buttonCancel;

    private JTextField              vertexName;
    private JTextField              vertexX;
    private JTextField              vertexY;
    private JTextField              vertexWidth;

    private JComboBox<Vertex.Shape> vertexShape;

    private Color newColor;
    private String newLabel;
    private Point newPosition;
    private int newWidth;
    private Vertex.Shape newShape;

    private Vertex initialVertex;

    private boolean colorModified;
    private boolean labelModified;
    private boolean positionModified;
    private boolean widthModified;
    private boolean shapeModified;

    private boolean    alreadyValidated;
    private boolean    cannotQuit;

    /**
     * Constructeur qui prend les données d'un {@link view.editor.elements.VertexView} et les associe à chaque champ du JDialog
     * @param v le vecteur utilisé
     */
    public VertexViewEditor(Vertex v, Component parent) {

        setTitle("Editeur de noeuds");

        initComponents(v);

        setLocationRelativeTo(parent);
        setContentPane(this.contentPane);
        setModal(true);
        getRootPane().setDefaultButton(this.buttonOK);

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

        this.pack();
        this.setVisible(true);
    }

    private void initComponents(Vertex v){

        this.labelModified = false;
        this.widthModified = false;
        this.colorModified = false;
        this.shapeModified = false;
        this.positionModified = false;

        this.initialVertex = v;
        this.newLabel = v.getLabel();
        this.newColor = v.getColor();
        this.newWidth = v.getSize();
        this.newShape = v.getShape();
        this.newPosition = v.getPosition();

        this.vertexWidth.setText(String.valueOf(this.newWidth));
        this.vertexX.setText(String.valueOf(this.newPosition.x));
        this.vertexY.setText(String.valueOf(this.newPosition.y));

        if(this.newLabel !=null) this.vertexName.setText(String.valueOf(this.newLabel));
        else this.vertexName.setText("");

        switch(this.newShape){
            case CIRCLE:
                this.vertexShape.setSelectedIndex(1);
                break;
            case CROSS:
                this.vertexShape.setSelectedIndex(3);
                break;
            case TRIANGLE:
                this.vertexShape.setSelectedIndex(2);
                break;
            case SQUARE:
                this.vertexShape.setSelectedIndex(0);
                break;
            default:
                break;

        }

        //Used to inform the user one time
        alreadyValidated = false;

        //Détermine si l'utilisateur peut quitter ou non la fenêtre
        cannotQuit = false;

        //Setting the newColor
        vertexColoration.setBackground(this.newColor);
        vertexColoration.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor();
            }
        });

    }

    /**
     * Méthode appellée à l'appui du bouton OK, qui enregistre toutes les données modifiées.
     */
    private void onOK() {

        cannotQuit = verifyModifications();

        switch((String)this.vertexShape.getSelectedItem()){
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

        if(!alreadyValidated && Integer.parseInt(this.vertexWidth.getText())<5){
            JOptionPane.showMessageDialog(this, "Attention, votre noeud peut s'avérer être trop petit.", "Information", JOptionPane.INFORMATION_MESSAGE);
            alreadyValidated = true;
        }else {
            if(!cannotQuit) {
                hasBeenModified();
                dispose();
            }
        }
    }

    /**
     * Méthode appellée à l'appui du Cancel, qui quitte le JDialog sans rien enregistrer
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Permet d'ouvrir une nouvelle fenêtre d'édition de couleurs.
     */
    private void onColor(){
        ColorChooser cc = new ColorChooser(this.vertexColoration.getBackground());
        this.vertexColoration.setBackground(cc.getColor());
    }


    /**
     * Méthode locale permettant de vérifier la modification des informations par l'utilisateur.
     * @return True si les données doivent être revues, false sinon
     */
    private boolean verifyModifications(){

        boolean mustBeVerified = false;
        this.newLabel = (this.vertexName.getText());
        this.newColor = (this.vertexColoration.getBackground());

        //Verifie la taille
        if(Integer.parseInt(this.vertexWidth.getText()) <= 0){
            JOptionPane.showMessageDialog(this, "La taille ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexWidth.setText(String.valueOf(this.newWidth));
            mustBeVerified = true;
        }else{
            this.newWidth = (Integer.parseInt(this.vertexWidth.getText()));
        }

        //Vérifie la newPosition
        if(Integer.parseInt(this.vertexX.getText())<=0){
            JOptionPane.showMessageDialog(this, "La newPosition x ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexX.setText(String.valueOf(this.newPosition.x));
            mustBeVerified = true;
        }else if(Integer.parseInt(this.vertexY.getText()) <= 0){
            JOptionPane.showMessageDialog(this, "La newPosition y ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexY.setText(String.valueOf(this.newPosition.y));
            mustBeVerified = true;
        }else {
            this.newPosition = (new Point(Integer.parseInt(this.vertexX.getText()), Integer.parseInt(this.vertexY.getText())));
        }

        return mustBeVerified;
    }

    private void hasBeenModified(){

        if (newLabel.equals(initialVertex.getLabel())) {
            labelModified = true;
        }
        if (newPosition == initialVertex.getPosition()) {
            positionModified = true;
        }
        if (newColor == initialVertex.getColor()) {
            colorModified = true;
        }
        if (newWidth == initialVertex.getSize()) {
            widthModified = true;
        }
        if (newShape == initialVertex.getShape()) {
            shapeModified = true;
        }
    }


    public Color getNewColor() {
        return newColor;
    }

    public String getNewLabel() {
        return newLabel;
    }

    public Point getNewPosition() {
        return newPosition;
    }

    public int getNewWidth() {
        return newWidth;
    }

    public Vertex.Shape getNewShape(){
        return newShape;
    }

    public boolean isColorModified() {
        return colorModified;
    }

    public boolean isLabelModified() {
        return labelModified;
    }

    public boolean isPositionModified() {
        return positionModified;
    }

    public boolean isWidthModified() {
        return widthModified;
    }

    public boolean isShapeModified() {
        return shapeModified;
    }
}