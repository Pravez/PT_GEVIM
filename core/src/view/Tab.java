package view;

import controller.Controller;
import controller.EdgeMouseListener;
import controller.VertexMouseListener;
import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Observable;
import data.Vertex;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    
    private ArrayList<ElementView> selectedElements;
    
    private String                name;
    private String                file;
    private Color                 defaultColor;
    private Color                 defaultSelectedColor;
    private int                   defaultThickness;
    private int                   defaultSelectedThickness;
    private int                   defaultSize;
    private Vertex.Shape          defaultShape;
    
    /** Sélection par zone **/
    private Rectangle selectionZone;
    private Color     selectionColor;
    private Color     selectionBorderColor;

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
        this.vertexes                 = new ArrayList<VertexView>();
        this.selectedElements         = new ArrayList<ElementView>();
        
        this.defaultColor             = Color.BLACK;
        this.defaultSelectedColor     = Color.BLUE;
        this.defaultThickness         = 1;
        this.defaultSelectedThickness = 2;
        this.defaultSize              = 15;
        this.defaultShape             = Vertex.Shape.SQUARE;
        
        this.selectionColor           = new Color(172, 211, 244);
        this.selectionBorderColor     = new Color(107, 153, 189);
        this.selectionZone            = null;
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
    	if (this.selectionZone != null) {
    		g.setColor(this.selectionColor);
    		g.fillRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
    		g.setColor(this.selectionBorderColor);
    		g.drawRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
    	}
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

    public VertexView getVertexAt(Point position){

        for(VertexView v : this.vertexes){
            if(v.getPosition() == position){
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
     * Méthode pour déselectionner un ElementView de la liste des ElementView sélectionnés
     * @param element le ElementView à retirer de la liste
     */
    public void unselectElement(ElementView element){
    	element.updateHover(false);
        this.selectedElements.remove(element);
        this.repaint();
    }

    /**
     * Méthode pour ajouter un ElementView à la liste des ElementView sélectionnés
     * @param element le ElementView à ajouter à la liste
     */
    public void selectElement(ElementView element){
    	element.updateHover(true);
        this.selectedElements.add(element);
        this.repaint();
    }

    public void modifySelectedElement() {
    	selectedElements.get(0).modify(this.graph);
    	this.repaint();
    }

    public ArrayList<ElementView> getSelectedElements(){
        return this.selectedElements;
    }

    /**
     * Méthode pour déselectionner tous les ElementView sélectionnés
     */
    public void clearSelectedElements(){
    	for(ElementView element : this.selectedElements) {
    		element.updateHover(false);
    	}
        this.selectedElements.clear();
        this.repaint();
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
    	VertexMouseListener listener = new VertexMouseListener(this.controller, vertexView);
    	vertexView.addMouseListener(listener);
    	vertexView.addMouseMotionListener(listener);
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
        edgeView.addMouseListener(new EdgeMouseListener(this.controller, edgeView));
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
		// pour chaque GraphElement du Graph
		for (GraphElement element : (ArrayList<GraphElement>)object) {
			if (element.isVertex()) {
				addVertex((Vertex)element);
			} else {
				VertexView src= null, dst = null;
	            ListIterator<VertexView> search = vertexes.listIterator();

	            while (search.hasNext() && (src == null || dst == null)){
	                VertexView tmp = search.next();
	                if (tmp.getVertex() == ((Edge) element).getOrigin()) src = tmp;
	                else if (tmp.getVertex() == ((Edge) element).getDestination()) dst = tmp;
	            }
	            if ( src != null && dst != null) addEdge((Edge) element, src, dst);
			}
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
        origin.setAttribute("key", "origin");
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

	public void launchSelectionZone(Point origin, Point position) {
		int x      = origin.x > position.x ? position.x : origin.x;
		int y      = origin.y > position.y ? position.y : origin.y;
		int width  = origin.x - position.x < 0 ? position.x - origin.x : origin.x - position.x;
		int height = origin.y - position.y < 0 ? position.y - origin.y : origin.y - position.y;
		this.selectionZone = new Rectangle(x, y, width, height);
		this.repaint();
	}

	public void handleSelectionZone() {
		// Ici, ajouter les ElementView à la sélection
		clearSelectedElements();
		for (VertexView v : this.vertexes) {
			if (this.selectionZone.contains(v.getPosition())) {
				selectElement(v);
			}
		}
		for (EdgeView e : this.edges) {
			if (this.selectionZone.contains(e.getOrigin().getPosition()) && this.selectionZone.contains(e.getDestination().getPosition())) {
				selectElement(e);
			}
		}
		
		this.selectionZone = null;
		this.repaint();
	}


    //END REGION
}
