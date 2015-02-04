package data;

import javax.swing.undo.UndoManager;
import java.util.List;

/**
 * Created by Corentin Davidenko on 04/02/15.
 */
public class Graph {

    protected UndoManager undo = new UndoManager();

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge e){
        edges.add(e);
    }

    public void removeEdge(Edge e){
        edges.remove(e);
    }

    public void addVertex(Vertex v){
        vertexes.add(v);
    }

    public void removeVertex(Vertex v){
        vertexes.remove(v);
    }

    private List<Edge> edges;
    private List<Vertex> vertexes;

    public Graph() {
    }

}
