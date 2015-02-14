package data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vain on 26/01/15.
 * Modified by cordavidenko on 26/01/15
 * Classe Vertex, sommet du Graph
 */
public class Vertex {
    private String          name;
    private Point           position;
    private ArrayList<Edge> edges; // plus tard il n'y aura plus d'Edges, que un lien vers les autres vertex qu'il connait

    /**
     * Constructeur de la classe Vertex
     * @param name le nom du Vertex
     * @param position la position du Vertex
     */
    public Vertex(String name, Point position) {
        this.name      = name;
        this.position  = position;
        this.edges     = new ArrayList<Edge>();
    }

    /**
     * Constructeur de la classe Vertex
     * @param position la position du Vertex
     */
    public Vertex(Point position) {
        this.position  = position;
        this.edges     = new ArrayList<Edge>();
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
     * Setter du nom du Vertex
     * @param name le nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter de la position du Vertex
     * @param position la nouvelle position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Getter du nom du Vertex
     * @return le nom du Vertex
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter de la position du Vertex
     * @return la position du Vertex
     */
    public Point getPosition() {
        return this.position;
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