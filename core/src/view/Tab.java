package view;

import controller.Controller;
import controller.VertexMouseListener;
import data.Edge;
import data.Graph;
import data.Observable;
import data.Vertex;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Corentin Davidenko on 04/02/15
 * Classe Tab, onglet affichant un graphe dans l'application, est un Observer de la classe Graph
 */
public class Tab extends JComponent implements Observer {

	private static final long     serialVersionUID = 1L;
    private Graph                 graph;
    private Controller            controller;
    
    private ArrayList<EdgeView>   edges;
    private ArrayList<VertexView> vertexes;
    
    private ArrayList<EdgeView>   selectedEdges;
    private ArrayList<VertexView> selectedVertexes;
    
    private String                name;
    private String                file;
    private Color                 defaultColor;
    private Color                 defaultSelectedColor;
    private int                   defaultThickness;
    private int                   defaultSelectedThickness;
    private int                   defaultSize;
    private Vertex.Shape          defaultShape;

    /**
     * Getter du Graph
     * @return le Graph
     */
    public Graph getGraph() {
        return this.graph;
    }

    /**
     * Setter du Graph
     * @param graph le nouveau Graph
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * Constructeur du Tab, l'onglet. Un onglet est associé à un {@link data.Graph}
     * @param graph le Graph observé
     * @param controller le Controller de l'application
     */
    public Tab(Graph graph, Controller controller) {
        super();
        this.graph                    = graph;
        this.controller               = controller;
        
        this.edges                    = new ArrayList<EdgeView>();
        this.selectedEdges            = new ArrayList<EdgeView>();

        this.vertexes                 = new ArrayList<VertexView>();
        this.selectedVertexes         = new ArrayList<VertexView>();
        
        this.defaultColor             = Color.BLACK;
        this.defaultSelectedColor     = Color.BLUE;
        this.defaultThickness         = 1;
        this.defaultSelectedThickness = 2;
        this.defaultSize              = 15;
        this.defaultShape             = Vertex.Shape.SQUARE;
    }
    
    /**
     * Méthode permettant de savoir si l'on peut ajouter un VertexView à la position désirée
     * @param position la position du VertexView à ajouter
     * @return un boolean avec le résultat
     */
    public boolean canAddVertex(Point position) {
    	for (VertexView v : this.vertexes) {
    		int margin = this.defaultSize/2;
    		int side   = v.getWidth() + margin*3; 
    		if (new Rectangle(v.getPosition().x - margin, v.getPosition().y - margin, side, side).contains(position))
    			return false;
    	}
    	return true;
    }

    /**
     * Méthode de dessin des éléments dans un onglet, à partir des données d'un {@link data.Graph}
     * @param g {@link java.awt.Graphics} à partir de quoi dessiner
     */
    public void paintComponent(Graphics g){
        for(EdgeView e : this.edges){
            e.paintComponent(g);
        }

        for (VertexView v : this.vertexes) {
            v.paintComponent(g);
        }
    }

    /**
     * Teste si à partir des données d'un événement souris ({@link java.awt.event.MouseEvent}) un vertex est situé au dessous.
     * @param mouseEvent Evénement souris
     * @return Le {@link data.Vertex} s'il existe, sinon null
     */
    public VertexView onVertex(MouseEvent mouseEvent){
        Rectangle rect = new Rectangle();

        for(VertexView v : this.vertexes) {
            rect.setBounds(v.getPosition().x, v.getPosition().y, v.getWidth(), v.getWidth());
            if(rect.contains(mouseEvent.getX(), mouseEvent.getY())){
                return v;
            }
        }
        return null;
    }
    
    /**
     * Getter de la forme par défaut des VertexView
     * @return la forme par défaut des VertexView
     */
    public Vertex.Shape getDefaultShape() { // pourquoi cela ne serait pas dans les Vertex plutôt ? Est-ce que tous les Vertex doivent avoir la même forme ?
        return defaultShape;
    }

    /**
     * Setter de la forme par défaut des VertexView
     * @param defaultShape la nouvelle forme des VertexView
     */
    public void setDefaultShape(Vertex.Shape defaultShape) {
        this.defaultShape = defaultShape;
    }

    /**
     * Getter de la liste des VertexView du Tab
     * @return la liste des VertexView
     */
    public ArrayList<VertexView> getVertexes() {
        return vertexes;
    }

    /**
     * Setter de la liste des VertexView du Tab
     * @param vertexes la nouvelle liste des VertexView
     */
    public void setVertexes(ArrayList<VertexView> vertexes) {
        this.vertexes = vertexes;
    }

