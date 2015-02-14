package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Paul Breton
 * Class to edit a VertexView with all its informations
 */
public class VertexViewEditor extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField vertexName;
    private JTextField vertexX;
    private JTextField vertexY;
    private JTextField vertexColor;
    private JTextField vertexWidth;
    private JComboBox vertexShape;

    private VertexView vertex;

    /**
     * Constructeur qui prend les données d'un {@link view.VertexView} et les associe à chaque champ du JDialog
     * @param v le vecteur utilisé
     */
    public VertexViewEditor(VertexView v) {

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


        vertex = v;

        vertexX.setText(String.valueOf(vertex.getPosition().x));
        vertexY.setText(String.valueOf(vertex.getPosition().y));
        vertexColor.setText(Integer.toHexString(vertex.getColor().getRGB()));
        vertexName.setText(String.valueOf(vertex.getName()));
        vertexWidth.setText(String.valueOf(vertex.getWidth()));
        if(vertex.getShape() == VertexView.Shape.CIRCLE){
            vertexShape.setSelectedIndex(1);
        }else{
            vertexShape.setSelectedIndex(0);
        }

        this.pack();
        this.setVisible(true);


    }

    /**
     * Méthode appellée à l'appui du bouton OK, qui enregistre toutes les données modifiées.
     */
    private void onOK() {

        vertex.setName(vertexName.getText());
        vertex.setWidth(Integer.parseInt(vertexWidth.getText()));
        vertex.setPosition(new Point(Integer.parseInt(vertexX.getText()), Integer.parseInt(vertexY.getText())));
        //vertex.setColor(Color.decode("#"+vertexColor.getText()));
        if(vertexShape.getSelectedItem() == "Circle"){
            vertex.setShape(VertexView.Shape.CIRCLE);
        }else{
            vertex.setShape(VertexView.Shape.SQUARE);
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
     * Renvoie les données du VertexView qui a été modifié
     * @return le VertexView modifié
     */
    public VertexView getModifiedVertex(){
        return vertex;
    }
}
