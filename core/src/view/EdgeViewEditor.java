package view;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.*;

public class EdgeViewEditor extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField edgeThickness;
    private JComboBox originVertex;
    private JComboBox destinationVertex;
    private JPanel edgeViewColor;

    private Edge edge;
    private Graph graph;

    public EdgeViewEditor(Edge edge, Graph graph) {

        initComponents(edge, graph);
        this.setTitle("Edge editor");

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
    }

    private void initComponents(Edge edge, Graph graph) {

        String positionToString = "";

        this.edge = edge;
        this.graph = graph;
        edgeThickness.setText(String.valueOf(edge.getThickness()));

        for(Vertex v : this.graph.getVertexes()){
            positionToString = v.getPosition().toString();
            originVertex.addItem(positionToString);
            destinationVertex.addItem(positionToString);
            if(edge.getOrigin()==v){
                originVertex.setSelectedItem(positionToString);
            }
            if(edge.getDestination()==v){
                destinationVertex.setSelectedItem(positionToString);
            }
        }

        edgeViewColor.setBackground(edge.getColor());
        edgeViewColor.addMouseListener(new MouseListener() {
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


    }

    private void onOK() {

        /*this.edgeView.setColor(this.edgeViewColor.getBackground());
        this.edgeView.setThickness(Integer.parseInt(this.edgeThickness.getText()));

        this.edgeView.setOrigin(((Tab)this.edgeView.getParent()).getVertexAt(getPointFromString((String) this.originVertex.getSelectedItem())));
        this.edgeView.setDestination(((Tab) this.edgeView.getParent()).getVertexAt(getPointFromString((String) this.destinationVertex.getSelectedItem())));

*/
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onColor(){

        ColorChooser cc = new ColorChooser(this.edgeViewColor.getBackground());
        this.edgeViewColor.setBackground(cc.getColor());

    }

    private Point getPointFromString(String string){

        String position = string;
        position.replace("(", "");
        position.replace(")", "");
        char[] positionArray = position.toCharArray();

        int firstParameter=0;
        int secondParameter=0;
        String toBeParsed = "";

        for(int i=0; i<positionArray.length;i++){

            if(positionArray[i] != ','){

                toBeParsed+=positionArray[i];
            } else if(i != positionArray.length) {

                firstParameter = Integer.parseInt(toBeParsed);
                toBeParsed = "";
            }else {
                secondParameter = Integer.parseInt(toBeParsed);
            }

        }

        Point createdPoint = new Point(firstParameter, secondParameter);

        return createdPoint;
    }

    public Edge getModifiedEdge(){ return this.edge; }
}
