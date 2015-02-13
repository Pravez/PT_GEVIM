package view;

import data.Graph;
import data.Observable;

import javax.swing.*;

import controller.Controller;
import controller.VertexMouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Corentin Davidenko on 04/02/15.
 */

public class Tab extends JPanel implements Observer {

	private static final long     serialVersionUID = 1L;
    private Graph                 graph;
    private Controller            controller;
    
    private ArrayList<EdgeView>   edges;
    private ArrayList<VertexView> vertexes;
    
    private ArrayList<EdgeView>   selectedEdges;
    private ArrayList<VertexView> selectedVertexes;
    
    private String            name;
    private String            file;
    private Color             defaultColor;
    private Color             defaultSelectedColor;
    private int               defaultSelectedThickness;
    private int               defaultThickness;
    private int               defaultWidth;
    private Shape             defaultShape;

    public Graph getGraph() {
        return this.graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * Constructeur du Tab, l'onglet. Un onglet est associé à un {@link data.Graph}
     * @param graph Graphe devant être associé
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
        this.defaultWidth             = 10;
    }
    
    public boolean canAddVertex(Point position) {
    	for (VertexView v : this.vertexes) {
    		int margin = this.defaultWidth/2;
    		int side   = v.getWidth() + margin*3; 
    		if (new Rectangle(v.getPositionX() - margin, v.getPositionY() - margin, side, side).contains(position))
    			return false;
    	}
    	return true;
    }

    /**
     * Méthode de dessin des éléments dans un onglet, à partir des données d'un {@link data.Graph}
     * @param g {@link java.awt.Graphics} à partir de quoi dessiner
     */
    public void paintComponent(Graphics g){
    	/**/
    	Stroke oldStroke = ((Graphics2D) g).getStroke();
    	for (VertexView v : graph.getVertexes()) {
    		v.paintComponent(g);
    	}
    	/**/
        /*for(Vertex v : graph.getVertexes()){
            g.setColor(v.getColor());
            ((Graphics2D) g).setStroke(new BasicStroke(v.getThickness()));
            g.drawRect(v.getPositionX(), v.getPositionY(), v.getWidth(), v.getWidth());
        }
			*/
        for(EdgeView e : graph.getEdges()){
            g.setColor(e.getColor());
            g.drawLine(e.getOrigin().getPositionX(), e.getOrigin().getPositionY(), e.getDestination().getPositionX(), e.getDestination().getPositionY());
        }
        ((Graphics2D) g).setStroke(oldStroke);
    }

    /**
     * Teste si à partir des données d'un événement souris ({@link java.awt.event.MouseEvent}) un vertex est situé au dessous.
     * @param mouseEvent Evénement souris
     * @return Le {@link data.Vertex} s'il existe, sinon null
     */
    public VertexView onVertex(MouseEvent mouseEvent){
        Rectangle rect = new Rectangle();

        for(VertexView v : this.vertexes) {
            rect.setBounds(v.getPositionX(), v.getPositionY(), v.getWidth(), v.getWidth());
            if(rect.contains(mouseEvent.getX(), mouseEvent.getY())){
                return v;
            }
        }
        return null;
    }
    
    public Shape getDefaultShape() { // pourquoi cela ne serait pas dans les Vertex plutôt ? Est-ce que tous les Vertex doivent avoir la même forme ?
        return defaultShape;
    }

    public void setDefaultShape(Shape defaultShape) {
        this.defaultShape = defaultShape;
    }

    public ArrayList<VertexView> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<VertexView> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<EdgeView> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<EdgeView> edges) {
        this.edges = edges;
    }

    public void selectedEdge(EdgeView e){
    	e.setColor(this.defaultSelectedColor);
    	e.setThickness(this.defaultSelectedThickness);
        this.selectedEdges.add(e);
    }

    public void unselectedEdge(EdgeView e){
    	e.setColor(this.defaultColor);
    	e.setThickness(this.defaultThickness);
        this.selectedEdges.remove(e);
    }

    public void selectVertex(VertexView v){
    	v.setColor(this.defaultSelectedColor);
    	v.setThickness(this.defaultSelectedThickness);
        this.selectedVertexes.add(v);
    }

    public void unselectedVertex(VertexView v){
    	v.setColor(this.defaultColor);
    	v.setThickness(this.defaultThickness);
        this.selectedVertexes.remove(v);
    }

    public void clearSelectedItem(){
    	for(VertexView v : this.selectedVertexes) {
    		v.setColor(this.defaultColor);
        	v.setThickness(this.defaultThickness);
    	}
    	for(EdgeView e : this.selectedEdges) {
    		e.setColor(this.defaultColor);
        	e.setThickness(this.defaultThickness);
    	}
    	this.selectedVertexes.clear();
        this.selectedEdges.clear();
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
    
    public int getDefaultWidth() {
        return this.defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    /**
     * Creates a vertex with default attributes
     * @param x
     * @param y
     */
    public void addVertex(Point position){
		position.x -= this.defaultWidth/2;
		position.y -= this.defaultWidth/2;
    	VertexView vertex = new VertexView(this.defaultColor, this.defaultThickness, this.defaultWidth, position.x, position.y, this.defaultShape);
        vertex.addMouseListener(new VertexMouseListener(this.controller, vertex));
        this.vertexes.add(vertex);
    }
    
    /**
     * Creates an edge between two vertexes
     * @param origin
     * @param destination
     */
    public void createEdge(VertexView origin, VertexView destination ){
    	EdgeView edge = new EdgeView(this.defaultThickness, this.defaultColor, origin, destination);
        this.edges.add(edge);
    }

    /**
     * Moves a vertex to x and y coordinates
     * @param vertex
     * @param x
     * @param y
     */
    public void moveVertex(VertexView vertex, int x, int y){
        vertex.setPositionX(x);
        vertex.setPositionY(y);
    }

    /**
     * Moves a vertex by a vector
     * @param vectorX
     * @param vectorY
     */
    public void moveSelectedVertex(int vectorX, int vectorY){
        for(VertexView vertex : this.selectedVertexes){
            vertex.move(vectorX,vectorY);
        }
    }

	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		
	}
}