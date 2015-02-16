package view;

import data.Edge;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

/**
 * Created by cordavidenko on 26/01/15.
 * Classe EdgeView, Edge affiché dans le Tab
 */
public class EdgeView extends JComponent {

	private static final long serialVersionUID = 1L;
	private Edge       edge;
    private VertexView origin;
    private VertexView destination;
    private int        thickness;
    private int        hoverThickness;
    private Color      color;
    private Color      hoverColor;

    //rajouter des statics pour les paramètres par défaut

    /**
     * Constructeur de la classe EdgeView
     * @param edge le modèle Edge qui est affiché
     * @param hoverThickness l'épaisseur du trait lorsque l'EdgeView est sélectionné
     * @param hoverColor la couleur de l'EdgeView lorsque l'EdgeView est sélectionné
     * @param origin le VertexView d'origine
     * @param destination le VertexView de destination
     */
    public EdgeView(Edge edge, int hoverThickness, Color hoverColor, VertexView origin, VertexView destination) {
    	this.edge           = edge;
    	this.thickness      = edge.getThickness();
        this.hoverThickness = hoverThickness;
        this.color          = edge.getColor();
        this.hoverColor     = hoverColor;
        this.origin         = origin;
        this.destination    = destination;
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = 4;
        int length = (int) sqrt((this.origin.getPosition().x - this.destination.getPosition().x) * (this.origin.getPosition().x - this.destination.getPosition().x) +
                          (this.origin.getPosition().y - this.destination.getPosition().y) * (this.origin.getPosition().y - this.destination.getPosition().y));

        double angle = acos((this.destination.getPosition().x - this.origin.getPosition().x)/length);

        int vector_y = (int) (cos(angle) * radius);
        int vector_x = (int) sqrt(radius*radius - vector_y*vector_y);
        vector_y = vector_y < 0 ? -vector_y : vector_y;
        vector_x = vector_x < 0 ? -vector_x : vector_x;

        Point p1 = new Point(this.origin.getPosition().x + vector_x, this.origin.getPosition().y + vector_y);
        Point p2 = new Point(this.origin.getPosition().x - vector_x, this.origin.getPosition().y - vector_y);
        Point p3 = new Point(this.destination.getPosition().x - vector_x, this.destination.getPosition().y - vector_y);
        Point p4 = new Point(this.destination.getPosition().x + vector_x, this.destination.getPosition().y + vector_y);
        return new Polygon(new int[] { p1.x, p2.x, p3.x, p4.x }, new int[] { p1.y, p2.y, p3.y, p4.y }, 4).contains(x, y);
    }
    
    /**
     * Override de la méthode paintComponent pour dessiner l'EdgeView dans le Tab
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
    	g.setFont(super.getFont());
    	Stroke oldStroke = ((Graphics2D) g).getStroke();
		
		Graphics2D        g2d         = ((Graphics2D) g);
		RenderingHints    renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(renderHints);
        /** **/
        g.setColor(Color.RED);
        int radius = 10;
        int length = (int) sqrt((this.origin.getPosition().x - this.destination.getPosition().x) * (this.origin.getPosition().x - this.destination.getPosition().x) +
                (this.origin.getPosition().y - this.destination.getPosition().y) * (this.origin.getPosition().y - this.destination.getPosition().y));

        double angle = acos((this.destination.getPosition().x - this.origin.getPosition().x)/length);

        int vector_y = (int) (cos(angle) * radius);
        int vector_x = (int) sqrt(radius*radius - vector_y*vector_y);

        vector_y = vector_y < 0 ? -vector_y : vector_y;
        vector_x = vector_x < 0 ? -vector_x : vector_x;

        Point p1 = new Point(this.origin.getPosition().x + vector_x, this.origin.getPosition().y + vector_y);
        Point p2 = new Point(this.origin.getPosition().x - vector_x, this.origin.getPosition().y - vector_y);
        Point p3 = new Point(this.destination.getPosition().x - vector_x, this.destination.getPosition().y - vector_y);
        Point p4 = new Point(this.destination.getPosition().x + vector_x, this.destination.getPosition().y + vector_y);
        ((Graphics2D) g).fill(new Polygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4));
        /** **/
		g.setColor(this.color);
		((Graphics2D) g).setStroke(new BasicStroke(this.thickness));
		g.drawLine(this.origin.getPosition().x, this.origin.getPosition().y, this.destination.getPosition().x, this.destination.getPosition().y);
		((Graphics2D) g).setStroke(oldStroke);
    }
    
    /**
     * Getter de l'épaisseur de l'EdgeView
     * @return l'épaisseur de l'EdgeView
     */
    public int getThickness() {
        return this.thickness;
    }

    /**
     * Setter de l'épaisseur de l'EdgeView
     * @param thickness la nouvelle épaisseur
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
    
    /**
     * Getter de l'épaisseur de l'EdgeView lorsque l'EdgeView est sélectionné
     * @return l'épaisseur lorsque l'EdgeView est sélectionné
     */
    public int getHoverThickness() {
        return this.thickness;
    }

    /**
     * Setter de l'épaisseur de l'EdgeView lorsque l'EdgeView est sélectionné
     * @param thickness la nouvelle épaisseur lorsque l'EdgeView est sélectionné
     */
    public void setHoverThickness(int thickness) {
        this.hoverThickness = thickness;
    }

    /**
     * Getter de la couleur de l'EdgeView
     * @return la couleur de l'EdgeView
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter de la couleur de l'EdgeView
     * @param color la nouvelle couleur de l'EdgeView
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Getter de la couleur de l'EdgeView lorsque l'EdgeView est sélectionné
     * @return la couleur de l'EdgeView lorsque l'EdgeView est sélectionné
     */
    public Color getHoverColor() {
        return this.hoverColor;
    }

    /**
     * Setter de la couleur de l'EdgeView lorsque l'EdgeView est sélectionné
     * @param color la nouvelle couleur de l'EdgeView lorsque l'EdgeView est sélectionné
     */
    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    /**
     * Getter du VertexView d'origine de l'EdgeView
     * @return le VertexView d'origine
     */
    public VertexView getOrigin() {
        return this.origin;
    }

    /**
     * Setter du VertexView d'origine de l'EdgeView
     * @param origin le nouveau VertexView d'origine
     */
    public void setOrigin(VertexView origin) {
        this.origin = origin;
    }

    /**
     * Getter du VertexView de destination de l'EdgeView
     * @return le VertexView de destination
     */
    public VertexView getDestination() {
        return this.destination;
    }

    /**
     * Setter du VertexView de destination de l'EdgeView
     * @param destination le nouveau VertexView de destination
     */
    public void setDestination(VertexView destination) {
        this.destination = destination;
    }
    
    /**
     * Méthode appelée pour mettre à jour les paramètres d'affichage de l'EdgeView s'il est sélectionné ou non
     * @param isHover boolean si l'EdgeView est sélectionné ou non
     */
    public void updateHover(boolean isHover) {
		this.color     = (isHover) ? this.hoverColor     : this.edge.getColor();
		this.thickness = (isHover) ? this.hoverThickness : this.edge.getThickness();
		this.repaint();
	}
}