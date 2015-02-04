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

    public Shape getDefaultShape() {
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

    public void moveVertex(Vertex vertex, int x, int y){
        vertex.setPositionX(x);
        vertex.setPositionY(y);
    }

    public void moveSelectedVertex(int vectorX, int vectorY){
        for(Vertex vertex : selectedVertexes){
            vertex.move(vectorX,vectorY);
        }
    }

    /**
     * Saves the current graph to an XML doc (XML-like doc)
     * @param fileName
     */
    public void saveToGraphml(String fileName){

        Element racine = new Element("Vertexes");
        org.jdom2.Document toBeSaved = new Document(racine);

        for(Vertex v : vertexes){
            racine.addContent(createDocumentElement(v));
        }

        saveXML(fileName,toBeSaved);
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

        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        try {
            sortie.output(document, System.out);
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
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

            sortie.output(document, new FileOutputStream(file));
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
}
