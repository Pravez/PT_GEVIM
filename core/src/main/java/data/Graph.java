package data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe Graph. Gère les interactions entres les {@link data.Edge} et les {@link data.Vertex}
 */
public class Graph extends Observable {

    private ArrayList<GraphElement> elements;
    private String                  name;

    /**
     * Constructeur de la classe Graph
     */
    public Graph() {
        this.elements = new ArrayList<GraphElement>();
    }

    /**
     * Getter de la liste des GraphElement du Graph
     * @return la liste des GraphElement
     */
    public ArrayList<GraphElement> getGraphElements() {
        return this.elements;
    }

    /**
     * Setter de la liste des GraphElement du graph
     * @param elements la nouvelle liste de GraphElement
     */
    public void setGraphElements(ArrayList<GraphElement> elements) {
        this.elements = elements;
    }
    
    /**
     * Méthode permettant d'ajouter une liste de GraphElement au Graph
     * @param elements la liste de GraphElement à ajouter
     */
    public void addGraphElements(ArrayList<GraphElement> elements) {
    	this.elements.addAll(elements);
    }
    
    /**
     * Méthode statique pour copier des GraphElement dans une nouvelle ArrayList en créant de nouveaux Vertex et de nouveaux Edge
     * @param elements la liste des GraphElement à copier
     * @return la liste des GraphElement copiés
     */
    public static ArrayList<GraphElement> copyGraphElements(ArrayList<GraphElement> elements) {

    	ArrayList<GraphElement> new_elements = new ArrayList<> (elements.size());
    	for (int i = 0 ; i < elements.size() ; i++) {
    		new_elements.add(null);
    	}
    	// on ajoute tous les sommets dans la liste des nouveaux GraphElement
    	for (int i = 0 ; i < elements.size() ; i++) {
    		if (elements.get(i).isVertex()) {
    			new_elements.set(i, new Vertex((Vertex) elements.get(i)));
    		}
    	}
    	// on ajoute les edges une fois que tous les sommets on été ajoutés dans la liste des nouveaux GraphElement
    	for (int i = 0 ; i < elements.size() ; i++) {
    		if (!elements.get(i).isVertex()) {
    			int origin      = elements.indexOf(((Edge)elements.get(i)).getOrigin());
    			int destination = elements.indexOf(((Edge)elements.get(i)).getDestination());
    			new_elements.set(i, new Edge(elements.get(i). getLabel(), elements.get(i).getColor(), (Vertex)new_elements.get(origin), (Vertex)new_elements.get(destination), ((Edge)elements.get(i)).getThickness()));
    		}
    	}
    	return new_elements;
    }

    public static Point findBestCopyPoint(ArrayList<GraphElement> elements){

        Point bestPoint = new Point(2000,2000);

        for(GraphElement ge : elements){
            if(ge.isVertex()){
                Point currentPosition = ((Vertex)ge).getPosition();
                if(currentPosition.x < bestPoint.x){
                    bestPoint.x = currentPosition.x;
                }
                if(currentPosition.y < bestPoint.y){
                    bestPoint.y = currentPosition.y;
                }
            }
        }

        return bestPoint;

    }
    
