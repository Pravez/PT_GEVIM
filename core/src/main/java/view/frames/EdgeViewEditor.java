package view.frames;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Classe permettant d'éditer un {@link data.Edge} avec plusieurs champs. C'est avant tout visuel avec plusieurs champs
 * pour modifier une à une chacune des propriétés de l'Edge concerné.
 */
public class EdgeViewEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel            contentPane;
    private JButton           buttonOK;
    private JButton           buttonCancel;
    private JTextField        edgeThickness;
    private JComboBox<String> originVertex;
    private JComboBox<String> destinationVertex;
    private JPanel            edgeViewColor;
    private JTextField        edgeViewName;

    private Graph             graph;
    private HashMap<String, Integer> verticesID;

    private String label;
    private Color color;
    private int width;
    private Vertex origin;
    private Vertex destination;

    private boolean labelModified;
    private boolean colorModified;
    private boolean widthModified;
    private boolean originModified;
    private boolean destinationModified;

    private Edge initialEdge;

    private boolean           cannotQuit;

    /**
     * Constructeur de l'éditeur de {@link data.Edge}, il initialise les composants et lie les données du graphe
     * aux siennes.
     * @param edge Le edge concerné pour la modification
     * @param graph Le graphe dans lequel se situe le edge
     */
    public EdgeViewEditor(Edge edge, Graph graph, Component parent) {
        this.setTitle("Editeur d'aretes");

        initComponents(edge, graph);

        setLocationRelativeTo(parent);
        setContentPane(this.contentPane);
        setModal(true);
        getRootPane().setDefaultButton(this.buttonOK);

        //Vérificateur de données
        cannotQuit = false;

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
     * @param edge Le edge concerné
     * @param graph Le graphe contenant le edge concerné
     */
    private void initComponents(Edge edge, Graph graph) {

        this.labelModified = false;
        this.widthModified = false;
        this.colorModified = false;
        this.originModified = false;
        this.destinationModified = false;

        this.initialEdge = edge;

        this.graph = graph;

        this.label = edge.getLabel();
        this.color = edge.getColor();
        this.width = edge.getThickness();
        this.origin = edge.getOrigin();
        this.destination = edge.getDestination();

        this.edgeThickness.setText(String.valueOf(this.width));
        this.edgeViewName.setText(this.label);

        verticesID = new HashMap<>();
        String currentOrigin;
        String currentDestination;

        for(Vertex v : this.graph.getVertexes()){
            verticesID.put(v.getLabel(), v.getValue());

            currentDestination = v.getLabel() + "  | "+v.getValue();
            currentOrigin = v.getLabel() + "  | "+v.getValue();

            this.originVertex.addItem(currentOrigin);
            this.destinationVertex.addItem(currentDestination);

            if (this.origin.getValue() == v.getValue()) {
                this.originVertex.setSelectedItem(currentOrigin);
            } else if (this.destination.getValue() == v.getValue()) {
                this.destinationVertex.setSelectedItem(currentDestination);
            }
        }

        this.edgeViewColor.setBackground(edge.getColor());
        this.edgeViewColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onColor();
            }
        });
    }

    /**
     * Méthode appellée à la validation de la fenêtre. Elle modifie toutes les informations contenues dans le vertex
     * qui était lié à la fenêtre, suivant les modifications faites par l'utilisateur. Enfin, elle ferme la fenêtre (mais ne
     * détruit pas l'instance).
     */
    private void onOK() {

        cannotQuit = verifyModifications();

        if(!cannotQuit) {
            hasBeenModified();
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
    private void onColor(){
        ColorChooser cc = new ColorChooser(this.edgeViewColor.getBackground());
        this.edgeViewColor.setBackground(cc.getColor());
    }

    /**
     * Méthode locale permettant de vérifier la modification des informations par l'utilisateur.
     * @return True si les données doivent être revues, false sinon
     */
    private boolean verifyModifications(){
         boolean mustBeVerified = false;

        this.color = this.edgeViewColor.getBackground();
        this.label = this.edgeViewName.getText();


        if(Integer.parseInt(this.edgeThickness.getText()) <= 0){
            JOptionPane.showMessageDialog(this, "La taille doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
            this.edgeThickness.setText(String.valueOf(this.width));
            mustBeVerified = true;
        }else {
            this.width = Integer.parseInt(this.edgeThickness.getText());
        }


        String originValue = ((String)originVertex.getSelectedItem());
        originValue = originValue.substring(originValue.indexOf('|')+2, originValue.length());
        String destinationValue = ((String)destinationVertex.getSelectedItem());
        destinationValue = destinationValue.substring(destinationValue.indexOf('|')+2, destinationValue.length());


        //Si le Vertex de départ est le même que celui d'arrivée
        if(originValue.equals(destinationValue)){
            JOptionPane.showMessageDialog(null, "Les vertex de départ et d'arrivée doivent être différents", "Erreur", JOptionPane.ERROR_MESSAGE);
            mustBeVerified = true;
        }else {

            Vertex tempOrigin = (Vertex)this.graph.getFromValue(Integer.parseInt(originValue));
            Vertex tempDestination = (Vertex)this.graph.getFromValue(Integer.parseInt(destinationValue));

            if((tempOrigin != this.origin || tempDestination!=this.destination) && this.graph.existsBetweenVertices(tempOrigin, tempDestination)){
                JOptionPane.showMessageDialog(null, "Il existe deja une arete entre ces deux sommets.", "Erreur", JOptionPane.ERROR_MESSAGE);
                mustBeVerified = true;
            }else{
                this.origin = tempOrigin;
                this.destination = tempDestination;
            }


        }

        return mustBeVerified;
    }

    private void hasBeenModified(){

        if (!label.equals(initialEdge.getLabel())) {
            labelModified = true;
        }
        if (width != initialEdge.getThickness()) {
            widthModified = true;
        }
        if (color != initialEdge.getColor()) {
            colorModified = true;
        }
        if (origin.getValue() != initialEdge.getOrigin().getValue()) {
            originModified = true;
        }
        if (destination.getValue() != initialEdge.getDestination().getValue()) {
            destinationModified = true;
        }
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public int getThickness() {
        return width;
    }

    public Color getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public boolean isLabelModified() {
        return labelModified;
    }

    public boolean isColorModified() {
        return colorModified;
    }

    public boolean isWidthModified() {
        return widthModified;
    }

    public boolean isOriginModified() {
        return originModified;
    }

    public boolean isDestinationModified() {
        return destinationModified;
    }
}
