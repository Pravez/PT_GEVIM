package view.editor.display;

import controller.Controller;
import data.*;
import files.dot.DotFileManager;
import files.gml.GmlFileManager;
import threading.UpdateThread;
import undoRedo.snap.SnapEdge;
import undoRedo.snap.SnapPosition;
import undoRedo.snap.SnapProperties;
import undoRedo.snap.SnapVertex;
import view.UIElements.CustomUIManager;
import view.editor.Tab;
import view.editor.elements.EdgeView;
import view.editor.elements.ElementView;
import view.editor.elements.VertexView;
import view.frames.ElementsEditor;
import view.frames.SheetPropertiesEditor;
import view.frames.VerticesEditor;

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
 * Classe Sheet, feuille de dessin affichant un {@link data.Graph} dans l'application, est un {@link Observer} de la classe Graph
 */
public class Sheet extends JComponent implements Observer {

	private static final long      serialVersionUID = 1L;
    private Tab tab;
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
    private int                    defaultSelectedThickness;
    private int                    defaultEdgesThickness;
    private int                    defaultVerticesSize;
    private Vertex.Shape           defaultVerticesShape;
    
    /** Sélection par zone **/
    private Rectangle              selectionZone;

    /** Edge temporaire **/
    private Point                  originEdge;
    private Point                  destinationEdge;
    private Color                  edgeColor;

    private double                 scale;

