package data;

import javax.swing.undo.UndoManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Corentin Davidenko on 04/02/15.
 * Class to manage the interactions between edges, vertexes and users. It possesses an UndoManager to
 * manage undo actions. It is associated to a file and has the different default settings for the vertexes
 * and the edges.
 */
public class Graph extends Observable {

	protected UndoManager     undo = new UndoManager();

    private ArrayList<Edge>   edges;
    private ArrayList<Vertex> vertexes;

    private String            name;
    private String            file;
    
    /**
     * Constructeur de la classe Graph
     */
    public Graph() {
        this.edges    = new ArrayList<Edge>();
        this.vertexes = new ArrayList<Vertex>();
    }

    /**
     * Constructeur par copie de la classe Graph
     * @param g
     */
    public Graph(Graph g) {
    	this.edges    = new ArrayList<Edge>(g.edges);
    	this.vertexes = new ArrayList<Vertex>(g.vertexes);
    }

    /**
     * Getter de la liste des Vertex du Graph
     * @return la liste des Vertex
     */
    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    /**
     * Setter de la liste des Vertex du graph
     * @param vertexes la nouvelle liste de Vertex
     */
    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    /**
     * Getter de la liste des Edge du Graph
     * @return la liste des Edge
     */
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Setter de la liste des Edge du graph
     * @param edges la nouvelle liste de Edge
     */
    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    /**
     * Getter du nom du Graph
     * @return le nom du Graph
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter du nom du Graph
     * @param name le nouveau nom du Graph
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter du fichier du Graph
     * @return le nom du fichier du Graph
     */
    public String getFile() {
        return this.file;
    }

    /**
     * Setter du fichier du Graph
     * @param file le nouveau nom du fichier du Graph
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Méthode pour ajouter un Vertex au Graph
     * @param position la position du Vertex à créer
     */
    public void createVertex(Point position) {
    	Vertex vertex = new Vertex(position);
        this.vertexes.add(vertex);
        this.setChanged();
    }

    /**
     * Creates an edge between two vertexes
     * @param origin
     * @param destination
     */
    public void createEdge(Vertex origin, Vertex destination) {
    	Edge edge = new Edge(origin, destination);
        this.edges.add(edge);
        this.setChanged();
    }

    /**
     * Méthode pour changer la position d'un Vertex du Graph
     * @param vertex le Vertex à déplacer
     * @param destination les coordonnées de destination
     */
    public void moveVertex(Vertex vertex, Point destination){
        vertex.setPosition(destination);
        this.setChanged();
    }

    /**
     * Méthode pour déplacer une liste de Vertex dans une certaine direction
     * @param vertexes la liste des Vertex à déplacer
     * @param vectorX la direction du vecteur de déplacement en abscisse
     * @param vectorY la direction du vecteur de déplacement en ordonnée
     */
    public void moveVertexes(ArrayList<Vertex> vertexes, int vectorX, int vectorY){
        for(Vertex vertex : vertexes){
            vertex.move(vectorX,vectorY);
        }
        this.setChanged();
    }

    /**
     * (non-Javadoc)
     * @see data.Observable#setChanged()
     */
	@Override
	public void setChanged() {
		this.notifyObservers(this.vertexes);
	}

	/**
	 * (non-Javadoc)
	 * @see data.Observable#getState()
	 */
	@Override
	public Object getState() {
		return this.vertexes;
	}
}