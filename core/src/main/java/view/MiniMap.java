package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import view.elements.EdgeView;
import view.elements.VertexView;
import data.Edge;
import data.GraphElement;
import data.Observable;
import data.Vertex;

public class MiniMap extends JComponent implements Observer {

	private static final long serialVersionUID = 1L;
	
	private JScrollPane pane;
	private Tab tab;
	
	private ArrayList<EdgeView>    edges;
    private ArrayList<VertexView>  vertexes;
    
    /** Zone **/
    private Rectangle              selectionZone;
    private Color                  selectionColor;
    private Color                  selectionBorderColor;
	
	public MiniMap(JScrollPane pane, Tab tab) {
		this.tab      = tab;
		this.edges    = new ArrayList<EdgeView>();
        this.vertexes = new ArrayList<VertexView>();
        
        this.selectionColor           = new Color(172, 211, 244);
        this.selectionBorderColor     = new Color(107, 153, 189);
        this.selectionZone            = new Rectangle(0, 0, (int)1.0*500*500/2000, (int)1.0*500*500/2000);
        		//new Rectangle(0, 0, (int) (1.0*this.getWidth()* this.tab.getSize().width)/this.tab.getPreferredSize().width , (int) (1.0*this.getHeight() * this.tab.getSize().height/this.tab.getPreferredSize().height));
        System.out.println("Zone : " + selectionZone.width + ", " + selectionZone.height);
        this.setVisible(true);
	}
	
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

}
