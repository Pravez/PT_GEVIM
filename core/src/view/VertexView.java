package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JComponent;

/**
 * @author Alexis Dufrenne
 * Classe VertexView, Vertex affiché dans le Tab
 */
public class VertexView extends JComponent {

	private static final long serialVersionUID = 1L;
	private String         name;
    private java.awt.Color color;
    private java.awt.Color defaultColor;
    private java.awt.Color hoverColor; 
    private int            width;
    private Point          position;
    private Shape          shape;

    public static enum     Shape { SQUARE, CIRCLE, TRIANGLE, CROSS };

    //rajouter des statics pour les paramètres par défaut

    /**
     * Constructeur de la classe VertexView
     * @param name nom du VertexView
     * @param color couleur du VertexView
     * @param hoverColor couleur du VertexView lorsqu'il est sélectionné
     * @param width largeur du VertexView
     * @param position position du VertexView
     * @param shape forme du VertexView
     */
    public VertexView(String name, Color color, Color hoverColor, int width, Point position, Shape shape) {
    	this(color, hoverColor, width, position, shape);
        this.name = name;
    }

    /**
     * Constructeur de la classe VertexView
     * @param color couleur du VertexView
     * @param hoverColor couleur du VertexView lorsqu'il est sélectionné
     * @param width largeur du VertexView
     * @param position position du VertexView
     * @param shape forme du VertexView
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
    
    /**
     * Override de la méthode paintComponent pour dessiner le VertexView dans le Tab
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
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
		case TRIANGLE :
			int center = this.position.x + this.width/2;
			int right  = this.position.x + this.width;
			int bottom = this.position.y + this.width;
			g.fillPolygon(new Polygon(new int[] {center, right, this.position.x}, new int[] {this.position.y, bottom, bottom}, 3));
			break;
		case CROSS :
			Stroke oldStroke = ((Graphics2D) g).getStroke();
			int thickness = this.width/3;
			((Graphics2D) g).setStroke(new BasicStroke(thickness));
			g.drawLine(this.position.x, this.position.y, this.position.x + this.width, this.position.y + this.width);
			g.drawLine(this.position.x, this.position.y + this.width, this.position.x + this.width, this.position.y);
			((Graphics2D) g).setStroke(oldStroke);
			break;
		default:
			break;
		}
    }
    
    /**
     * Setter de la forme du VertexView
     * @param shape nouvelle forme du VertexView
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Setter du nom du VertexView
     * @param name nouveau nom du VertexView
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter de la couleur du VertexView
     * @param color nouvelle couleur du VertexView
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Setter de la couleur du VertexView lorsqu'il est sélectionné
     * @param color nouvelle couleur du VertexView lorsqu'il est sélectionné
     */
    public void setHoverColor(Color color) {
    	this.hoverColor = color;
    }
    
    /**
     * Setter de la largeur du VertexView
     * @param width nouvelle largeur du VertexView
     */
    public void setWidth(int width) {
        this.width = width;
    }
   
    /**
     * Setter de la position du VertexView
     * @param position nouvelle position
     */
    public void setPosition(Point position) {
    	this.position = position;
    }

    /**
     * Getter du nom du VertexView
     * @return le nom du VertexView
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter de la couleur du VertexView
     * @return la couleur du VertexView
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Getter de la couleur du VertexView lorsqu'il est sélectionné
     * @return la couleur du VertexView lorsqu'il est sélectionné
     */
    public Color getHoverColor() {
        return this.hoverColor;
    }
    
    /**
     * Getter de la largeur du VertexView
     * @return la largeur du VertexView
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter de la position du VertexView
     * @return la position du VertexView
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Getter de la forme du VertexView
     * @return la forme du VertexView
     */
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Méthode permettant de déplacer le VertexView dans une certaine direction
     * @param vectorX la direction du vecteur de déplacement en abscisse
     * @param vectorY la direction du vecteur de déplacement en ordonnée
     */
    public void move2(int vectorX, int vectorY) {
    	System.out.println("Vertex moved");
        this.position.x += vectorX;
        this.position.y += vectorY;
    }
    
    /**
     * Méthode appelée pour mettre à jour les paramètres d'affichage du VertexView s'il est sélectionné ou non
     * @param isHover boolean si le VertexView est sélectionné ou non
     */
    public void updateHover(boolean isHover) {
		this.color = (isHover) ? this.hoverColor : this.defaultColor;
		this.repaint();
	}

    /**
     * Méthode appellée pour invoquer un JDialog permettant de modifier les informations d'un VertexView dans le détail.
     */
    public void modifyVertexView(){
        VertexViewEditor edit = new VertexViewEditor(this);

        VertexView newVertex = edit.getModifiedVertex();

        this.width = newVertex.getWidth();
        this.position = newVertex.getPosition();
        this.name = newVertex.getName();

        this.repaint();
        //PROBLEME : N'EST ENREGISTRE QUE TEMPORAIREMENT JUSQU'AU PROCHAIN update()
        //ET N'EST PAS ACTUALISE COMME IL LE FAUDRAIT.
    }
}
