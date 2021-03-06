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
 * Classe Sheet, feuille de dessin affichant un {@link data.Graph} dans l'application, est un {@link Observer} de la classe Graph
 */
public class Sheet extends JComponent implements Observer {

	private static final long       serialVersionUID = 1L;
    /* L'onglet conteneur de la feuille de dessin */
    private Tab                     tab;
    /* Le Graph qui est dessiné */
    private Graph                   graph;
    /* Le Controller de l'application */
    private Controller              controller;

    /* La liste des EdgeView affichées */
    private ArrayList<EdgeView>     edges;
    /* La liste des VertexView affichés */
    private ArrayList<VertexView>   vertices;

    /* La liste des ElementView sélectionnés dans la zone précédente (avant le Ctrl enfoncé) */
    private ArrayList<ElementView>  previousSelectedElements;
    /* La liste des ElementView sélectionnés */
    private ArrayList<ElementView>  selectedElements;
    /* La liste des ElementView qui sont actuellement dans la zone de sélection */
    private ArrayList<ElementView>  currentSelectedElements;

    /* La liste des positions avant le déplacement des ElementView sélectionnés */
    private ArrayList<SnapPosition> previousPositions;

    /* Le nom de la feuille de dessin */
    private String                  name;
    /* Le nom du fichier associé à la feuille de dessin */
    private String                  file;
    /* La couleur par défaut de la feuille de dessin */
    private Color                   defaultSheetColor;
    /* La couleur par défaut des VertexView */
    private Color                   defaultVerticesColor;
    /* La couleur par défaut des EdgeView */
    private Color                   defaultEdgesColor;
    /* L'épaisseur par défaut à rajouter aux EdgeView sélectionnées */
    private int                     defaultSelectedThickness;
    /* L'épaisseur par défaut des EdgeView */
    private int                     defaultEdgesThickness;
    /* La taille par défaut des VertexView */
    private int                     defaultVerticesSize;
    /* La forme par défaut des VertexView */
    private Vertex.Shape            defaultVerticesShape;
    /* Si on doit afficher les labels des ElementViews ou non */
    private boolean                 paintLabels;
    
    /** Sélection par zone **/
    private Rectangle               selectionZone;

    /** Edge temporaire **/
    private Point                   originEdge;
    private Point                   destinationEdge;
    private Color                   edgeColor;

    /* L'échelle du zoom */
    private double                  scale;

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
        this.previousSelectedElements = new ArrayList<>();

        this.previousPositions        = new ArrayList<>();