    /**
     * Méthode statique pour copier des GraphElement dans une nouvelle ArrayList en créant de nouveaux Vertex et de nouveaux Edge à une position donnée
     * @param elements la liste des GraphElement à copier
     * @param position la position du centre de la zone contenant les GraphElement
     * @param sheetDimension la dimension de la Feuille de dessin
     * @param vertexDimension la dimension des Vertex
     * @return la liste des GraphElement copiés
     */
    public static ArrayList<GraphElement> copyGraphElements(ArrayList<GraphElement> elements, Point position, Dimension sheetDimension, Dimension vertexDimension) {
    	ArrayList<GraphElement> new_elements = new ArrayList<GraphElement> (elements.size());
    	Point min = new Point(0, 0);
    	Point max = new Point(0, 0);
    	for(GraphElement e : elements) {
    		if (e.isVertex()) {
    			min = new Point(((Vertex)e).getPosition());
    			max = new Point(min);
    			break;
    		}
    	}
        for (GraphElement element : elements) {
            new_elements.add(null);
            if (element.isVertex()) {
                min.x = ((Vertex) element).getPosition().x < min.x ? ((Vertex) element).getPosition().x : min.x;
                min.y = ((Vertex) element).getPosition().y < min.y ? ((Vertex) element).getPosition().y : min.y;
                max.x = ((Vertex) element).getPosition().x > max.x ? ((Vertex) element).getPosition().x : max.x;
                max.y = ((Vertex) element).getPosition().y > max.y ? ((Vertex) element).getPosition().y : max.y;
            }
        }

        // Pour que les GraphElement ne soient pas copiés en dehors de la Sheet
        int width  = max.x - min.x;
        int height = max.y - min.y;
        if (position.x - width/2 < vertexDimension.width/2) {
            position.x = vertexDimension.width/2 + width/2;
        } else if (position.x + width/2 > sheetDimension.getWidth() - vertexDimension.width/2) {
            position.x = (int) (sheetDimension.getWidth() - vertexDimension.width/2 - width/2);
        }
        if (position.y - height/2 < vertexDimension.height/2) {
            position.y = vertexDimension.height/2 + height/2;
        } else if (position.y + height/2 > sheetDimension.getHeight() - vertexDimension.height/2) {
            position.y = (int) (sheetDimension.getHeight() - vertexDimension.height/2 - height/2);
        }

    	// on ajoute tous les sommets dans la liste des nouveaux GraphElement
    	for (int i = 0 ; i < elements.size() ; i++) {
    		if (elements.get(i).isVertex()) {
    			new_elements.set(i, new Vertex((Vertex) elements.get(i)));
    			int new_min_x = position.x - width/2;
    			int new_min_y = position.y - height/2;
    			((Vertex)new_elements.get(i)).move(new_min_x - min.x, new_min_y - min.y);
    		}
    	}
    	// on ajoute les edges une fois que tous les sommets on été ajoutés dans la liste des nouveaux GraphElement
    	for (int i = 0 ; i < elements.size() ; i++) {
    		if (!elements.get(i).isVertex()) {
    			int origin      = elements.indexOf(((Edge)elements.get(i)).getOrigin());
    			int destination = elements.indexOf(((Edge)elements.get(i)).getDestination());
    			new_elements.set(i, new Edge(elements.get(i). getLabel(), elements.get(i).getColor(), (Vertex)new_elements.get(origin), (Vertex)new_elements.get(destination), ((Edge)elements.get(i)).getThickness()));
    		}
    	}
    	return new_elements;
    }

    /**
     * Getter de la liste des Edge du Graph
     * @return la liste des Edge
     */
    public ArrayList<Edge> getEdges() {
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	for(GraphElement element : this.elements) {
    		if (!element.isVertex()) {
    			edges.add((Edge) element);
    		}
    	}
        return edges;
    }

    /**
     * Getter de la liste des Vertex du Graph
     * @return la liste des Vertex
     */
    public ArrayList<Vertex> getVertexes() {
    	ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
    	for(GraphElement element : this.elements) {
    		if (element.isVertex()) {
    			vertexes.add((Vertex) element);
    		}
    	}
        return vertexes;
    }

    /**
     * Getter du nom du Graph
     * @return le nom du Graph
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter du nom du Graph
     * @param name le nouveau nom du Graph
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Méthode pour ajouter un Vertex au Graph
     * @param color la couleur du Vertex à créer
     * @param position la position du Vertex à créer
     * @param size la taille du Vertex à créer
     * @param shape la forme du Vertex à créer
	 * @return vertex copie du vertex destinée au UndoPanel
     */
	public Vertex createVertex(Color color, Point position, int size, Vertex.Shape shape) {
		Vertex vertex = new Vertex(color, position, size, shape);
		this.elements.add(vertex);
		return vertex;
	}
    /**
     * Creates an edge between two vertexes
     * @param color la couleur de l'Edge à créer
     * @param origin le Vertex d'origine de l'Edge à créer
     * @param destination le Vertex de destination de l'Edge à créer
     * @param thickness l'épaisseur de l'Edge à créer
	 * @return edge copie du vertex destinée au UndoPanel
     */
    public Edge createEdge(Color color, Vertex origin, Vertex destination, int thickness) {
    	Edge edge = new Edge(color, origin, destination, thickness);
        this.elements.add(edge);
		return edge;
    }

