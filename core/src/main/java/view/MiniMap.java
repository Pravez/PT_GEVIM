package view;

import data.Edge;
import data.GraphElement;
import data.Observable;
import data.Vertex;
import view.elements.EdgeView;
import view.elements.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class MiniMap extends JComponent implements Observer, AdjustmentListener {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane pane;
	private Tab tab;
	
	private ArrayList<EdgeView>    edges;
    private ArrayList<VertexView>  vertexes;
    
    /** Zone **/
    private Rectangle              selectionZone;
    private Color                  selectionColor;
    private Color                  selectionBorderColor;

	/**
	 * Conbstructeur de la classe MiniMap
	 * @param width la largeur de la MiniMap
	 * @param height la hauteur de la MiniMap
	 * @param pane le JScrollPane de la window
	 * @param tab le Tab actuel
	 */
	public MiniMap(int width, int height, JScrollPane pane, Tab tab) {
		this.setSize(width, height);
		this.pane     = pane;
		this.tab      = tab;
		this.edges    = new ArrayList<EdgeView>();
        this.vertexes = new ArrayList<VertexView>();
        
        this.selectionColor           = new Color(172, 211, 244);
        this.selectionBorderColor     = new Color(107, 153, 189);
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
		g.setColor(this.selectionColor);
		g.fillRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
		g.setColor(this.selectionBorderColor);
		g.drawRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
        for(EdgeView e : this.edges){
            e.paintComponent(g, new Point(0, 0), 1.0*this.getWidth()/this.tab.getPreferredSize().width, 1.0*this.getHeight()/this.tab.getPreferredSize().height);
        }

        for (VertexView v : this.vertexes) {
            v.paintComponent(g, new Point(0, 0), 1.0*this.getWidth()/this.tab.getPreferredSize().width, 1.0*this.getHeight()/this.tab.getPreferredSize().height);
        }
        
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object object) {
		this.vertexes.clear();
        this.edges.clear();
		super.removeAll();
		// pour chaque GraphElement du Graph
		for (GraphElement element : (ArrayList<GraphElement>)object) {
			if (element.isVertex()) {
				addVertex((Vertex)element);
			} else {
				VertexView src= null, dst = null;
	            ListIterator<VertexView> search = vertexes.listIterator();

	            while (search.hasNext() && (src == null || dst == null)){
	                VertexView tmp = search.next();
	                if (tmp.getVertex() == ((Edge) element).getOrigin()) src = tmp;
	                else if (tmp.getVertex() == ((Edge) element).getDestination()) dst = tmp;
	            }
	            if ( src != null && dst != null) addEdge((Edge) element, src, dst);
			}
		}
		this.repaint();
	}
	
	/**
     * Méthode permettant d'ajouter un VertexView à la position désirée
     * @param vertex le modèle Vertex du VertexView
     */
    public void addVertex(Vertex vertex){
    	VertexView vertexView = new VertexView(vertex, Color.BLUE);
        this.vertexes.add(vertexView);
        super.add(vertexView);
    }
    
    /**
     * Méthode permettant d'ajouter un EdgeView
     * @param edge le modèle Edge de l'EdgeView
     * @param origin le VertexView d'origine de l'EdgeView
     * @param destination le VertexView de destination de l'EdgeView
     */
    public void addEdge(Edge edge, VertexView origin, VertexView destination ){
    	EdgeView edgeView = new EdgeView(edge, 2, Color.BLUE, origin, destination);
        this.edges.add(edgeView);
        super.add(edgeView);
    }

	@Override
	public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
		updateSelectionZone();
		this.repaint();
	}

	/**
	 * Méthode permettant de mettre à jour la zone de sélection (viewport actuel) en fonction de la dimension des fenêtres
	 */
	private void updateSelectionZone() {
		int x      = (int)(1.0*this.getSize().width*this.pane.getHorizontalScrollBar().getValue()/this.tab.getSize().width);
		int y      = (int)(1.0*this.getSize().height*this.pane.getVerticalScrollBar().getValue()/this.tab.getSize().height);
		int width  = (int)(1.0*this.getSize().width*this.pane.getViewport().getSize().width/this.tab.getPreferredSize().width);
		int height = (int)(1.0*this.getSize().height*this.pane.getViewport().getSize().height/this.tab.getPreferredSize().height);
		this.selectionZone = new Rectangle(x, y, width, height);
	}

	/**
	 * Méthode appelée pour mettre à jour la position de la zone de sélection dans le JScrollPane selon la position sur la MiniMap
	 * @param e l'événement souris
	 */
	private void moveSelectionZone(MouseEvent e) {
		Point origin = new Point(e.getX() - this.selectionZone.width/2, e.getY() - this.selectionZone.height/2);
		this.pane.getHorizontalScrollBar().setValue((int) (1.0*origin.x*this.tab.getSize().width/this.getSize().width));
		this.pane.getVerticalScrollBar().setValue((int) (1.0*origin.y*this.tab.getSize().height/this.getSize().height));
	}
}