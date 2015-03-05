package view;

import controller.Controller;
import controller.VertexMouseListener;
import data.*;
import files.GmlFileManager;
import view.elements.EdgeView;
import view.elements.ElementView;
import view.elements.VertexView;
import view.frames.TabPropertiesViewEditor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Corentin Davidenko on 04/02/15
 * Classe Tab, onglet affichant un {@link data.Graph} dans l'application, est un {@link view.Observer} de la classe Graph
 */
public class Tab extends JComponent implements Observer {

	private static final long      serialVersionUID = 1L;
    private Graph                  graph;
    private Controller             controller;
    
    private ArrayList<EdgeView>    edges;
    private ArrayList<VertexView>  vertexes;
    
    private ArrayList<ElementView> selectedElements;
    private ArrayList<ElementView> currentSelectedElements;
    
    private String                 name;
    private String                 file;
    private Color                  defaultColor;
    private Color                  defaultSelectedColor;
    private int                    defaultThickness;
    private int                    defaultSelectedThickness;
    private int                    defaultSize;
    private Vertex.Shape           defaultShape;
    
    /** Sélection par zone **/
    private Rectangle              selectionZone;
    private Color                  selectionColor;
    private Color                  selectionBorderColor;

    /** Edge temporaire **/
    private Point                  originEdge;
    private Point                  destinationEdge;
    private Color                  edgeColor;

    private int                    currentScale;
    private AffineTransform        transform = new AffineTransform();

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
        this.currentSelectedElements  = new ArrayList<ElementView>();
        
        this.defaultColor             = Color.BLACK;
        this.defaultSelectedColor     = Color.BLUE;
        this.defaultThickness         = 1;
        this.defaultSelectedThickness = 2;
        this.defaultSize              = 15;
        this.defaultShape             = Vertex.Shape.SQUARE;
        
        this.selectionColor           = new Color(172, 211, 244);
        this.selectionBorderColor     = new Color(107, 153, 189);
        this.selectionZone            = null;

        this.originEdge               = null;
        this.destinationEdge          = null;
        this.edgeColor                = new Color(0,0,0);

        this.currentScale             = 0;
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
        if(this.originEdge != null && this.destinationEdge != null){
            g.setColor(this.edgeColor);
            g.drawLine(this.originEdge.x, this.originEdge.y, this.destinationEdge.x, this.destinationEdge.y);
        }
        for(EdgeView e : this.edges){
            e.paintComponent(g);
        }

        for (VertexView v : this.vertexes) {
            v.paintComponent(g);
        }

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform.getScaleInstance(0.95, 1.25);

        g2d.setTransform(transform);

