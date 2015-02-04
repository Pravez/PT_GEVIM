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
    private Shape defaultShape;

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

    public void selectedEdge(Edge e){
        selectedEdges.add(e);
    }

    public void unselectedEdge(Edge e){
        selectedEdges.remove(e);
    }

    public void selectVertex(Vertex v){
        selectedVertexes.add(v);
    }

    public void unselectedVertex(Vertex v){
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

    public Graph( Graph g) {
        edges = new ArrayList<Edge>(g.edges);
        selectedEdges = new ArrayList<Edge>(g.selectedEdges);

        vertexes = new ArrayList<Vertex>(g.vertexes);
        selectedVertexes = new ArrayList<Vertex>(g.selectedVertexes);
    }

    public void createVertex(int x, int y){
        Vertex vertex = new Vertex(defaultColor, defaultThickness, x, y,defaultShape);
        vertexes.add(vertex);
    }

    public void createEdge(Vertex origin, Vertex destination ){
        Edge edge = new Edge(defaultThickness,defaultColor,origin,destination);
        edges.add(edge);
    }

}
