package view;

import java.awt.*;

import javax.swing.JComponent;

/**
 * Created by cordavidenko on 26/01/15.
 */

public class EdgeView extends JComponent {

	private static final long serialVersionUID = 1L;
	private String         label;
    private VertexView     origin;
    private VertexView     destination;
    private int            thickness;
    private int            defaultThickness;
    private int            hoverThickness;
    private java.awt.Color color;
    private java.awt.Color defaultColor;
    private java.awt.Color hoverColor;

    //rajouter des statics pour les paramètres par défaut

    /**
     * Edge Constructor
     * @param thickness
     * @param color
     * @param label
     * @param origin
     * @param destination
     */
    public EdgeView(String label, int thickness, int hoverThickness, Color color, Color hoverColor, VertexView origin, VertexView destination) {
        this(thickness, hoverThickness, color, hoverColor, origin, destination);
        this.label       = label;
    }

    /**
     * Edge Constructor without label
     * @param thickness
     * @param color
     * @param origin
     * @param destination
     */
    public EdgeView(int thickness, int hoverThickness, Color color, Color hoverColor, VertexView origin, VertexView destination) {
        this.thickness        = thickness;
        this.defaultThickness = thickness;
        this.hoverThickness   = hoverThickness;
        this.color            = color;
        this.defaultColor     = color;
        this.hoverColor       = hoverColor;
        this.origin           = origin;
        this.destination      = destination;
        /** Set bounds et setSize pour les Mouse Listeners **/
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	g.setFont(super.getFont());
    	Stroke oldStroke = ((Graphics2D) g).getStroke();
		
		Graphics2D        g2d         = ((Graphics2D) g);
		RenderingHints    renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(renderHints);
		g.setColor(this.color);
		((Graphics2D) g).setStroke(new BasicStroke(this.thickness));
		g.drawLine(this.origin.getPosition().x, this.origin.getPosition().y, this.destination.getPosition().x, this.destination.getPosition().y);
		((Graphics2D) g).setStroke(oldStroke);
    }
    
    public int getThickness() {
        return this.thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
    
    public int getHoverThickness() {
        return this.thickness;
    }

    public void setHoverThickness(int thickness) {
        this.hoverThickness = thickness;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getHoverColor() {
        return this.hoverColor;
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public VertexView getOrigin() {
        return this.origin;
    }

    public void setOrigin(VertexView origin) {
        this.origin = origin;
    }

    public VertexView getDestination() {
        return this.destination;
    }

    public void setDestination(VertexView destination) {
        this.destination = destination;
    }
    
    public void updateHover(boolean isHover) {
		this.color     = (isHover) ? this.hoverColor     : this.defaultColor;
		this.thickness = (isHover) ? this.hoverThickness : this.defaultThickness;
		this.repaint();
	}
}