        this.defaultSheetColor        = new Color(248, 248, 248);
        this.defaultVerticesColor     = new Color(39, 39, 39);
        this.defaultEdgesColor        = new Color(39, 39, 39);
        this.defaultEdgesThickness    = 1;
        this.defaultSelectedThickness = 2;
        this.defaultVerticesSize      = 15;
        this.defaultVerticesShape     = Vertex.Shape.SQUARE;
        this.paintLabels              = true;

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
        g.setColor(this.defaultSheetColor);
        g.fillRect(0, 0, getWidth(), getHeight());
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
            e.paintComponent(g, this.scale, this.scale, this.paintLabels);
        }
        // dessiner tous les VertexView
        for (VertexView v : this.vertices) {
            v.paintComponent(g, this.scale, this.scale, this.paintLabels);
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
     * Méthode permettant de récupérer la position du VertexView dans sa liste en fonction de l'ID
     * @param ID l'ID du VertexView à trouver
     * @return la position du VertexView dans la liste des VertexView
     */
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
     * Méthode permettant de récupérer la position de l'EdgeView dans sa liste en fonction de l'ID
     * @param ID l'ID de l'EdgeView à trouver
     * @return la position de l'EdgeView dans la liste des EdgeView
     */
    public int getEdgePositionFromID(int ID){
        for(EdgeView ev : this.edges){
            if(ev.getGraphElement().getID() == ID){
                return this.edges.indexOf(ev);
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

    /**
     * Getter du Tab
     * @return le Tab
     */
    public Tab getTab() {
        return tab;
    }

    /**
     * Méthode pour modifier le premier élément sélectionné
     */
    public void modifySelectedElements() {
        if(selectedElements.size()==1){
            SnapProperties snapBefore=null;
            if(selectedElements.get(0).isVertexView()) {
                snapBefore = new SnapVertex((Vertex) selectedElements.get(0).getGraphElement(), graph.getVertexes().indexOf(selectedElements.get(0).getGraphElement()));
            }
            else {
                snapBefore = new SnapEdge((Edge) selectedElements.get(0).getGraphElement(), graph.getEdges().indexOf(selectedElements.get(0).getGraphElement()));

            }
            SnapProperties snapAfter= selectedElements.get(0).modify(this.graph);

            if(snapAfter!=null)
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
            this.setDefaultSheetColor(tpve.getSheetColor());
            this.setDefaultVerticesSize(tpve.getVertexSize());
            this.setDefaultVerticesColor(tpve.getVertexColor());
            this.setDefaultEdgesThickness(tpve.getEdgeThickness());
            this.setDefaultEdgesColor(tpve.getEdgeColor());
            this.setDefaultVerticesShape(tpve.getVertexShape());
            this.setPreferredSize(tpve.getNewSize());
            this.setMaximumSize(tpve.getNewSize());
            CustomUIManager.setHoverColor(tpve.getHoverColor());
            this.paintLabels = tpve.isPaintingLabels();

            this.graph.setChanged();
        }
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
     * Modifier pour récupérer la liste des ElementView sélectionnés
     * @return la liste des ElementView sélectionnés
     */
    public ArrayList<ElementView> getSelectedElements(){
        return this.selectedElements;
    }

    /**
     * Méthode permettant de récupérer le rectangle englobant les VertexView passés en paramètres afin de connaitre le rectangle qui les contient
     * @param vertices les VertexVeiew dont on veut connaitre le rectangle englobeur
     * @return le rectangle englobant les VertexView
     */
    public Rectangle getVerticesBounds(ArrayList<VertexView> vertices) {
        Rectangle verticesBounds;
        if (vertices.isEmpty()) {
            return new Rectangle();
        } else {
            verticesBounds = vertices.get(0).getVertexBounds();
            for (VertexView vertex : vertices) {
                Rectangle bounds      = vertex.getVertexBounds();
                verticesBounds.x      = bounds.x < verticesBounds.x ? bounds.x : verticesBounds.x;
                verticesBounds.y      = bounds.y < verticesBounds.y ? bounds.y : verticesBounds.y;
                verticesBounds.width  = bounds.x + bounds.width - verticesBounds.x > verticesBounds.width ? bounds.x + bounds.width - verticesBounds.x : verticesBounds.width;
                verticesBounds.height = bounds.y + bounds.height - verticesBounds.y> verticesBounds.height ? bounds.y + bounds.height - verticesBounds.y : verticesBounds.height;
            }
            return verticesBounds;
        }
    }

    /**
     * Méthode pour initialiser le déplacement des ElementView sélectionnés
     *  - sauvegarde les positions initiales en vue de l'undo & redo
     * @param vector le décalage à effectuer pour chaque élément par rapport à leur position
     */
    public void initMovingSelectedElements(Point vector) {
        this.previousPositions.clear();
        Point tmpPosition;
        int   tmpIndex;
        for (ElementView element : this.selectedElements) {
            if (element.getGraphElement().isVertex()) {
                tmpPosition = ((Vertex)element.getGraphElement()).getPosition();
                tmpIndex    = graph.getVertexes().indexOf(element.getGraphElement());
                this.previousPositions.add(new SnapPosition(tmpPosition,tmpIndex));
                ((VertexView) element).move(new Point((int) (vector.x / this.scale), (int) (vector.y / this.scale)));
            }
        }
    }

    /**
     * Méthode pour clore le déplacement des ElementView sélectionnés
     *  - récupère la position finale des VertexView pour enregistrer le déplacement dans l'undo & redo
     */
    public void endMovingSelectedElements() {
        ArrayList<SnapPosition> after = new ArrayList<>();
        Point tmpPosition;
        int   tmpIndex;
        for (ElementView element : this.selectedElements) {
            if (element.getGraphElement().isVertex()) {
                tmpPosition = ((Vertex)element.getGraphElement()).getPosition();
                tmpIndex    = graph.getVertexes().indexOf(element.getGraphElement());
                after.add(new SnapPosition(tmpPosition, tmpIndex));
            }
        }
        tab.getUndoRedo().registerMoveEdit(this.previousPositions, after);
    }

    /**
     * Méthode pour déplacer les ElementView sélectionnés
     * @param vector le décalage à effectuer pour chaque élément par rapport à leur position
     * @param sheetDimension la dimension de la fenêtre
     */
    public void moveSelectedElements(Point vector, Dimension sheetDimension) {
        ArrayList<VertexView> vertices = new ArrayList<>();
        for (ElementView element : this.selectedElements) {
            if (element.isVertexView()) {
                vertices.add((VertexView) element);
            }
        }
        // Récupérer le rectangle englobant les ElementView sélectionnés
        Rectangle verticesBounds = getVerticesBounds(vertices);
        if (verticesBounds.x + vector.x < 0) {
            vector.x = 0 - verticesBounds.x;
        } else if (verticesBounds.x + verticesBounds.width + vector.x > sheetDimension.width) {
            vector.x = sheetDimension.width - verticesBounds.x - verticesBounds.width;
        }
        if (verticesBounds.y + vector.y < 0) {
            vector.y = 0 - verticesBounds.y;
        } else if (verticesBounds.y + verticesBounds.height + vector.y > sheetDimension.height) {
            vector.y = sheetDimension.height - verticesBounds.y - verticesBounds.height;
        }
        for (VertexView vertex : vertices) {
            vertex.move(new Point((int) (vector.x / this.scale), (int) (vector.y / this.scale)));
        }
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
        clearCurrentSelectedElements();
        manageElementsInZone();
        ArrayList<ElementView> elementsToRemove = new ArrayList<>(this.selectedElements);
        elementsToRemove.removeAll(this.currentSelectedElements); // on récupère les ElementView qui ne sont plus sélectionnés
        for (ElementView e : elementsToRemove) {
            if (!this.previousSelectedElements.contains(e))
            unselectElement(e);
        }
		this.repaint();
	}

    /**
     * Méthode pour gérer la sélection des ElementView présents dans la zone de sélection en fonction des ElementView sélectionnés précédemment
     */
    public void manageElementsInZone() {
        for (VertexView v : this.vertices) {
            if (this.selectionZone.contains(new Point((int)(v.getPosition().x * this.scale), (int)(v.getPosition().y * this.scale)))) {
                handleSelectElement(v);
            }
        }
        for (EdgeView e : this.edges) {
            if (edgeIsInSelectionZone(e)) {
                handleSelectElement(e);
            }
        }
    }

    /**
     * Méthode pour sélectionner les ElementView présents dans la zone de sélection
     */
	public void selectElementsInZone() {
		for (VertexView v : this.vertices) {
			if (this.selectionZone.contains(new Point((int)(v.getPosition().x * this.scale), (int)(v.getPosition().y * this.scale)))) {
				selectElement(v);
			}
		}
		for (EdgeView e : this.edges) {
            if (edgeIsInSelectionZone(e)) {
				selectElement(e);
			}
		}
	}
	
	/**
	 * Méthode permettant de gérer la sélection d'un ElementView : 
	 *  - si l'élément était déjà sélectionné avant le drag, le désélectionne
	 *  - sinon s'il n'était pas déjà sélectionné, le sélectionne
	 * @param e l'ElementView à sélectionner ou déselectionner 
	 */
	public void handleSelectElement(ElementView e) {
        if (this.previousSelectedElements.contains(e)) { // s'ils étaient sélectionnés à la base, ils sont déselectionnés
            unselectElement(e);
        } else if (!this.selectedElements.contains(e)) { // s'ils n'étaient pas sélectionnés, ils sont sélectionnés
            selectElement(e);
        }
        this.currentSelectedElements.add(e);
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
     * - détruit la zone de sélection
     * - redessine la feuille de dessin
     */
	public void handleEndSelectionZone() {
        this.previousSelectedElements.clear();
        this.previousSelectedElements.addAll(this.selectedElements);
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
     * Getter de la couleur de fond par défaut de la Sheet
     * @return la couleur par défaut
     */
    public Color getDefaultSheetColor() {
        return this.defaultSheetColor;
    }

    /**
     * Setter de la couleur de fond par défaut de la Sheet
     * @param defaultSheetColor la nouvelle couleur par défaut
     */
    public void setDefaultSheetColor(Color defaultSheetColor) {
        this.defaultSheetColor = defaultSheetColor;
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
     * Méthode pour récupérer la dimension de base des Vertex
     * @return la dimension
     */
    public Dimension getDefaultVerticesDimension() {
        return new Dimension(this.defaultVerticesSize > 25 ? this.defaultVerticesSize : 25, this.getDefaultVerticesSize() + 20);
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
        } else {
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

    /**
     * Getter du Controller
     * @return le Controller
     */
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

    /**
     * Fonction servant à sauvegarder un graphe au format graphVIZ à l'aide de la classe {@link files.dot.DotFileManager}
     * @param dot Le fichier où sera enregistré le graphe (au format .dot)
     */
    public void saveToVIZ(File dot) {
        if(dot != null){
            DotFileManager dotFileManager = new DotFileManager(this.graph, dot);
            dotFileManager.saveToDotFile();
        }
    }

    /**
     * Getter de l'échelle du zoom de la feuille de dessin
     * @return l'échelle du zoom
     */
    public double getScale() {
        return scale;
    }

    /**
     * Setter de l'échelle du zoom de la feuille de dessin
     * @param scale la nouvelle échelle du zoom
     */
    public void setScale(double scale) { this.scale = scale; }

    /**
     * Méthoder permettant de modifier les ElementView sélectionnés avec la fenêtre de propriétés et de sauvegarder les propriétés modifiées
     */
    public void modifyElements(){
        if (selectionComposedByVerticesOnly()) {
            ArrayList<SnapProperties> verticesBefore = new ArrayList<>();
            VerticesEditor verticesEditor = new VerticesEditor(this.selectedElements.get(0));

            if (!verticesEditor.isNotModified()) {
                boolean colorModified = verticesEditor.isColorModified();
                boolean labelModified = verticesEditor.isLabelModified();
                boolean sizeModified  = verticesEditor.isSizeModified();
                boolean shapeModified = verticesEditor.isShapeModified();
                boolean indexModified = verticesEditor.isIndexModified();

                Color modifiedColor        = colorModified ? verticesEditor.getNewColor() : null;
                String modifiedLabel       = labelModified ? verticesEditor.getNewLabel() : "none";
                int modifiedSize           = sizeModified ? verticesEditor.getNewSize() : 0;
                Vertex.Shape modifiedShape = shapeModified ? verticesEditor.getNewShape() : null;
                int modifiedIndex          = indexModified ? verticesEditor.getNewIndex() : 0;

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
                        if(indexModified) {
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
                    if(indexModified) {
                        verticesAfter.setValue(modifiedIndex);
                    }

                    tab.getUndoRedo().registerSingleTypeEdit(verticesBefore, verticesAfter);

                }
            }

        } else {
            ElementsEditor elementsViewEditor = new ElementsEditor(this.selectedElements.get(0));

            if(!elementsViewEditor.isNotModified()) {
                boolean colorModified = elementsViewEditor.isColorModified();
                boolean sizeModified  = elementsViewEditor.isSizeModified();
                boolean labelModified = elementsViewEditor.isLabelModified();

                Color modifiedColor   = colorModified ? elementsViewEditor.getNewColor() : null;
                int modifiedSize      = sizeModified ? elementsViewEditor.getNewSize() : 0;
                String modifiedLabel  = labelModified ? elementsViewEditor.getNewName() : "none";

                ArrayList<SnapProperties> propertiesBefore = new ArrayList<>();

                if (colorModified || sizeModified || labelModified) {
                    for (ElementView ev : this.selectedElements) {
                        SnapProperties tmpSnap = new SnapProperties();
                        GraphElement   temp    = ev.getGraphElement();

                        //On récupère les propriétés avant les modifications
                        tmpSnap.setIndex(this.graph.getGraphElements().indexOf(temp));
                        tmpSnap.setColor(temp.getColor());
                        tmpSnap.setLabel(temp.getLabel());

                        if (temp.isVertex()) {
                            tmpSnap.setSize(((Vertex) temp).getValue());
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

    /**
     * Méthode permettant de savoir si la sélection est composée exclusivement de Vertex
     * @return le résultat du test
     */
    public boolean selectionComposedByVerticesOnly(){
        for(ElementView ev : this.selectedElements){
            if(!ev.getGraphElement().isVertex()){
                return false;
            }
        }
        return true;
    }

    /**
     * Getter de la propriété pour afficher ou non les labels
     * @return si on affiche les labels ou non
     */
    public boolean isPaintingLabels() {
        return this.paintLabels;
    }
}