    /**
     * Méthode pour changer la position d'un Vertex du Graph
     * @param vertex le Vertex à déplacer
     * @param destination les coordonnées de destination
     */
    public void moveVertex(Vertex vertex, Point destination){
        vertex.setPosition(destination);
        this.setChanged();
    }

    /**
     * Méthode pour déplacer une liste de Vertex dans une certaine direction
     * @param vertexes la liste des Vertex à déplacer
     * @param vectorX la direction du vecteur de déplacement en abscisse
     * @param vectorY la direction du vecteur de déplacement en ordonnée
     */
    public void moveVertexes(ArrayList<Vertex> vertexes, int vectorX, int vectorY){
        for(Vertex vertex : vertexes){
            vertex.move(vectorX,vectorY);
        }
        this.setChanged();
    }
    
    /**
     * Supprime un GraphElement
     * @param element GraphElement qui doit être supprimé
     */
    public void removeGraphElement(GraphElement element) {
    	if (element.isVertex()) {
            //On utilise un endroit temporaire où l'on stocke les Edges à supprimer dans les Vertices pour
            //eviter les accès concurrents lors de la lecture dans les edges du vertex
            ArrayList<Edge> toClear = new ArrayList<>();

    		for(Edge e : ((Vertex) element).getEdges()){
                toClear.add(e);
                this.elements.remove(e);
            }
            for(Edge e : toClear){
                clearLinkedVertices(e);
            }
            toClear.clear();
    	}else{
            clearLinkedVertices((Edge) element);
        }
        this.elements.remove(element);
    }

    /**
     * Méthode nettoyant la présence d'une {@link data.Edge} dans ses Vertices d'origine et de destination
     * @param e L'edge à supprimer des Vertices d'origine et de destination
     */
    public void clearLinkedVertices(Edge e){
        
        int originPosition = e.getDestination().getEdges().indexOf(e);
        int destinationPosition = e.getOrigin().getEdges().indexOf(e);
        
        if(originPosition != -1){
            e.getDestination().getEdges().remove(originPosition);
        }
        if(destinationPosition != -1){
            e.getOrigin().getEdges().remove(destinationPosition);
        }
    }

	/**
	 * Ajoute un GraphElement
	 * @param element GraphElement qui doit être inséré
	 */
	public void createGraphElement(GraphElement element) {

		this.elements.add(element);
	}


	/**
     * (non-Javadoc)
     * @see data.Observable#setChanged()
     */
	@Override
	public void setChanged() {
		this.notifyObservers(this.elements);
	}

	/**
	 * (non-Javadoc)
	 * @see data.Observable#getState()
	 */
	@Override
	public Object getState() {
		return this.elements;
	}

    /**
     * Méthode permettant de récupérer un {@link data.GraphElement} en ne connaissant que son ID, dans un {@link data.Graph}.
     * @param id L'ID (valeur) de l'élément à récupérer.
     * @return Le {@link data.GraphElement} dont l'attribut Value est la value passée en paramètre
     */
    public GraphElement getFromID(int id){
        for(GraphElement ge : this.elements){
            if(ge.getID()==id){
                return ge;
            }
        }
        return null;
    }

    /**
     * Méthode renvoyant un booléen pour tester la présence d'une {@link data.Edge} entre deux {@link data.Vertex}.
     * @param one Le premier vertex
     * @param two Le deuxième vertex
     * @return La présence de l'Edge
     */
    public boolean existsBetweenVertices(Vertex one, Vertex two){
        for(Edge Oe : one.getEdges()){
            for(Edge Te : two.getEdges()){
                if(Oe.equals(Te)){
                    return true;
                }
            }
        }

        return false;
    }


}