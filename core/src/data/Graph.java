package data;

import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Corentin Davidenko on 04/02/15.
 */
public class Graph {

    protected UndoManager undo = new UndoManager();

    private ArrayList<Edge> edges;
    private ArrayList<Vertex> vertexes;

    private ArrayList<Edge> selectedEdges;
    private ArrayList<Vertex> selectedVertexes;
    private String name;
    private String file;
    private Color defaultColor;
    private int defaultThickness;

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
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

    public void addSelectedEdge(Edge e){
        selectedEdges.add(e);
    }

    public void removeSelectedEdge(Edge e){
        selectedEdges.remove(e);
    }

    public void addSelectedVertex(Vertex v){
        selectedVertexes.add(v);
    }

    public void removeSelectedVertex(Vertex v){
        selectedVertexes.remove(v);
    }

    public void clearSelectedsItem(){
        selectedEdges.clear();
        selectedVertexes.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int getDefaultThickness() {
        return defaultThickness;
    }

    public void setDefaultThickness(int defaultThickness) {
        this.defaultThickness = defaultThickness;
    }

    public Graph() {
        edges = new ArrayList<Edge>();
        selectedEdges = new ArrayList<Edge>();

        vertexes = new ArrayList<Vertex>();
        selectedVertexes = new ArrayList<Vertex>();
    }


}