    /**
     * Getter de la liste des EdgeView du Tab
     * @return la liste des EdgeView
     */
    public ArrayList<EdgeView> getEdges() {
        return this.edges;
    }

    /**
     * Setter de la liste des EdgeView du Tab
     * @param edges la nouvelle liste des EdgeView
     */
    public void setEdges(ArrayList<EdgeView> edges) {
        this.edges = edges;
    }

    /**
     * Méthode pour ajouter un EdgeView à la liste des EdgeView sélectionnés
     * @param e le EdgeView à ajouter à la liste
     */
    public void selectedEdge(EdgeView e){
    	e.setColor(this.defaultSelectedColor);
        this.selectedEdges.add(e);
    }

    /**
     * Méthode pour déselectionner un EdgeView de la liste des EdgeView sélectionnés
     * @param e le EdgeView à retirer de la liste
     */
    public void unselectedEdge(EdgeView e){
    	e.setColor(this.defaultColor);
        this.selectedEdges.remove(e);
    }

    /**
     * Méthode pour ajouter un VertexView à la liste des VertexView sélectionnés
     * @param v le VertexView à ajouter à la liste
     */
    public void selectVertex(VertexView v){
    	v.updateHover(true);
        this.selectedVertexes.add(v);
        this.repaint();
    }

    public void modifySelectedVertex(){
        selectedVertexes.get(0).modifyVertexView();
    }

    public ArrayList<VertexView> getSelectedVertexes(){
        return this.selectedVertexes;
    }

    /**
     * Méthode pour déselectionner un VertexView de la liste des VertexView sélectionnés
     * @param v le VertexView à retirer de la liste
     */
    public void unselectedVertex(VertexView v){
    	v.updateHover(false);
        this.selectedVertexes.remove(v);
    }

    /**
     * Méthode pour déselectionner tous les VertexView et EdgeView sélectionnés
     */
    public void clearSelectedItem(){
    	for(VertexView v : this.selectedVertexes) {
    		v.updateHover(false);
    	}
    	for(EdgeView e : this.selectedEdges) {
    		e.setColor(this.defaultColor);
    	}
    	this.selectedVertexes.clear();
        this.selectedEdges.clear();
    }

    /**
     * Getter du nom du Tab
     * @return le nom du Tab
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter du nom du Tab
     * @param name le nouveau nom du Tab
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter du nom du fichier du Tab
     * @return le nom du fichier du Tab
     */
    public String getFile() {
        return this.file;
    }

    /**
     * Setter du nom du fichier du Tab
     * @param file le nouveau nom du fichier du Tab
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Getter de la couleur par défaut des VertexView et des EdgeView
     * @return la couleur par défaut
     */
    public Color getDefaultColor() {
        return this.defaultColor;
    }

    /**
     * Setter de la couleur par défaut des VertexView et des EdgeView
     * @param defaultColor la nouvelle couleur par défaut
     */
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
    
    /**
     * Getter de la taille par défaut des VertexView
     * @return la taille par défaut
     */
    public int getDefaultSize() {
        return this.defaultSize;
    }

    /**
     * Setter de la taille par défaut des VertexView
     * @param defaultSize la nouvelle taille par défaut
     */
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    /**
     * Getter de l'épaisseur par défaut des EdgeView
     * @return l'épaisseur par défaut
     */
    public int getDefaultThickness() {
        return this.defaultThickness;
    }

    /**
     * Setter de l'épaisseur par défaut des EdgeView
     * @param defaultThickness la nouvelle épaisseur par défaut
     */
    public void setDefaultThickness(int defaultThickness) {
        this.defaultThickness = defaultThickness;
    }

    /**
     * Méthode permettant d'ajouter un VertexView à la position désirée
     * @param vertex le modèle Vertex du VertexView
     */
    public void addVertex(Vertex vertex){
    	VertexView vertexView = new VertexView(vertex, this.defaultSelectedColor);
    	vertexView.addMouseListener(new VertexMouseListener(this.controller, vertexView));
        this.vertexes.add(vertexView);
        super.add(vertexView);
    }
    
    /**
     * Méthode permettant d'ajouter un EdgeView
     * @param edge le modèle Edge de l'EdgeView
     * @param origin le VertexView d'origine de l'EdgeView
     * @param destination le VertexView de destination de l'EdgeView
     */
    public void addEdge(Edge edge, VertexView origin, VertexView destination ){
    	EdgeView edgeView = new EdgeView(edge, this.defaultSelectedThickness, this.defaultSelectedColor, origin, destination);
        this.edges.add(edgeView);
        super.add(edgeView);
    }

