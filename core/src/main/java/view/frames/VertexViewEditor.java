package view.frames;

import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static data.Vertex.Shape.*;

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


    private Vertex     vertex;
    private boolean    alreadyValidated;
    private boolean    cannotQuit;

    /**
     * Constructeur qui prend les données d'un {@link view.editor.elements.VertexView} et les associe à chaque champ du JDialog
     * @param v le vecteur utilisé
     */
    public VertexViewEditor(Vertex v, Component parent) {

        setTitle("Editeur de noeuds");

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


        this.vertex = v;

        this.vertexWidth.setText(String.valueOf(this.vertex.getSize()));
        this.vertexX.setText(String.valueOf(this.vertex.getPosition().x));
        this.vertexY.setText(String.valueOf(this.vertex.getPosition().y));

        if(this.vertex.getLabel()!=null) this.vertexName.setText(String.valueOf(this.vertex.getLabel()));
        else this.vertexName.setText("");

        switch(this.vertex.getShape()){
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

        //Setting the color
        vertexColoration.setBackground(this.vertex.getColor());
        vertexColoration.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor();
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

        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode appellée à l'appui du bouton OK, qui enregistre toutes les données modifiées.
     */
    private void onOK() {

        cannotQuit = verifyModifications();

        switch((String)this.vertexShape.getSelectedItem()){
            case "Cercle":
                this.vertex.setShape(CIRCLE);
                break;
            case "Carré":
                this.vertex.setShape(SQUARE);
                break;
            case "Croix":
                this.vertex.setShape(CROSS);
                break;
            case "Triangle":
                this.vertex.setShape(TRIANGLE);
                break;
            default:
                break;
        }

        if(!alreadyValidated && Integer.parseInt(this.vertexWidth.getText())<5){
            JOptionPane.showMessageDialog(this, "Attention, votre noeud peut s'avérer être trop petit.", "Information", JOptionPane.INFORMATION_MESSAGE);
            alreadyValidated = true;
        }else {
            if(!cannotQuit) {
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
     * Renvoie les données du Vertex qui a été modifié, associé à l'instance de la classe {@link VertexViewEditor}
     * @return le Vertex modifié
     */
    public Vertex getModifiedVertex(){
        return this.vertex;
    }


    /**
     * Méthode locale permettant de vérifier la modification des informations par l'utilisateur.
     * @return True si les données doivent être revues, false sinon
     */
    private boolean verifyModifications(){

        boolean mustBeVerified = false;
        this.vertex.setLabel(this.vertexName.getText());
        this.vertex.setColor(this.vertexColoration.getBackground());

        //Verifie la taille
        if(Integer.parseInt(this.vertexWidth.getText()) <= 0){
            JOptionPane.showMessageDialog(this, "La taille ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexWidth.setText(String.valueOf(this.vertex.getSize()));
            mustBeVerified = true;
        }else{
            this.vertex.setSize(Integer.parseInt(this.vertexWidth.getText()));
        }

        //Vérifie la position
        if(Integer.parseInt(this.vertexX.getText())<=0){
            JOptionPane.showMessageDialog(this, "La position x ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexX.setText(String.valueOf(this.vertex.getPosition().x));
            mustBeVerified = true;
        }else if(Integer.parseInt(this.vertexY.getText()) <= 0){
            JOptionPane.showMessageDialog(this, "La position y ne peut être inférieure ou égale à 0.", "Attention", JOptionPane.ERROR_MESSAGE);
            this.vertexY.setText(String.valueOf(this.vertex.getPosition().y));
            mustBeVerified = true;
        }else {
            this.vertex.setPosition(new Point(Integer.parseInt(this.vertexX.getText()), Integer.parseInt(this.vertexY.getText())));
        }

        return mustBeVerified;
    }
}