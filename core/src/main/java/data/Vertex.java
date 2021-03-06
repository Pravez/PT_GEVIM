package data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe Vertex, ils peuvent être reliés par des {@link data.Edge}, partie du modèle de l'application
 */
public class Vertex extends GraphElement {

    private Point           position;
    private int             size;
    private Shape           shape;
    private ArrayList<Edge> edges;

    /**
     * les différentes formes disponibles
     */
    public static enum Shape {
        SQUARE,
        CIRCLE,
        TRIANGLE,
        CROSS;

        /**
         * Permet de récupérer les noms des formes sous forme de String
         * @return le nom de la forme
         */
        @Override
        public String toString(){
            switch(this){
                case SQUARE:return "Square";
                case CIRCLE:return "Circle";
                case TRIANGLE:return "Triangle";
                case CROSS:return "Cross";
                default:throw new IllegalArgumentException();
            }
        }

        /**
         * Définit la forme associée au String passé en paramètre
         * @param shape nom de la forme
         * @return énum correspondant au nom passé en paramètre
         */
        public static Shape decode(String shape){
            switch(shape){
                case "Square": return SQUARE;
                case "Circle": return CIRCLE;
                case "Triangle" : return TRIANGLE;
                case "Cross" : return CROSS;
                default: throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Constructeur de la classe Vertex
     * @param label l'étiquette du Vertex
     * @param color la couleur du Vertex
     * @param position la position du Vertex
     * @param size la taille du Vertex
     * @param shape la forme du Vertex
     */
    public Vertex(String label, Color color, Point position, int size, Shape shape) {
    	super(label, color);
        this.position = position;
        this.size     = size;
        this.shape    = shape;
        this.edges    = new ArrayList<Edge>();
    }

    /**
     * Constructeur de la classe Vertex
     * @param element Vertex dont on copie les propriétés
     */
    public Vertex(Vertex element) {
        super(element);
        this.position = new Point(element.position);
        this.size     = element.size;
        this.shape    = Shape.valueOf(element.shape.name());
        this.edges    = new ArrayList<Edge>();
    }

    /**
     * Constructeur de la classe Vertex
     * @param color la couleur du Vertex
     * @param position la position du Vertex
     * @param size la taille du Vertex
     * @param shape la forme du Vertex
     */
    public Vertex(Color color, Point position, int size, Shape shape) {
    	super("node", color);
        this.position = position;
        this.size     = size;
        this.shape    = shape;
        this.edges    = new ArrayList<Edge>();
    }
    
    /**
     * Méthode pour ajouter un Edge au Vertex
     * @param edge le nouvel Edge
     */
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    /**
     * Méthode pour supprimer un Edge spécifique de la liste des Edge du Vertex
     * @param edge l'Edge à retirer
     */
    public void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    /**
     * Getter de la liste des Edge du Vertex
     * @return la liste des Edge
     */
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Setter de la liste des Edge du Vertex
     * @param edges la nouvelle liste des Edge
     */
    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Setter de la position du Vertex
     * @param position la nouvelle position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Getter de la position du Vertex
     * @return la position du Vertex
     */
    public Point getPosition() {
        return this.position;
    }
    
    /**
     * Setter de la taille du Vertex
     * @param size la nouvelle taille
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Getter de la taille du Vertex
     * @return la taille du Vertex
     */
    public int getSize() {
        return this.size;
    }
    
    /**
     * Setter de la forme du Vertex
     * @param shape la nouvelle forme
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Getter de la forme du Vertex
     * @return la forme du Vertex
     */
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Méthode permettant de déplacer le Vertex selon un certaine direction
     * @param vectorX la direction du vecteur de déplacement en abscisse
     * @param vectorY la direction du vecteur de déplacement en ordonnée
     */
    public void move(int vectorX, int vectorY) {
        this.position.x += vectorX;
        this.position.y += vectorY;
    }

    /**
     *Permet de se distinguer des {@link data.Edge}
     * @return
     */
	@Override
	public boolean isVertex() {
		return true;
	}
}