    /**
     * Méthode pour changer la position d'un VertexView du Tab
     * @param vertex le VertexView à déplacer
     * @param position les coordonnées de destination
     */
    public void moveVertex(VertexView vertex, Point position){
        vertex.setPosition(position);
    }

    /**
     * Méthode pour déplacer la liste de VertexView sélectionnés dans une certaine direction
     * @param vectorX la direction du vecteur de déplacement en abscisse
     * @param vectorY la direction du vecteur de déplacement en ordonnée
     */
    /*public void moveSelectedVertexes(int vectorX, int vectorY){
        for(VertexView vertex : this.selectedVertexes){
            vertex.move2(vectorX, vectorY);
        }
    }*/ 
    /** Cette méthode devrait être remplacée par une méthode déplaçant directement les Vertex dans le Graph ? **/

    /**
     * Méthode pour supprimer la liste des VertexView et en recréer à partir des données du Graph observé
     * (non-Javadoc)
     * @see view.Observer#update(data.Observable, java.lang.Object)
     */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object object) {

		this.vertexes.clear();
        this.edges.clear();
		super.removeAll();
		for (Vertex v : (ArrayList<Vertex>)((Object[])object)[0]) {
			addVertex(v);
		}

        for (Edge e : (ArrayList<Edge>)((Object[])object)[1]){
            VertexView src= null, dst = null;
            ListIterator<VertexView> search = vertexes.listIterator();

            while (search.hasNext() && (src == null || dst == null)){
                VertexView tmp = search.next();
                if (tmp.getVertex() == e.getOrigin()) src = tmp;
                else if (tmp.getVertex() == e.getDestination()) dst = tmp;
            }
            if ( src != null && dst != null) addEdge(e, src, dst);

        }

		this.repaint();
		/** A modifier pour n'ajouter que ceux qui sont dans la fenêtre **/
	}

    //REGION GRAPHML
	/**
     * Saves the current graph to an XML doc (XML-like doc)
     * @param fileName
     */
    public void saveToGraphml(String fileName) {

        Element graphml = new Element("graphml");
        graphml.setAttribute("axmlns", "http://graphml.graphdrawing.org/xmlns");


        Element graphXML = new Element("graph");
        graphXML.setAttribute("id", "0");
        graphXML.setAttribute("edgedefault", "directed");


        org.jdom2.Document toBeSaved = new Document(graphXML);

        for(Vertex v : this.graph.getVertexes()) {
            graphXML.addContent(createVertexDocumentElement(v));
        }
        
        for(Edge e : this.graph.getEdges()) {
            graphXML.addContent(createEdgeDocumentElement(e));
        }

        showXML(toBeSaved);
        saveXML(fileName, toBeSaved);
    }

    /**
     * Creates an XML element from a VertexView
     * @param v
     * @return
     */
    private Element createVertexDocumentElement(Vertex v) {

        Element createdElement = new Element("node");
        createdElement.setAttribute("id",String.valueOf(v.getValue()));

        Element name = new Element("data");
        name.setAttribute("key","name");
        Element color     = new Element("data");
        color.setAttribute("key","color");
        Element size = new Element("data");
        size.setAttribute("key","size");
        Element positionX = new Element("data");
        positionX.setAttribute("key", "positionX");
        Element positionY = new Element("data");
        positionY.setAttribute("key", "positionY");
        Element shape     = new Element("data");
        shape.setAttribute("key", "shape");

        name.setText(v.getLabel());
        color.setText(String.valueOf(v.getColor().getRGB()));
        positionX.setText(String.valueOf(v.getPosition().x));
        positionY.setText(String.valueOf(v.getPosition().y));
        shape.setText(v.getShape().toString());

        createdElement.addContent(name);
        createdElement.addContent(color);
        createdElement.addContent(size);
        createdElement.addContent(positionX);
        createdElement.addContent(positionY);
        createdElement.addContent(shape);

        return createdElement;
    }


    
    /**
     * Creates an XML element from a EdgeView
     * @param e
     * @return
     */
    private Element createEdgeDocumentElement(Edge e) {

        Element createdElement = new Element("edge");
        createdElement.setAttribute("id",String.valueOf(e.getValue()));

        Element origin = new Element("data");
        origin.setAttribute("key","origin");
        Element destination = new Element("data");
        destination.setAttribute("key", "destination");
        Element thickness = new Element("data");
        thickness.setAttribute("key", "thickness");

        origin.setText(String.valueOf(e.getOrigin().getValue()));
        destination.setText(String.valueOf(e.getDestination().getValue()));
        thickness.setText(String.valueOf(e.getThickness()));


        createdElement.addContent(origin);
        createdElement.addContent(destination);
        createdElement.addContent(thickness);

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

    //END REGION
}