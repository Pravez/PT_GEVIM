package view;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;
import java.awt.event.*;

/**
 * Classe permettant d'éditer un {@link data.Edge} avec plusieurs champs. C'est avant tout visuel avec plusieurs champs
 * pour modifier une à une chacune des propriétés de l'Edge concerné.
 */
public class EdgeViewEditor extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField edgeThickness;
    private JComboBox<String> originVertex;
    private JComboBox<String> destinationVertex;
    private JPanel edgeViewColor;

    private Edge edge;
    private Graph graph;

    /**
     * Constructeur de l'éditeur de {@link data.Edge}, il initialise les composants et lie les données du graphe
     * aux siennes.
     *
     * @param edge  Le edge concerné pour la modification
     * @param graph Le graphe dans lequel se situe le edge
     */
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

    /**
     * Lien entre les données de la fenêtre d'édition et celles du modèle ({@link data.Vertex}, {@link data.Edge} et {@link data.Graph})
     *
     * @param edge  Le edge concerné
     * @param graph Le graphe contenant le edge concerné
     */
    private void initComponents(Edge edge, Graph graph) {

        this.edge = edge;
        this.graph = graph;
        this.edgeThickness.setText(String.valueOf(edge.getThickness()));

        String vertexLabel = "";

        for (Vertex v : this.graph.getVertexes()) {
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

    /**
     * Méthode appellée à la validation de la fenêtre. Elle modifie toutes les informations contenues dans le vertex
     * qui était lié à la fenêtre, suivant les modifications faites par l'utilisateur. Enfin, elle ferme la fenêtre (mais ne
     * détruit pas l'instance).
     */
    private void onOK() {
        this.edge.setColor(this.edgeViewColor.getBackground());
        this.edge.setThickness(Integer.parseInt(this.edgeThickness.getText()));

        String originValue = ((String) originVertex.getSelectedItem());
        String destinationValue = ((String) destinationVertex.getSelectedItem());

        //Si le Vertex de départ est le même que celui d'arrivée
        if (originValue == destinationValue) {
            JOptionPane.showMessageDialog(null, "Les vertex de départ et d'arrivée doivent être différents", "Erreur", JOptionPane.ERROR_MESSAGE);

        } else {
            this.edge.setOrigin(this.graph.getVertexes().get(this.graph.getElementIndexWithLabel(originValue)));
            this.edge.setDestination(this.graph.getVertexes().get(this.graph.getElementIndexWithLabel(destinationValue)));

            dispose();
        }
    }

    /**
     * A l'appel du bouton Cancel : ferme la fenêtre
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Méthode appellée pour initialiser la fenêtre d'édition de couleurs.
     */
    private void onColor() {
        ColorChooser cc = new ColorChooser(this.edgeViewColor.getBackground());
        this.edgeViewColor.setBackground(cc.getColor());
    }

    /**
     * Méthode pour récupérer le {@link data.Edge} de la fenêtre qui a été modifié
     *
     * @return Le {@link data.Edge} associé à la fenêtre.
     */
    public Edge getModifiedEdge() {
        return this.edge;
    }
}
