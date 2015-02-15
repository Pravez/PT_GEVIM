package view;

import javax.swing.*;

import data.Vertex;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Paul Breton
 * Class to edit a VertexView with all its informations
 */
public class VertexViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel     contentPane;
    private JButton    buttonOK;
    private JButton    buttonCancel;
    private JTextField vertexName;
    private JTextField vertexX;
    private JTextField vertexY;
    private JTextField vertexColor;
    private JTextField vertexWidth;
    //private JComboBox  vertexShape;

    private Vertex     vertex;

    /**
     * Constructeur qui prend les données d'un {@link view.VertexView} et les associe à chaque champ du JDialog
     * @param v le vecteur utilisé
     */
    public VertexViewEditor(Vertex v) {

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

        this.vertexX.setText(String.valueOf(this.vertex.getPosition().x));
        this.vertexY.setText(String.valueOf(this.vertex.getPosition().y));
        this.vertexColor.setText(Integer.toHexString(this.vertex.getColor().getRGB()));
        this.vertexName.setText(String.valueOf(this.vertex.getLabel()));
        this.vertexWidth.setText(String.valueOf(this.vertex.getSize()));
        /*if(this.vertex.getShape() == VertexView.Shape.CIRCLE) {
        	this.vertexShape.setSelectedIndex(1);
        } else {
        	this.vertexShape.setSelectedIndex(0);
        }*/

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
        //this.vertex.setColor(Color.decode("#"+this.vertexColor.getText()));
        /*if(this.vertexShape.getSelectedItem() == "Circle") {
        	this.vertex.setShape(VertexView.Shape.CIRCLE);
        } else {
        	this.vertex.setShape(VertexView.Shape.SQUARE);
        }*/
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
     * Renvoie les données du Vertex qui a été modifié
     * @return le Vertex modifié
     */
    public Vertex getModifiedVertex(){
        return this.vertex;
    }
}
