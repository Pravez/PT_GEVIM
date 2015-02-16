package view;

import javax.swing.*;

import data.Vertex;

import java.awt.*;
import java.awt.event.*;

import static data.Vertex.Shape.*;

/**
 * Created by Paul Breton
 * Class to edit a VertexView with all its informations
 */
public class VertexViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel     contentPane;
    private JPanel vertexColoration;

    private JButton    buttonOK;
    private JButton    buttonCancel;

    private JTextField vertexName;
    private JTextField vertexX;
    private JTextField vertexY;
    private JTextField vertexWidth;

    private JComboBox  vertexShape;


    private Vertex     vertex;

    /**
     * Constructeur qui prend les données d'un {@link view.VertexView} et les associe à chaque champ du JDialog
     * @param v le vecteur utilisé
     */
    public VertexViewEditor(Vertex v) {

        setTitle("Vertex Editor");

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

        //Setting the color
        vertexColoration.setBackground(this.vertex.getColor());
        vertexColoration.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor();
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

        this.pack();
        this.setVisible(true);
    }

    /**
     * Méthode appellée à l'appui du bouton OK, qui enregistre toutes les données modifiées.
     */
    private void onOK() {
    	this.vertex.setLabel(this.vertexName.getText());
    	this.vertex.setSize(Integer.parseInt(this.vertexWidth.getText()));
    	this.vertex.setPosition(new Point(Integer.parseInt(this.vertexX.getText()), Integer.parseInt(this.vertexY.getText())));
        this.vertex.setColor(this.vertexColoration.getBackground());

        switch((String)this.vertexShape.getSelectedItem()){
            case "Circle":
                this.vertex.setShape(CIRCLE);
                break;
            case "Square":
                this.vertex.setShape(SQUARE);
                break;
            case "Cross":
                this.vertex.setShape(CROSS);
                break;
            case "Triangle":
                this.vertex.setShape(TRIANGLE);
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

    private void onColor(){

        ColorChooser cc = new ColorChooser(this.vertexColoration.getBackground());
        this.vertexColoration.setBackground(cc.getColor());

    }

    /**
     * Renvoie les données du Vertex qui a été modifié
     * @return le Vertex modifié
     */
    public Vertex getModifiedVertex(){
        return this.vertex;
    }

}