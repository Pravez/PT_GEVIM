package view;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;
import java.awt.event.*;

public class EdgeViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel            contentPane;
    private JButton           buttonOK;
    private JButton           buttonCancel;
    private JTextField        edgeThickness;
    private JComboBox<String> originVertex;
    private JComboBox<String> destinationVertex;
    private JPanel            edgeViewColor;

    private Edge              edge;
    private Graph             graph;

    public EdgeViewEditor(Edge edge, Graph graph) {
        this.setTitle("Edge editor");

        initComponents(edge, graph);

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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    private void initComponents(Edge edge, Graph graph) {

        this.edge  = edge;
        this.graph = graph;
        this.edgeThickness.setText(String.valueOf(edge.getThickness()));

        String vertexLabel = "";

        for(Vertex v : this.graph.getVertexes()){
            vertexLabel = v.getLabel();

            this.originVertex.addItem(vertexLabel);
            this.destinationVertex.addItem(vertexLabel);

            if (this.edge.getOrigin().getLabel() == vertexLabel) {
                this.originVertex.setSelectedItem(vertexLabel);
            } else if (this.edge.getDestination().getLabel() == vertexLabel) {
                this.destinationVertex.setSelectedItem(vertexLabel);
            }
        }

        this.edgeViewColor.setBackground(edge.getColor());
        this.edgeViewColor.addMouseListener(new MouseListener() {
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
    }

    private void onOK() {
        this.edge.setColor(this.edgeViewColor.getBackground());
        this.edge.setThickness(Integer.parseInt(this.edgeThickness.getText()));

        String originValue = ((String)originVertex.getSelectedItem());
        String destinationValue = ((String)destinationVertex.getSelectedItem());

        //Si le Vertex de départ est le même que celui d'arrivée
        if(originValue == destinationValue){
            JOptionPane.showMessageDialog(null, "Les vertex de départ et d'arrivée doivent être différents", "Erreur", JOptionPane.ERROR_MESSAGE);

        }else {
            this.edge.setOrigin(this.graph.getVertexes().get(this.graph.getElementIndexWithLabel(originValue)));
            this.edge.setDestination(this.graph.getVertexes().get(this.graph.getElementIndexWithLabel(destinationValue)));

            dispose();
        }
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
