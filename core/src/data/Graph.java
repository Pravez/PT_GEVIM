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
     * Default constructor, initializes attributes.
     */
    public Graph() {
        this.edges                    = new ArrayList<Edge>();
        this.vertexes                 = new ArrayList<Vertex>();
    }

    /**
     * Constructor with copy
     * @param g
     */
    public Graph(Graph g) {
    	this.edges            = new ArrayList<Edge>(g.edges);
    	this.vertexes         = new ArrayList<Vertex>(g.vertexes);
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Creates a vertex with default attributes
     * @param x
     * @param y
     */
    public void createVertex(Point position){
    	Vertex vertex = new Vertex(position);
        this.vertexes.add(vertex);
        this.setChanged();
    }

    /**
     * Creates an edge between two vertexes
     * @param origin
     * @param destination
     */
    public void createEdge(Vertex origin, Vertex destination ){
    	Edge edge = new Edge(origin, destination);
        this.edges.add(edge);
        this.setChanged();
    }

    /**
     * Moves a vertex to x and y coordinates
     * @param vertex
     * @param x
     * @param y
     */
    public void moveVertex(Vertex vertex, Point destination){
        vertex.setPosition(destination);
        this.setChanged();
    }

    /**
     * Moves a vertex by a vector
     * @param vectorX
     * @param vectorY
     */
    public void moveVertexes(ArrayList<Vertex> vertexes, int vectorX, int vectorY){
        for(Vertex vertex : vertexes){
            vertex.move(vectorX,vectorY);
        }
        this.setChanged();
    }

	@Override
	public void setChanged() {
		this.notifyObservers(this.vertexes);
		
	}

	@Override
	public Object getState() {
		return this.vertexes;
	}
}