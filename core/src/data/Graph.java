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

    private ArrayList<GraphElement> elements;

    private String            name;
    private String            file;
    
    /**
     * Constructeur de la classe Graph
     */
    public Graph() {
        this.elements = new ArrayList<GraphElement>();
    }

    /**
     * Constructeur par copie de la classe Graph
     * @param g
     */
    public Graph(Graph g) {
    	this.elements    = new ArrayList<GraphElement>(g.elements);
    }

    /**
     * Getter de la liste des GraphElement du Graph
     * @return la liste des GraphElement
     */
    public ArrayList<GraphElement> getGraphElements() {
        return this.elements;
    }

    /**
     * Setter de la liste des GraphElement du graph
     * @param elements la nouvelle liste de GraphElement
     */
    public void setGraphElements(ArrayList<GraphElement> elements) {
        this.elements = elements;
    }

    /**
     * Getter de la liste des Edge du Graph
     * @return la liste des Edge
     */
    public ArrayList<Edge> getEdges() {
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	for(GraphElement element : this.elements) {
    		if (!element.isVertex()) {
    			edges.add((Edge) element);
    		}
    	}
        return edges;
    }

    /**
     * Getter de la liste des Vertex du Graph
     * @return la liste des Vertex
     */
    public ArrayList<Vertex> getVertexes() {
    	ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
    	for(GraphElement element : this.elements) {
    		if (element.isVertex()) {
    			vertexes.add((Vertex) element);
    		}
    	}
        return vertexes;
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
     * @param color la couleur du Vertex à créer
     * @param position la position du Vertex à créer
     * @param size la taille du Vertex à créer
     * @param shape la forme du Vertex à créer
     */
    public void createVertex(Color color, Point position, int size, Vertex.Shape shape) {
    	Vertex vertex = new Vertex(color, position, size, shape);
        this.elements.add(vertex);
        this.setChanged();
    }

    /**
     * Creates an edge between two vertexes
     * @param color la couleur de l'Edge à créer
     * @param origin le Vertex d'origine de l'Edge à créer
     * @param destination le Vertex de destination de l'Edge à créer
     * @param thickness l'épaisseur de l'Edge à créer
     */
    public void createEdge(Color color, Vertex origin, Vertex destination, int thickness) {
    	Edge edge = new Edge(color, origin, destination, thickness);
        this.elements.add(edge);
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
     * Supprime un GraphElement
     * @param element GraphElement qui doit être supprimé
     */
    public void removeGraphElement(GraphElement element) {
    	if (element.isVertex()) {
    		for(Edge e : ((Vertex) element).getEdges()){
                this.elements.remove(e);
            }
    	}
        this.elements.remove(element);
    }

    /**
     * (non-Javadoc)
     * @see data.Observable#setChanged()
     */
	@Override
	public void setChanged() {
		this.notifyObservers(this.elements);
	}

	/**
	 * (non-Javadoc)
	 * @see data.Observable#getState()
	 */
	@Override
	public Object getState() {
		return this.elements;
	}
}