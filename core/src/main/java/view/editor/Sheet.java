package view.editor;

import controller.Controller;
import data.*;
import files.dot.DotFileManager;
import files.gml.GmlFileManager;
import view.Observer;
import view.editor.elements.EdgeView;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;
import view.frames.ElementsEditor;
import view.frames.SheetPropertiesViewEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Corentin Davidenko on 04/02/15
 * Classe Sheet, feuille de dessin affichant un {@link data.Graph} dans l'application, est un {@link view.Observer} de la classe Graph
 */
public class Sheet extends JComponent implements Observer {

	private static final long      serialVersionUID = 1L;
    private Graph                  graph;
    private Controller             controller;
    
    private ArrayList<EdgeView>    edges;
    private ArrayList<VertexView>  vertices;
    
    private ArrayList<ElementView> selectedElements;
    private ArrayList<ElementView> currentSelectedElements;
    
    private String                 name;
    private String                 file;
    private Color                  defaultVerticesColor;
    private Color                  defaultEdgesColor;
    private Color                  defaultSelectedColor;
    private int                    defaultSelectedThickness;
    private int                    defaultEdgesThickness;
    private int defaultVerticesSize;
    private Vertex.Shape defaultVerticesShape;
    
    /** Sélection par zone **/
    private Rectangle              selectionZone;
    private Color                  selectionColor;
    private Color                  selectionBorderColor;

    /** Edge temporaire **/
    private Point                  originEdge;
    private Point                  destinationEdge;
    private Color                  edgeColor;

    private double                 scale;

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
    public Sheet(Graph graph, Controller controller) {
        super();
        this.graph                    = graph;
        this.controller               = controller;
        
        this.edges                    = new ArrayList<>();
        this.vertices                 = new ArrayList<>();
        this.selectedElements         = new ArrayList<>();
        this.currentSelectedElements  = new ArrayList<>();
        
        this.defaultVerticesColor     = Color.BLACK;
        this.defaultEdgesColor        = Color.BLACK;
        this.defaultSelectedColor     = Color.BLUE;
        this.defaultEdgesThickness    = 1;
        this.defaultSelectedThickness = 2;
        this.defaultVerticesSize      = 15;
        this.defaultVerticesShape     = Vertex.Shape.SQUARE;
        
        this.selectionColor           = new Color(172, 211, 244);
        this.selectionBorderColor     = new Color(107, 153, 189);
        this.selectionZone            = null;

        this.originEdge               = null;
        this.destinationEdge          = null;
        this.edgeColor                = new Color(0,0,0);

        this.scale                    = (float) 1.0;
    }

