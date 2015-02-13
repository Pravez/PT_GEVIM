package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class VertexView extends JComponent {

	private static final long serialVersionUID = 1L;
	private String         name;
    private java.awt.Color color;
    private java.awt.Color defaultColor;
    private java.awt.Color hoverColor; 
    private int            width;
    private Point          position;
    private Shape          shape;
    public static enum     Shape { SQUARE, CIRCLE };

    //rajouter des statics pour les paramètres par défaut

    /**
     * VertexView constructor
     * @param name
     * @param color
     * @param hoverColor
     * @param width
     * @param position
     * @param shape
     */
    public VertexView(String name, Color color, Color hoverColor, int width, Point position, Shape shape) {
    	this(color, hoverColor, width, position, shape);
        this.name = name;
    }

    /**
     * VertexView constructor
     * @param color
     * @param hoverColor
     * @param width
     * @param position
     * @param shape
     */
    public VertexView(Color color, Color hoverColor, int width, Point position, Shape shape) {
    	this.defaultColor = color;
        this.color        = color;
        this.hoverColor   = hoverColor;
        this.width        = width;
        this.position     = position;
        this.shape        = shape;
        super.setSize(this.width, this.width);
        super.setBounds(this.position.x, this.position.y, this.width, this.width);
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	g.setFont(super.getFont());
		
		Graphics2D        g2d         = ((Graphics2D) g);
		RenderingHints    renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(renderHints);
		g.setColor(this.color);
		
		switch (this.shape) {
		case SQUARE :
			g.fillRect(this.position.x, this.position.y, this.width, this.width);
			break;
		case CIRCLE :
			g.fillOval(this.position.x, this.position.y, this.width, this.width);
			break;
		default:
			break;
		}
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
    
    public void setHoverColor(Color color) {
    	this.hoverColor = color;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
   
    public void setPosition(Point position) {
    	this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }
    
    public Color getHoverColor() {
        return this.hoverColor;
    }
    
    public int getWidth() {
        return this.width;
    }

    public Point getPosition() {
        return this.position;
    }

    public Shape getShape() {
        return this.shape;
    }

    /**
     * move the Vertex in function of the given vector value
     * @param vectorX
     * @param vectorY
     */
    public void move2(int vectorX, int vectorY) {
    	System.out.println("Vertex moved");
        this.position.x += vectorX;
        this.position.y += vectorY;
    }
    
    public void updateHover(boolean isHover) {
		this.color = (isHover) ? this.hoverColor : this.defaultColor;
		this.repaint();
	}
}
