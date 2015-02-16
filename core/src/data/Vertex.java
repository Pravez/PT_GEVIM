package data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vain on 26/01/15.
 * Modified by cordavidenko on 26/01/15
 * Classe Vertex, sommet du Graph
 */
public class Vertex extends GraphElement {

    private static int CURRENT_VALUE = 0;

    private Point           position;
    private int             size;
    private Shape           shape;
    private ArrayList<Edge> edges;
    
    public static enum Shape { SQUARE, CIRCLE, TRIANGLE, CROSS };

    /**
     * Constructeur de la classe Vertex
     * @param label l'étiquette du Vertex
     * @param color la couleur du Vertex
     * @param position la position du Vertex
     * @param size la taille du Vertex
     * @param shape la forme du Vertex
     */
    public Vertex(String label, Color color, Point position, int size, Shape shape) {
    	super(label, CURRENT_VALUE, color);
        this.position = position;
        this.size     = size;
        this.shape    = shape;
        this.edges    = new ArrayList<Edge>();
        CURRENT_VALUE++;
    }

    /**
     * Constructeur de la classe Vertex
     * @param color la couleur du Vertex
     * @param position la position du Vertex
     * @param size la taille du Vertex
     * @param shape la forme du Vertex
     */
    public Vertex(Color color, Point position, int size, Shape shape) {
    	super(CURRENT_VALUE, color);
        this.position = position;
        this.size     = size;
        this.shape    = shape;
        this.edges    = new ArrayList<Edge>();
        CURRENT_VALUE++;
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
     * Getter de la position du Vertex
     * @return la position du Vertex
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
}