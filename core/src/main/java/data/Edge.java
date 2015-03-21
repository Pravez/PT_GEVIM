package data;

import java.awt.Color;

/**
 * Created by cordavidenko on 26/01/15.
 * Classe Edge, arête entre deux Vertex, partie du modèle de l'application
 */
public class Edge extends GraphElement {

    private Vertex  origin;
    private Vertex  destination;
    private int     thickness;
    
    /**
     * Constructeur de la classe Edge
     * @param label l'étiquette de l'Edge
     * @param color la couleur de l'Edge
     * @param origin le Vertex d'origine de l'Edge
     * @param destination le Vertex de destination de l'Edge
     * @param thickness l'épaisseur de l'Edge
     */
    public Edge(String label, Color color, Vertex origin, Vertex destination, int thickness) {
    	super(label, color);
        this.origin      = origin;
        this.destination = destination;
        this.thickness   = thickness;
        origin.addEdge(this);
        destination.addEdge(this);
    }

    /**
     * Constructeur de la classe Edge
     * @param color la couleur de l'Edge
     * @param origin le Vertex d'origine de l'Edge
     * @param destination le Vertex de destination de l'Edge
     * @param thickness l'épaisseur de l'Edge
     */
    public Edge(Color color, Vertex origin, Vertex destination, int thickness) {
    	super("edge", color);
        this.origin      = origin;
        this.destination = destination;
        this.thickness   = thickness;
        origin.addEdge(this);
        destination.addEdge(this);
    }

    public Edge(Edge element) {
        super(element);
        this.origin = element.origin;
        this.destination = element.destination;
        this.thickness = element.thickness;
        element.getDestination().addEdge(this);
        element.getOrigin().addEdge(this);
    }

    /**
     * Getter du Vertex d'origine de l'Edge
     * @return le Vertex d'origine
     */
    public Vertex getOrigin() {
        return this.origin;
    }

    /**
     * Setter du Vertex d'origine de l'Edge
     * @param origin le nouveau Vertex d'origine
     */
    public void setOrigin(Vertex origin) {

        this.origin.getEdges().remove(this);
        this.origin = origin;
        origin.addEdge(this);
    }

    /**
     * Getter du Vertex de destination de l'Edge
     * @return le Vertex de destination
     */
    public Vertex getDestination() {
        return this.destination;
    }

    /**
     * Setter du Vertex de destination de l'Edge
     * @param destination le nouveau Vertex de destination
     */
    public void setDestination(Vertex destination) {

        this.destination.getEdges().remove(this);
        this.destination = destination;
        destination.addEdge(this);
    }

    /**
     * Getter de l'épaisseur de l'Edge
     * @return l'épaisseur de l'Edge
     */
    public int getThickness() {
    	return this.thickness;
    }
    
    /**
     * Setter de l'épaisseur de l'Edge
     * @param thickness la nouvelle épaisseur de l'Edge
     */
    public void setThickness(int thickness) {
    	this.thickness = thickness;
    }


	@Override
	public boolean isVertex() {
		return false;
	}
}