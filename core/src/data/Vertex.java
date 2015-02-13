package data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vain on 26/01/15.
 * Modified by cordavidenko on 26/01/15
 */

public class Vertex {
    private String          name;
    private Point           position;
    private ArrayList<Edge> edges; // plus tard il n'y aura plus d'Edges, que un lien vers les autres vertex qu'il connait

    /**
     * Vertex constructor
     * @param name
     * @param position
     */
    public Vertex(String name, Point position) {
        this.name      = name;
        this.position  = position;
        this.edges     = new ArrayList<Edge>();
    }

    /**
     * Vertex constructor
     * @param position
     */
    public Vertex(Point position) {
        this.position  = position;
        this.edges     = new ArrayList<Edge>();
    }
    
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public Point getPosition() {
        return this.position;
    }

    /**
     * move the Vertex in function of the given vector value
     * @param vectorX
     * @param vectorY
     */
    public void move(int vectorX, int vectorY) {
        this.position.x += vectorX;
        this.position.y += vectorY;
    }
}