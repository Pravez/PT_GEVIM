package data;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.undo.UndoManager;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Corentin Davidenko on 04/02/15.
 * Class to manage the interactions between edges, vertexes and users. It possesses an UndoManager to
 * manage undo actions. It is associated to a file and has the different default settings for the vertexes
 * and the edges.
 */
public class Graph {

	protected UndoManager     undo = new UndoManager();

    private ArrayList<Edge>   edges;
    private ArrayList<Vertex> vertexes;

    private ArrayList<Edge>   selectedEdges;
    private ArrayList<Vertex> selectedVertexes;
    private String            name;
    private String            file;
    private Color             defaultColor;
    private int               defaultThickness;
    private Shape             defaultShape;
    
    /**
     * Default constructor, initializes attributes.
     */
    public Graph() {
        this.edges            = new ArrayList<Edge>();
        this.selectedEdges    = new ArrayList<Edge>();

        this.vertexes         = new ArrayList<Vertex>();
        this.selectedVertexes = new ArrayList<Vertex>();
    }

    /**
     * Constructor with copy
     * @param g
     */
    public Graph(Graph g) {
    	this.edges            = new ArrayList<Edge>(g.edges);
    	this.selectedEdges    = new ArrayList<Edge>(g.selectedEdges);

    	this.vertexes         = new ArrayList<Vertex>(g.vertexes);
    	this.selectedVertexes = new ArrayList<Vertex>(g.selectedVertexes);
    }
    
    public Shape getDefaultShape() { // pourquoi cela ne serait pas dans les Vertex plutôt ? Est-ce que tous les Vertex doivent avoir la même forme ?
        return defaultShape;
    }

    public void setDefaultShape(Shape defaultShape) {
        this.defaultShape = defaultShape;
    }

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

    public void clearSelectedItem(){
        selectedEdges.clear();
        selectedVertexes.clear();
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

    public Color getDefaultColor() {
        return this.defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int getDefaultThickness() {
        return this.defaultThickness;
    }

    public void setDefaultThickness(int defaultThickness) {
        this.defaultThickness = defaultThickness;
    }

    /**
     * Creates a vertex with default attributes
     * @param x
     * @param y
     */
    public void createVertex(int x, int y){
        Vertex vertex = new Vertex(this.defaultColor, this.defaultThickness, x, y, this.defaultShape);
        this.vertexes.add(vertex);
    }

    /**
     * Creates an edge between two vertexes
     * @param origin
     * @param destination
     */
    public void createEdge(Vertex origin, Vertex destination ){
        Edge edge = new Edge(this.defaultThickness, this.defaultColor, origin, destination);
        this.edges.add(edge);
    }

    /**
     * Moves a vertex to x and y coordinates
     * @param vertex
     * @param x
     * @param y
     */
    public void moveVertex(Vertex vertex, int x, int y){
        vertex.setPositionX(x);
        vertex.setPositionY(y);
    }

    /**
     * Moves a vertex by a vector
     * @param vectorX
     * @param vectorY
     */
    public void moveSelectedVertex(int vectorX, int vectorY){
        for(Vertex vertex : this.selectedVertexes){
            vertex.move(vectorX,vectorY);
        }
    }

    /**
     * Saves the current graph to an XML doc (XML-like doc)
     * @param fileName
     */
    public void saveToGraphml(String fileName){

        Element root = new Element("Vertexes");
        org.jdom2.Document toBeSaved = new Document(root);

        for(Vertex v : this.vertexes){
            root.addContent(createDocumentElement(v));
        }

        saveXML(fileName, toBeSaved);
    }


    /**
     * Creates an XML element from a vertex
     * @param v
     * @return
     */
    private Element createDocumentElement(Vertex v){

        Element createdElement = new Element("vertex");

        Element name = new Element("name");
        Element color = new Element("color");
        Element thickness = new Element("thickness");
        Element positionX = new Element("positionX");
        Element positionY = new Element("positionY");
        Element shape = new Element("shape");
        Element edges = new Element("edges");

        for(Edge e : v.getEdges()){
            Element edge = new Element("edge");
            edge.setText(e.getLabel());
            edges.addContent(edge);
        }

        name.setText(v.getName());
        color.setText(v.getColor().toString());
        thickness.setText(String.valueOf(v.getThickness()));
        positionX.setText(String.valueOf(v.getPositionX()));
        positionY.setText(String.valueOf(v.getPositionY()));
        shape.setText(v.getShape().toString());

        createdElement.addContent(name);
        createdElement.addContent(color);
        createdElement.addContent(thickness);
        createdElement.addContent(positionX);
        createdElement.addContent(positionY);
        createdElement.addContent(shape);

        return createdElement;
    }

    /**
     * Shows an XML document
     * @param document
     */
    private static void showXML(Document document){

        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

        try {
            out.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a document at the "file" destination, in XML format
     * @param file
     * @param document
     */
    private static void saveXML(String file, Document document){
        try
        {
            //On modifie le format d'enregistrement (affichage)
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

            out.output(document, new FileOutputStream(file));
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
}
