package view;

import data.Edge;
import data.Graph;
import data.Observable;
import data.Vertex;

import javax.swing.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import controller.Controller;
import controller.VertexMouseListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
        this.defaultSize              = 10;
        this.defaultShape             = Vertex.Shape.CROSS;
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
    	for (VertexView v : this.vertexes) {
    		v.paintComponent(g);
    	}

        for(EdgeView e : this.edges){
            e.paintComponent(g);
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
    public void setDefaultWidth(int defaultSize) {
        this.defaultSize = defaultSize;
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
    public void createEdge(Edge edge, VertexView origin, VertexView destination ){
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

        ///AAAAAAAAAAAAAATTENTION, CETTE METHODE N'A PAS LIEU D'ETRE
        //LA SUPPRESSION DE TOUS LES VERTEXVIEW RECURRENTE A CHAQUE update() EMPECHE
        //TOUTE EDITION DU GRAPHE.


		this.vertexes.clear();
		super.removeAll();
		for (Vertex v : (ArrayList<Vertex>)object) {
			addVertex(v);
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

        Element root = new Element("Vertexes");
        org.jdom2.Document toBeSaved = new Document(root);

        for(Vertex v : this.graph.getVertexes()) {
            root.addContent(createVertexDocumentElement(v));
        }
        
        for(Edge e : this.graph.getEdges()) {
        	root.addContent(createEdgeDocumentElement(e));
        }

        saveXML(fileName, toBeSaved);
    }

    /**
     * Creates an XML element from a VertexView
     * @param v
     * @return
     */
    private Element createVertexDocumentElement(Vertex v) {

        Element createdElement = new Element("vertex");

        Element name      = new Element("name");
        Element color     = new Element("color");
        Element thickness = new Element("thickness");
        Element positionX = new Element("positionX");
        Element positionY = new Element("positionY");
        Element shape     = new Element("shape");

        name.setText(v.getLabel());
        color.setText(v.getColor().toString());
        positionX.setText(String.valueOf(v.getPosition().x));
        positionY.setText(String.valueOf(v.getPosition().y));
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
     * Creates an XML element from a EdgeView
     * @param e
     * @return
     */
    private Element createEdgeDocumentElement(Edge e) {
    	Element createdElement = new Element("edge");
    	
    	Element name = new Element("name");
    	
        name.setText(e.getLabel());
        
        createdElement.addContent(name);
        
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