    /**
     * Méthode permettant de savoir si l'on peut ajouter un VertexView à la position désirée
     * @param position la position du VertexView à ajouter
     * @return un boolean avec le résultat
     */
    public boolean canAddVertex(Point position) {
    	for (VertexView v : this.vertices) {
    		int margin = this.defaultVerticesSize /2;
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
        // dessiner ou non la zone de sélection
    	if (this.selectionZone != null) {
    		g.setColor(this.selectionColor);
    		g.fillRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
    		g.setColor(this.selectionBorderColor);
    		g.drawRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
    	}
        // dessiner ou non l'edge temporaire
        if(this.originEdge != null && this.destinationEdge != null){
            g.setColor(this.edgeColor);
            g.drawLine(this.originEdge.x, this.originEdge.y, this.destinationEdge.x, this.destinationEdge.y);
        }
        // dessiner tous les EdgeView
        for(EdgeView e : this.edges){
            e.paintComponent(g, this.scale, this.scale);
        }
        // dessiner tous les VertexView
        for (VertexView v : this.vertices) {
            v.paintComponent(g, this.scale, this.scale);
        }
    }

    /**
     * Getter de la forme par défaut des VertexView
     * @return la forme par défaut des VertexView
     */
    public Vertex.Shape getDefaultVerticesShape() { // pourquoi cela ne serait pas dans les Vertex plutôt ? Est-ce que tous les Vertex doivent avoir la même forme ?
        return defaultVerticesShape;
    }

    /**
     * Setter de la forme par défaut des VertexView
     * @param defaultVerticesShape la nouvelle forme des VertexView
     */
    public void setDefaultVerticesShape(Vertex.Shape defaultVerticesShape) {
        this.defaultVerticesShape = defaultVerticesShape;
    }

    /**
     * Getter de la liste des VertexView du Tab
     * @return la liste des VertexView
     */
    public ArrayList<VertexView> getVertices() {
        return vertices;
    }

    /**
     * Setter de la liste des VertexView du Tab
     * @param vertices la nouvelle liste des VertexView
     */
    public void setVertices(ArrayList<VertexView> vertices) {
        this.vertices = vertices;
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
    }

    /**
     * Méthode pour ajouter un ElementView à la liste des ElementView sélectionnés
     * @param element le ElementView à ajouter à la liste
     */
    public void selectElement(ElementView element){
    	element.updateHover(true);
        this.selectedElements.add(element);
    }

    /**
     * Méthode permettant de sélectionner la totalité des ElementView du Graph
     */
    public void selectAll() {
        clearSelectedElements();
        for (ElementView e : this.edges) {
            selectElement(e);
        }
        for (ElementView e : this.vertices) {
            selectElement(e);
        }
        this.repaint();
    }

    /**
     * Méthode pour modifier le premier élément sélectionné
     */
    public void modifySelectedElements() {
        if(selectedElements.size()==1){
            selectedElements.get(0).modify(this.graph);

        }else if(selectedElements.size()>=2){
            modifyElements();

        }

        this.graph.setChanged();
        this.repaint();
    }

    /**
     * Méthode pour modifier tous les éléments existants
     */
    public void modifyProperties() {
        SheetPropertiesViewEditor tpve = new SheetPropertiesViewEditor(this);
        this.setDefaultVerticesSize(tpve.getTab().getDefaultVerticesSize());
        this.setDefaultVerticesColor(tpve.getTab().getDefaultVerticesColor());
        this.setDefaultEdgesThickness(tpve.getTab().getDefaultEdgesThickness());
        this.setDefaultEdgesColor(tpve.getTab().getDefaultEdgesColor());
        this.setDefaultVerticesShape(tpve.getTab().getDefaultVerticesShape());
        //this.tab.*******************defaultBackgroundColor ++A faire éventuellement (changer le background complet du tab)
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
		for (VertexView v : this.vertices) {
            Point position = new Point((int)(v.getPosition().x * this.scale), (int)(v.getPosition().y * this.scale));
			if (this.selectionZone.contains(position)) {
				selectElement(v);
				this.currentSelectedElements.add(v);
			}
		}
		for (EdgeView e : this.edges) {
            Point positionO = new Point((int)(e.getOrigin().getPosition().x * this.scale), (int)(e.getOrigin().getPosition().y * this.scale));
            Point positionD = new Point((int)(e.getDestination().getPosition().x * this.scale), (int)(e.getDestination().getPosition().y * this.scale));
			if (this.selectionZone.contains(positionO) && this.selectionZone.contains(positionD)) {
				selectElement(e);
				this.currentSelectedElements.add(e);
			}
		}
	}
	
	/**
	 * Méthode permettant de gérer la sélection des ElementView dans la zone de sélection avec la touche Ctrl enfoncée
     *  - on vide la liste des éléments sélectionnés
     *  - on rajoute ceux qui étaient sélectionnés avant
     *  - on rajoute ceux qui sont dans la nouvelle zone de sélection
	 */
	public void manageElementsInZone() {
		clearSelectedElements();
		for (ElementView e : this.currentSelectedElements) {
			selectElement(e);
		}
		for (VertexView v : this.vertices) {
			if (this.selectionZone.contains(v.getPosition())) {
				selectElement(v);
				this.currentSelectedElements.add(v);
			}
		}
		for (EdgeView e : this.edges) {
			if (edgeIsInSelectionZone(e)) {
				selectElement(e);
				this.currentSelectedElements.add(e);
			}
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
		int x             = origin.x > destination.x ? destination.x : origin.x;
		int y             = origin.y > destination.y ? destination.y : origin.y;
		int width         = origin.x - destination.x < 0 ? destination.x - origin.x : origin.x - destination.x;
		int height        = origin.y - destination.y < 0 ? destination.y - origin.y : origin.y - destination.y;
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
     * Getter de la couleur par défaut des VertexView
     * @return la couleur par défaut
     */
    public Color getDefaultVerticesColor() {
        return this.defaultVerticesColor;
    }

    /**
     * Getter de la couleur par défaut des EdgeView
     * @return la couleur par défaut
     */
    public Color getDefaultEdgesColor() {
        return this.defaultEdgesColor;
    }

    /**
     * Setter de la couleur par défaut des VertexView
     * @param defaultVerticesColor la nouvelle couleur par défaut
     */
    public void setDefaultVerticesColor(Color defaultVerticesColor) {
        this.defaultVerticesColor = defaultVerticesColor;
    }

    /**
     * Setter de la couleur par défaut des EdgeView
     * @param defaultEdgesColor la nouvelle couleur par défaut
     */
    public void setDefaultEdgesColor(Color defaultEdgesColor) {
        this.defaultEdgesColor = defaultEdgesColor;
    }
    
    /**
     * Getter de la taille par défaut des VertexView
     * @return la taille par défaut
     */
    public int getDefaultVerticesSize() {
        return this.defaultVerticesSize;
    }

    /**
     * Setter de la taille par défaut des VertexView
     * @param defaultVerticesSize la nouvelle taille par défaut
     */
    public void setDefaultVerticesSize(int defaultVerticesSize) {
        this.defaultVerticesSize = defaultVerticesSize;
    }

    /**
     * Getter de l'épaisseur par défaut des EdgeView
     * @return l'épaisseur par défaut
     */
    public int getDefaultEdgesThickness() {
        return this.defaultEdgesThickness;
    }

    /**
     * Setter de l'épaisseur par défaut des EdgeView
     * @param defaultEdgesThickness la nouvelle épaisseur par défaut
     */
    public void setDefaultEdgesThickness(int defaultEdgesThickness) {
        this.defaultEdgesThickness = defaultEdgesThickness;
    }

    /**
     * Méthode permettant d'ajouter un VertexView à la position désirée
     * @param vertex le modèle Vertex du VertexView
     */
    public void addVertex(Vertex vertex){
    	final VertexView vertexView = new VertexView(vertex, this.defaultSelectedColor);
        vertexView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { controller.getState().click(vertexView, e); }
            @Override
            public void mousePressed(MouseEvent e) { controller.getState().pressed(vertexView, e); }
            @Override
            public void mouseReleased(MouseEvent e) { controller.getState().released(vertexView, e); }
            @Override
            public void mouseEntered(MouseEvent e) { controller.getState().mouseEntered(vertexView, e); }
            @Override
            public void mouseExited(MouseEvent e) { controller.getState().mouseExited(vertexView, e); }
        });
        vertexView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { controller.getState().drag(vertexView, e); }
        });
        this.vertices.add(vertexView);
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
        this.vertices.clear();
        this.edges.clear();
		super.removeAll();
		// pour chaque GraphElement du Graph
		for (GraphElement element : (ArrayList<GraphElement>)object) {
			if (element.isVertex()) {
				addVertex((Vertex)element);
			} else {
				VertexView src= null, dst = null;
	            ListIterator<VertexView> search = vertices.listIterator();

	            while (search.hasNext() && (src == null || dst == null)){
	                VertexView tmp = search.next();
	                if (tmp.getVertex() == ((Edge) element).getOrigin()) src = tmp;
	                else if (tmp.getVertex() == ((Edge) element).getDestination()) dst = tmp;
	            }
	            if ( src != null && dst != null) addEdge((Edge) element, src, dst);
			}
		}
		this.repaint();
	}

    /**
     * Fonction servant à sauvegarder un graphe au format GraphML à l'aide de la classe {@link files.gml.GmlFileManager}
     * @param file Le fichier où sera enregistré le graphe (au format .gml)
     */
    public void saveToGML(File file){
        if(file != null) {
            GmlFileManager gmlFileManager = new GmlFileManager(this.graph, file);
            gmlFileManager.saveGraph();
        }
    }

    public void saveToVIZ(File dot) {
        if(dot != null){
            DotFileManager dotFileManager = new DotFileManager(this.graph, dot);
            dotFileManager.saveToDotFile();
        }
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) { this.scale = scale; }

    public void modifyElements(){
        ElementsEditor elementsViewEditor = new ElementsEditor(this.selectedElements);

        ArrayList<ElementView> modifiedElements = elementsViewEditor.getElements();

        for(int i=0;i<modifiedElements.size();i++){
            this.selectedElements.get(i).getGraphElement().setColor(modifiedElements.get(i).getGraphElement().getColor());
            this.selectedElements.get(i).getGraphElement().setLabel(modifiedElements.get(i).getGraphElement().getLabel());

            if(this.selectedElements.get(i).getGraphElement().isVertex()){
                ((Vertex)this.selectedElements.get(i).getGraphElement()).setSize(((Vertex)modifiedElements.get(i).getGraphElement()).getSize());
            }else{
                ((Edge)this.selectedElements.get(i).getGraphElement()).setThickness(((Edge)modifiedElements.get(i).getGraphElement()).getThickness());
            }
        }
    }


}
