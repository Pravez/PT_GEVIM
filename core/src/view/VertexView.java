package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JComponent;

public class VertexView extends JComponent {

	private static final long serialVersionUID = 1L;
	private String          name;
    private java.awt.Color  color;
    private int             thickness;
    private int             width;
    private int             positionX;
    private int             positionY;
    private java.awt.Shape  shape;
    private ArrayList<EdgeView> edges; // à virer plus tard

    //rajouter des statics pour les paramètres par défaut

    /**
     * VertexView constructor
     * @param name
     * @param color
     * @param thickness
     * @param width
     * @param positionX
     * @param positionY
     * @param shape
     */
    public VertexView(String name, Color color, int thickness, int width, int positionX, int positionY, Shape shape) {
        this.name      = name;
        this.color     = color;
        this.thickness = thickness;
        this.width     = width;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape     = shape;
        this.edges     = new ArrayList<EdgeView>();
    }

    /**
     * VertexView constructor
     * @param color
     * @param thickness
     * @param width
     * @param positionX
     * @param positionY
     * @param shape
     */
    public VertexView(Color color, int thickness, int width, int positionX, int positionY, Shape shape) {
        this.color     = color;
        this.thickness = thickness;
        this.width     = width;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape     = shape;
        this.edges     = new ArrayList<EdgeView>();
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	g.setFont(super.getFont());
		
		Graphics2D        g2d         = ((Graphics2D) g);
		RenderingHints    renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(renderHints);
		g.setColor(this.color);
		g.drawRect(this.positionX, this.positionY, this.width, this.width);
    }
    
    public void addEdge(EdgeView edge){
        this.edges.add(edge);
    }

    public void removeEdge(EdgeView edge){
        this.edges.remove(edge);
    }

    public ArrayList<EdgeView> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<EdgeView> edges) {
        this.edges = edges;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getThickness() {
        return this.thickness;
    }
    
    public int getWidth() {
        return this.width;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public Shape getShape() {
        return this.shape;
    }

    /**
     * move the Vertex in function of the given vector value
     * @param vectorX
     * @param vectorY
     */
    public void move(int vectorX, int vectorY) {
        this.positionX += vectorX;
        this.positionY += vectorY;
    }
}
