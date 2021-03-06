package view.editor.display;

import data.Edge;
import data.GraphElement;
import data.Observable;
import data.Vertex;
import threading.UpdateThread;
import view.UIElements.CustomUIManager;
import view.editor.elements.EdgeView;
import view.editor.elements.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Classe MiniMap affichant une miniature de la feuille de dessin actuelle et permettant de s'y déplacer
 */
public class MiniMap extends JComponent implements Observer, AdjustmentListener {

	private static final long serialVersionUID = 1L;

    /* Le scrollPane conteneur de la feuille de dessin */
	private view.editor.ScrollPane pane;
    /* La feuille de dessin */
	private Sheet                  sheet;

    /* La liste des EdgeView affichées */
	private ArrayList<EdgeView>    edges;
    /* La liste des VertexView affichés */
    private ArrayList<VertexView>  vertices;
    
    /* Le rectangle de la zone de sélection */
    private Rectangle              selectionZone;

	/**
	 * Conbstructeur de la classe MiniMap
	 * @param pane le ScrollPane de la window
	 * @param sheet la Sheet, feuillde de dessin du Tab actuel
	 */
    public MiniMap(view.editor.ScrollPane pane, Sheet sheet) {
        this.pane     = pane;
        this.sheet    = sheet;
        this.edges    = new ArrayList<>();
        this.vertices = new ArrayList<>();

        updateSelectionZone();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveSelectionZone(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moveSelectionZone(e);
            }
        });
        this.setVisible(true);
    }

	/**
	 * Override de la méthode paintComponent pour dessiner le graph
	 * @param g les Graphics
	 */
	public void paintComponent(Graphics g) {
        g.setColor(CustomUIManager.minimapColor);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
		g.setColor(CustomUIManager.selectionColor);
		g.fillRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
		g.setColor(CustomUIManager.selectionBorderColor);
		g.drawRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
        for(EdgeView e : this.edges){
            e.paintComponent(g, 1.0*this.getWidth()/this.sheet.getMaximumSize().width, 1.0*this.getHeight()/this.sheet.getMaximumSize().height, false);
        }
        for (VertexView v : this.vertices) {
            v.paintComponent(g, 1.0*this.getWidth()/this.sheet.getMaximumSize().width, 1.0*this.getHeight()/this.sheet.getMaximumSize().height, false);
        }
    }

    /**
     * Override de la méthode update de la classe Observer permettant de mettre à jour l'affichage en fonction des données qui ont été modifiées
     * @param observable la classe observée
     * @param object l'objet de la classe observée ayant été modifié
     */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object object) {
		this.vertices.clear();
        this.edges.clear();
		super.removeAll();
		// pour chaque GraphElement du Graph
        ArrayList<GraphElement> elements = (ArrayList<GraphElement>)object;

        if(elements.size()<1000) {
            // pour chaque GraphElement du Graph
            for (GraphElement element : elements) {
                if (element.isVertex()) {
                    addVertex((Vertex) element);
                }
            }

            for (GraphElement element : elements) {
                if (!element.isVertex()) {
                    VertexView src = null, dst = null;
                    ListIterator<VertexView> search = vertices.listIterator();

                    while (search.hasNext() && (src == null || dst == null)) {
                        VertexView tmp = search.next();
                        if (tmp.getVertex().getID() == ((Edge) element).getOrigin().getID()) src = tmp;
                        else if (tmp.getVertex().getID() == ((Edge) element).getDestination().getID()) dst = tmp;
                    }
                    if (src != null && dst != null) addEdge((Edge) element, src, dst);
                }
            }
        }else{
            UpdateThread updateThread = new UpdateThread(this, elements, this.sheet.getController());

            this.vertices.addAll(updateThread.getVertices());
            this.edges.addAll(updateThread.getEdges());
        }

        updateSelectionZone();
		this.repaint();
	}
	
	/**
     * Méthode permettant d'ajouter un VertexView à la position désirée
     * @param vertex le modèle Vertex du VertexView
     */
    public void addVertex(Vertex vertex){
    	VertexView vertexView = new VertexView(vertex);
        this.vertices.add(vertexView);
        super.add(vertexView);
    }
    
    /**
     * Méthode permettant d'ajouter un EdgeView
     * @param edge le modèle Edge de l'EdgeView
     * @param origin le VertexView d'origine de l'EdgeView
     * @param destination le VertexView de destination de l'EdgeView
     */
    public void addEdge(Edge edge, VertexView origin, VertexView destination ){
    	EdgeView edgeView = new EdgeView(edge, 2, origin, destination);
        this.edges.add(edgeView);
        super.add(edgeView);
    }

    /**
     * Override de la méthode lorsque l'on déplace les ScrollBar des ScrollPane
     * @param adjustmentEvent l'événement d'ajustement des ScrollBar
     */
	@Override
	public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        updateSelectionZone();
	}

	/**
	 * Méthode permettant de mettre à jour la zone de sélection (viewport actuel) en fonction de la dimension des fenêtres
     * lorsque les scrollbar sont déplacées
	 */
	public void updateSelectionZone() {
        int x      = (int)(1.0*this.getPreferredSize().width*this.pane.getHorizontalScrollBar().getValue()/this.sheet.getPreferredSize().width);
		int y      = (int)(1.0*this.getPreferredSize().height*this.pane.getVerticalScrollBar().getValue()/this.sheet.getPreferredSize().height);
		int width  = (int)(1.0*this.getPreferredSize().width*this.pane.getViewport().getSize().width/this.sheet.getPreferredSize().width);
		int height = (int)(1.0*this.getPreferredSize().height*this.pane.getViewport().getSize().height/this.sheet.getPreferredSize().height);
		this.selectionZone = new Rectangle(x, y, width, height);
        this.repaint();
	}

	/**
	 * Méthode appelée pour mettre à jour la position de la zone de sélection dans le JScrollPane selon la position sur la MiniMap
	 * @param e l'événement souris
	 */
	private void moveSelectionZone(MouseEvent e) {
		Point origin = new Point(e.getX() - this.selectionZone.width/2, e.getY() - this.selectionZone.height/2);
		this.pane.getHorizontalScrollBar().setValue((int) (1.0*origin.x*this.sheet.getSize().width/this.getPreferredSize().width));
		this.pane.getVerticalScrollBar().setValue((int) (1.0*origin.y*this.sheet.getSize().height/this.getPreferredSize().height));
	}
}