        transform.scale(currentScale,currentScale);
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
        //this.repaint();
    }

    /**
     * Méthode pour ajouter un ElementView à la liste des ElementView sélectionnés
     * @param element le ElementView à ajouter à la liste
     */
    public void selectElement(ElementView element){
    	element.updateHover(true);
        this.selectedElements.add(element);
        //this.repaint();
    }

    /**
     * Méthode pour modifier le premier élément sélectionné
     */
    public void modifySelectedElement() {
    	/// A modifier --> soit modifier tous, soit le premier qui est un VertexView
    	selectedElements.get(0).modify(this.graph);
        this.repaint();
    }

    /**
     * Méthode pour modifier tous les éléments existants
     */
    public void modifyProperties() {
        new TabPropertiesViewEditor();
    }

    /**
     * Modifier pour récupérer la liste des ElementView sélectionnés
     * @return la liste des ElementView sélectionnés
     */
    public ArrayList<ElementView> getSelectedElements(){
        return this.selectedElements;
    }
    
    /**
     * Méthode pour déplacer les ElementView sélectionnés
     * @param vector le décalage à effectuer pour chaque élément par rapport à leur position
     */
    public void moveSelectedElements(Point vector) {
    	for (ElementView element : this.selectedElements) {
    		if (element.getGraphElement().isVertex()) {
    			((VertexView)element).move(vector);
    		}
    	}
    }
    
    /**
     * Méthode pour savoir si la liste des ElementView sélectionnés est vide ou non
     * @return le résultat sous la forme d'un booléen
     */
    public boolean selectedElementsIsEmpty() {
    	return this.selectedElements.isEmpty();
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
     * Méthode pour vider la liste des ElementView sélectionnés à la sélection antérieure
     */
    public void clearCurrentSelectedElements(){
    	this.currentSelectedElements.clear();
    }
    
    /**
     * Méthode permettant de définir la zone de sélection par rapport à la position initiale de la souris et la position courante :
     * @param origin le point d'origine de la zone de sélection
     * @param position position la position courante de la souris
     */
    public void setSelectionZone(Point origin, Point position) {
    	int x              = origin.x > position.x ? position.x : origin.x;
		int y              = origin.y > position.y ? position.y : origin.y;
		int width          = origin.x - position.x < 0 ? position.x - origin.x : origin.x - position.x;
		int height         = origin.y - position.y < 0 ? position.y - origin.y : origin.y - position.y;
		this.selectionZone = new Rectangle(x, y, width, height);
    }
    
    /**
     * Méthode pour définir la zone de sélection par rapport à la position initiale de la souris et la position courante :
     * - définit la zone de sélection
     * - appelle la méthode pour sélectionner les ElementView dans la zone de sélection
     * @param origin le point d'origine de la zone de sélection
     * @param position la position courante de la souris
     */
    public void launchSelectionZone(Point origin, Point position) {
		setSelectionZone(origin, position);
		selectElementsInZone();
		this.repaint();
	}
    
    /**
     * Méthode pour gérer la zone de sélection avec la touche Ctrl enfoncée :
     * - définit la zone de sélection
     * - appelle la méthode pour gérer les ElementView dans la zone de sélection
     * @param origin le point d'origine de la zone de sélection
     * @param position la position courante de la souris
     */
    public void addToSelectionZone(Point origin, Point position) {
		setSelectionZone(origin, position);
		manageElementsInZone();
		this.repaint();
	}

    /**
     * Méthode appellée pour dessiner une Edge temporaire qui n'a pas de substance, pour donner un aperçu à l'utilisateur.
     * @param origin Point depuis lequel part cet Edge temporaire
     * @param position Point jusqu'où elle va
     */
    public void launchTemporarilyEdge(Point origin, Point position) {
        this.originEdge = origin;
        this.destinationEdge = position;
        this.repaint();
    }

    /**
     * Méthode signifiant la fin de l'Edge temporaire et la détruisant
     */
    public void endTemporarilyEdge() {
        this.originEdge = null;
        this.destinationEdge = null;
        this.repaint();
    }
	
    /**
     * Méthode pour sélectionner les ElementView présents dans la zone de sélection en la vidant au préalable
     */
	public void selectElementsInZone() {
		clearSelectedElements();
		for (VertexView v : this.vertexes) {
			if (this.selectionZone.contains(v.getPosition())) {
				selectElement(v);
				this.currentSelectedElements.add(v);
			}
		}
		for (EdgeView e : this.edges) {
			if (this.selectionZone.contains(e.getOrigin().getPosition()) && this.selectionZone.contains(e.getDestination().getPosition())) {
				selectElement(e);
				this.currentSelectedElements.add(e);
			}
		}
	}
	
	/**
	 * Méthode permettant de gérer la sélection des ElementView dans la zone de sélection avec la touche Ctrl enfoncée
	 */
	public void manageElementsInZone() {
		clearSelectedElements();
		for (VertexView v : this.vertexes) {
			if (this.selectionZone.contains(v.getPosition())) {
				selectElement(v);
			}
		}
		for (EdgeView e : this.edges) {
			if (edgeIsInSelectionZone(e)) {
				selectElement(e);
			}
		}
		for (ElementView e : this.currentSelectedElements) {
			selectElement(e);
		}
	}
	
	/**
	 * Méthode permettant de gérer la sélection d'un ElementView : 
	 *  - si l'élément est déjà sélectionné, le désélectionne
	 *  - sinon, le sélectionne
	 * @param e l'ElementView à sélectionner ou déselectionner 
	 */
	public void handleSelectElement(ElementView e) {
		if (this.selectedElements.contains(e) && this.currentSelectedElements.contains(e)) {
			unselectElement(e);
		} else if (!this.currentSelectedElements.contains(e)){
			selectElement(e);
			this.currentSelectedElements.add(e);
		}
	}
	
	/**
	 * Méthode permettant de savoir si un EdgeView est dans la zone de sélection
	 * @param e l'EdgeView
	 * @return le résultat sous la forme d'un booléen
	 */
	public boolean edgeIsInSelectionZone(EdgeView e) {
		Point origin      = e.getOrigin().getPosition();
		Point destination = e.getDestination().getPosition();
		int x              = origin.x > destination.x ? destination.x : origin.x;
		int y              = origin.y > destination.y ? destination.y : origin.y;
		int width          = origin.x - destination.x < 0 ? destination.x - origin.x : origin.x - destination.x;
		int height         = origin.y - destination.y < 0 ? destination.y - origin.y : origin.y - destination.y;
		return this.selectionZone.intersects(new Rectangle(x, y, width, height));
	}

	/**
	 * Méthode pour clore la sélection d'ElementView :
	 * - sélectionne les ElementView dans la zone de sélection
	 * - détruit la zone de sélection
	 * - redessine la feuille de dessin
	 */
	public void handleSelectionZone() {
		selectElementsInZone();
		this.selectionZone = null;
		this.repaint();
	}
	
	public void handleEndSelectionZone() {
		this.selectionZone = null;
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
    	final EdgeView edgeView = new EdgeView(edge, this.defaultSelectedThickness, this.defaultSelectedColor, origin, destination);
    	edgeView.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseClicked(MouseEvent e) {
				controller.getState().click(edgeView, e);
			}
    		@Override
			public void mousePressed(MouseEvent e) {
				controller.getState().pressed(edgeView, e);
			}
    	});
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

    /**
     * Fonction servant à sauvegarder un graphe au format GraphML à l'aide de la classe {@link files.GmlFileManager}
     * @param file Le fichier où sera enregistré le graphe (au format .gml)
     */
    public void saveToGML(File file){
        if(file != null) {
            GmlFileManager gmlFileManager = new GmlFileManager(this.graph, file);
            gmlFileManager.saveGraph();
        }
    }

    public int getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(int currentScale) {
        this.currentScale = currentScale;
    }
}
