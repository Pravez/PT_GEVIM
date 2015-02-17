package view;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EdgeViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;
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

        this.setTitle("Edge editor");

        initComponents(edge, graph);


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        this.edge.setColor(this.edgeViewColor.getBackground());
        this.edge.setThickness(Integer.parseInt(this.edgeThickness.getText()));

        /*this.edgeView.setOrigin(((Tab)this.edgeView.getParent()).getVertexAt(getPointFromString((String) this.originVertex.getSelectedItem())));
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

    public Edge getModifiedEdge(){ return this.edge; }
}