    /**
     * Constructeur du Tab, l'onglet. Un onglet est associé à un {@link data.Graph}
     * @param tab l'onglet Tab qui a créé la Feuille de dessin
     * @param graph le Graph observé
     * @param controller le Controller de l'application
     */
    public Sheet(Tab tab, Graph graph, Controller controller) {
        super();
        this.tab                      = tab;
        this.graph                    = graph;
        this.controller               = controller;
        
        this.edges                    = new ArrayList<>();
        this.vertices                 = new ArrayList<>();
        this.selectedElements         = new ArrayList<>();
        this.currentSelectedElements  = new ArrayList<>();
        
        this.defaultVerticesColor     = Color.BLACK;
        this.defaultEdgesColor        = Color.BLACK;
        this.defaultEdgesThickness    = 1;
        this.defaultSelectedThickness = 2;
        this.defaultVerticesSize      = 15;
        this.defaultVerticesShape     = Vertex.Shape.SQUARE;

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
    		g.setColor(CustomUIManager.selectionColor);
    		g.fillRect(this.selectionZone.x, this.selectionZone.y, this.selectionZone.width, this.selectionZone.height);
    		g.setColor(CustomUIManager.selectionBorderColor);
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


    public int getVertexPositionFromID(int ID){
        for(VertexView vv : vertices){
            if(vv.getGraphElement().getID() == ID){
                return vertices.indexOf(vv);
            }
        }
        return -1;
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

    public int getEdgePositionFromID(int ID){
        for(EdgeView ev : edges){
            if(ev.getGraphElement().getID() == ID){
                return edges.indexOf(ev);
            }
        }
        return -1;
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
    }

    public Tab getTab() {
        return tab;
    }

    /**
     * Méthode pour modifier le premier élément sélectionné
     */
    public void modifySelectedElements() {
        if(selectedElements.size()==1){

            SnapProperties snapBefore;
            if(selectedElements.get(0).isVertexView()) {
                snapBefore = new SnapVertex((Vertex) selectedElements.get(0).getGraphElement(), graph.getVertexes().indexOf(selectedElements.get(0).getGraphElement()));
            }
            else {
                snapBefore = new SnapEdge((Edge) selectedElements.get(0).getGraphElement(), graph.getEdges().indexOf(selectedElements.get(0).getGraphElement()));

            }
            SnapProperties snapAfter= selectedElements.get(0).modify(this.graph);

            tab.getUndoRedo().registerSingleTypeEdit(snapBefore, snapAfter);

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

        SheetPropertiesEditor tpve = new SheetPropertiesEditor(this);

        if(!tpve.isCancelled()) {
            this.setDefaultVerticesSize(tpve.getVertexSize());
            this.setDefaultVerticesColor(tpve.getVertexColor());
            this.setDefaultEdgesThickness(tpve.getEdgeThickness());
            this.setDefaultEdgesColor(tpve.getEdgeColor());
            this.setDefaultVerticesShape(tpve.getVertexShape());
        }
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

        ArrayList<SnapPosition> before = new ArrayList<>();
        ArrayList<SnapPosition> after = new ArrayList<>();
        Point tmpPosition;
        SnapPosition snap;
        int tmpIndex;
    	for (ElementView element : this.selectedElements) {
    		if (element.getGraphElement().isVertex()) {
                tmpPosition=((Vertex) (((VertexView) element).getGraphElement())).getPosition();
                tmpIndex=graph.getVertexes().indexOf((Vertex) (((VertexView) element).getGraphElement()));
                snap = new SnapPosition(tmpPosition,tmpIndex );
                before.add(snap);
                        ((VertexView) element).move(new Point((int) (vector.x / this.scale), (int) (vector.y / this.scale)));
                tmpPosition=((Vertex) (((VertexView) element).getGraphElement())).getPosition();
                snap = new SnapPosition(tmpPosition, tmpIndex);
                after.add(snap);
            }
    	}

        tab.getUndoRedo().registerMoveEdit(before, after);

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
        clearSelectedElements();
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
        clearSelectedElements();
        for (ElementView e : this.currentSelectedElements) {
            selectElement(e);
        }
        selectElementsInZone();
		this.repaint();
	}

    /**
     * Méthode appellée pour dessiner une Edge temporaire qui n'a pas de substance, pour donner un aperçu à l'utilisateur.
     * @param origin Point depuis lequel part cet Edge temporaire
     * @param position Point jusqu'où elle va
     */
    public void launchTemporarilyEdge(Point origin, Point position) {
        this.originEdge      = origin;
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
     * Méthode pour sélectionner les ElementView présents dans la zone de sélection
     */
	public void selectElementsInZone() {
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
    	final VertexView vertexView = new VertexView(vertex);
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
    	final EdgeView edgeView = new EdgeView(edge, this.defaultSelectedThickness, origin, destination);
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
     * @see Observer#update(data.Observable, java.lang.Object)
     */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object object) {
        this.vertices.clear();
        this.edges.clear();
		super.removeAll();

        ArrayList<GraphElement> elements = (ArrayList<GraphElement>)object;

        if(elements.size()<1000) {
            // pour chaque GraphElement du Graph
            for (GraphElement element : elements) {
                if (element.isVertex()) {
                    addVertex((Vertex) element);
                }
            }

            for (GraphElement element : elements) {
                if (!element.isVertex()) {
                    VertexView src = null, dst = null;
                    ListIterator<VertexView> search = vertices.listIterator();

                    while (search.hasNext() && (src == null || dst == null)) {
                        VertexView tmp = search.next();
                        if (tmp.getVertex().getID() == ((Edge) element).getOrigin().getID()) src = tmp;
                        else if (tmp.getVertex().getID() == ((Edge) element).getDestination().getID()) dst = tmp;
                    }
                    if (src != null && dst != null) addEdge((Edge) element, src, dst);
                }
            }
        }else{
            UpdateThread updateThread = new UpdateThread(this, elements, this.controller);
            this.vertices.addAll(updateThread.getVertices());
            this.edges.addAll(updateThread.getEdges());
        }

        this.repaint();
	}

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

    public Controller getController(){
        return this.controller;
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

        if(selectionComposedByVerticesOnly()){

            ArrayList<SnapProperties> verticesBefore = new ArrayList<>();

            VerticesEditor verticesEditor = new VerticesEditor(this.selectedElements.get(0));

            if(!verticesEditor.isNotModified()) {

                boolean colorModified = verticesEditor.isColorModified();
                boolean labelModified = verticesEditor.isLabelModified();
                boolean sizeModified = verticesEditor.isSizeModified();
                boolean shapeModified = verticesEditor.isShapeModified();
                boolean indexModified = verticesEditor.isIndexModified();

                Color modifiedColor = colorModified ? verticesEditor.getNewColor() : null;
                String modifiedLabel = labelModified ? verticesEditor.getNewLabel() : "none";
                int modifiedSize = sizeModified ? verticesEditor.getNewSize() : 0;
                Vertex.Shape modifiedShape = shapeModified ? verticesEditor.getNewShape() : null;
                int modifiedIndex = indexModified ? verticesEditor.getNewIndex() : 0;

                if (colorModified || labelModified || shapeModified || sizeModified || indexModified) {

                    for (ElementView ev : this.selectedElements) {
                        Vertex v = (Vertex) ev.getGraphElement();

                        SnapVertex tmpSnap = new SnapVertex(v, graph.getVertexes().indexOf(v));
                        verticesBefore.add(tmpSnap);


                        if (colorModified) {
                            v.setColor(modifiedColor);
                        }
                        if (labelModified) {
                            v.setLabel(modifiedLabel);
                        }
                        if (shapeModified) {
                            v.setShape(modifiedShape);
                        }
                        if (sizeModified) {
                            v.setSize(modifiedSize);
                        }
                        if(indexModified){
                            v.setValue(modifiedIndex);
                        }
                    }

                    SnapVertex verticesAfter = new SnapVertex();
                    if (colorModified) {
                        verticesAfter.setColor(modifiedColor);
                    }
                    if (labelModified) {
                        verticesAfter.setLabel(modifiedLabel);
                    }
                    if (shapeModified) {
                        verticesAfter.setShape(modifiedShape);
                    }
                    if (sizeModified) {
                        verticesAfter.setSize(modifiedSize);
                    }
                    if(indexModified)
                    {
                        verticesAfter.setValue(modifiedIndex);
                    }

                    tab.getUndoRedo().registerSingleTypeEdit(verticesBefore, verticesAfter);

                }
            }

        }else{

            ElementsEditor elementsViewEditor = new ElementsEditor(this.selectedElements.get(0));

            if(!elementsViewEditor.isNotModified()) {

                boolean colorModified = elementsViewEditor.isColorModified();
                boolean sizeModified = elementsViewEditor.isSizeModified();
                boolean labelModified = elementsViewEditor.isLabelModified();

                Color modifiedColor = colorModified ? elementsViewEditor.getNewColor() : null;
                int modifiedSize = sizeModified ? elementsViewEditor.getNewSize() : 0;
                String modifiedLabel = labelModified ? elementsViewEditor.getNewName() : "none";

                ArrayList<SnapProperties> propertiesBefore = new ArrayList<>();


                if (colorModified || sizeModified || labelModified) {
                    for (ElementView ev : this.selectedElements) {
                        SnapProperties tmpSnap = new SnapProperties();
                        GraphElement temp = ev.getGraphElement();

                        //On récupère les propriétés avant les modifications
                        tmpSnap.setIndex(this.graph.getGraphElements().indexOf(temp));
                        tmpSnap.setColor(temp.getColor());
                        tmpSnap.setLabel(temp.getLabel());

                        if (temp.isVertex()) {
                            tmpSnap.setSize(((Vertex) temp).getSize());
                        } else
                            tmpSnap.setSize(((Edge) temp).getThickness());
                        if (colorModified) {
                            temp.setColor(modifiedColor);
                        }
                        if (sizeModified) {
                            if (temp.isVertex()) {
                                ((Vertex) temp).setSize(modifiedSize);
                            } else {
                                ((Edge) temp).setThickness(modifiedSize);
                            }

                        }
                        if (labelModified) {
                            temp.setLabel(modifiedLabel);
                        }
                        propertiesBefore.add(tmpSnap);
                    }

                    SnapProperties propertiesAfter = new SnapProperties();

                    if (colorModified) {
                        propertiesAfter.setColor(modifiedColor);
                    }
                    if (sizeModified) {
                        propertiesAfter.setSize(modifiedSize);

                    }
                    if (labelModified) {
                        propertiesAfter.setLabel(modifiedLabel);
                    }

                    tab.getUndoRedo().registerPropertiesEdit(propertiesBefore, propertiesAfter);
                }
            }

        }

    }

    public boolean selectionComposedByVerticesOnly(){

        for(ElementView ev : this.selectedElements){
            if(!ev.getGraphElement().isVertex()){
                return false;
            }
        }

        return true;
    }
}
