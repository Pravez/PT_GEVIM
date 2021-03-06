package view.editor.elements;

import data.Edge;
import data.Graph;
import data.GraphElement;
import undoRedo.snap.SnapEdge;
import undoRedo.snap.SnapProperties;
import view.UIElements.CustomUIManager;
import view.frames.EdgeViewEditor;

import java.awt.*;
import java.awt.geom.Point2D;

import static java.lang.Math.sqrt;

/**
 * Classe EdgeView, Edge affiché dans le Tab
 */
public class EdgeView extends ElementView {

	private static final long serialVersionUID = 1L;
    /* L'Edge associé à l'EdgeView */
	private Edge       edge;
    /* Le VertexView d'origine de l'EdgeView */
    private VertexView origin;
    /* Le VertexView de destination de l'EdgeView */
    private VertexView destination;
    /* L'épaisseur de l'EdgeView */
    private int        thickness;
    /* L'épaisseur en plus quand l'EdgeView est sélectionnée*/
    private int        hoverThickness;

    /**
     * Constructeur de la classe EdgeView
     * @param edge le modèle Edge qui est affiché
     * @param hoverThickness l'épaisseur du trait lorsque l'EdgeView est sélectionné
     * @param origin le VertexView d'origine
     * @param destination le VertexView de destination
     */
    public EdgeView(Edge edge, int hoverThickness, VertexView origin, VertexView destination) {
        super(edge.getColor());
    	this.edge           = edge;
    	this.thickness      = edge.getThickness();
        this.hoverThickness = hoverThickness;
        this.origin         = origin;
        this.destination    = destination;
    }

    /**
     * Override de la fonction contains pour savoir si le point est contenu par l'EdgeView
     * Est utilisé par le MouseListener pour savoir si l'on clique sur l'EdgeView
     * (non-Javadoc)
     * @see javax.swing.JComponent#contains(int, int)
     */
    @Override
    public boolean contains(int x, int y) {
    	// La largeur de la zone autour de l'EdgeView
    	int radius = this.edge.getThickness() > 10 ? this.edge.getThickness() : 10;
    	// la distance entre le point d'origine et le point de destination de l'EdgeView
        int length = (int) sqrt((this.destination.getPosition().x - this.origin.getPosition().x) * (this.destination.getPosition().x - this.origin.getPosition().x) +
                          (this.destination.getPosition().y - this.origin.getPosition().y) * (this.destination.getPosition().y - this.origin.getPosition().y));
        // la distance en abscisse entre les points d'origine et de destination
        int length_x = this.destination.getPosition().x - this.origin.getPosition().x;
        // le déplacement vers le haut pour le premier point de la zone
        int vector_y = (int) (1.0 * length_x * radius / length);
        // le déplacement vers la droite pour le premier point de la zone
        int vector_x = (int) sqrt(radius*radius - vector_y*vector_y);
        
        if (this.destination.getPosition().y > this.origin.getPosition().y) { // si le point de destination est plus bas
        	vector_x = -vector_x; // on inverse le vecteur en abscisse
        }

        // on instancie les différents points de la zone autour de l'EdgeView
        Point p1 = new Point((int) ((this.origin.getPosition().x + vector_x)*this.scale.x), (int) ((this.origin.getPosition().y + vector_y)*this.scale.y));
        Point p2 = new Point((int) ((this.destination.getPosition().x + vector_x)*this.scale.x), (int) ((this.destination.getPosition().y + vector_y)*this.scale.y));
        Point p3 = new Point((int) ((this.destination.getPosition().x - vector_x)*this.scale.x), (int) ((this.destination.getPosition().y - vector_y)*this.scale.y));
        Point p4 = new Point((int) ((this.origin.getPosition().x - vector_x)*this.scale.x), (int) ((this.origin.getPosition().y - vector_y)*this.scale.y));
        return new Polygon(new int[] { p1.x, p2.x, p3.x, p4.x }, new int[] { p1.y, p2.y, p3.y, p4.y }, 4).contains(x, y);
    }

    /**
     *  Méthode paintComponent pour dessiner l'EdgeView dans le Tab selon un zoom
     * @param g le Graphique
     * @param scaleX l'échelle du zoom en X
     * @param scaleY l'échelle du zoom en Y
     * @param paintLabel si on doit afficher ou non le label
     */
    public void paintComponent(Graphics g, double scaleX, double scaleY, boolean paintLabel) {
        this.scale = new Point2D.Double(scaleX, scaleY);
    	g.setFont(super.getFont());
    	Stroke oldStroke = ((Graphics2D) g).getStroke();
		
		Graphics2D        g2d         = ((Graphics2D) g);
		RenderingHints    renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(renderHints);
		g.setColor(this.color);
		((Graphics2D) g).setStroke(new BasicStroke((float) (this.thickness * scaleX)));
		Point source      = new Point((int)((this.origin.getPosition().x)*scaleX), (int)((this.origin.getPosition().y)*scaleY));
		Point destination = new Point((int)((this.destination.getPosition().x)*scaleX), (int)((this.destination.getPosition().y)*scaleY));
		g.drawLine(source.x, source.y, destination.x, destination.y);
		((Graphics2D) g).setStroke(oldStroke);

        Point min = new Point(source.x > destination.x ? destination.x : source.x, source.y > destination.y ? destination.y : source.y);
        Point max = new Point(source.x < destination.x ? destination.x : source.x, source.y < destination.y ? destination.y : source.y);

        if (paintLabel) { // Affichage du label du VertexView
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int width  = fontMetrics.stringWidth(this.edge.getLabel());
            g.setColor(Color.BLACK);
            g.drawString(this.edge.getLabel(), min.x + (max.x - min.x)/2 - width/2, min.y + (max.y - min.y)/2 - 5);
        }
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
     * Getter de l'Edge associée à l'EdgeView
     * @return l'Edge associée
     */
    public Edge getEdge() {
        return edge;
    }
    
    /**
     * Méthode appelée pour mettre à jour les paramètres d'affichage de l'EdgeView s'il est sélectionné ou non
     * @param isHover boolean si l'EdgeView est sélectionné ou non
     */
    @Override
    public void updateHover(boolean isHover) {
		this.color     = (isHover) ? CustomUIManager.getHoverColor() : this.edge.getColor();
		this.thickness = (isHover) ? this.thickness + this.hoverThickness : this.edge.getThickness();
	}

    /**
     * Méthode servant à modifier un {@link data.Edge} grâce au {@link view.frames.EdgeViewEditor}
     * @param graph Le {@link data.Graph} sur lequel se situe l'Edge qui est l'objet de la modification
     */
    @Override
    public SnapProperties modify(Graph graph) {

        EdgeViewEditor edit = new EdgeViewEditor(this.edge, graph, this);
        SnapEdge snap = null; 

        if(!edit.isNotModified()) {
                
            snap = new SnapEdge();
            this.edge.setThickness(edit.getThickness());
            this.edge.setLabel(edit.getLabel());
            this.edge.setColor(edit.getColor());
            this.edge.setOrigin(edit.getOrigin());
            this.edge.setDestination(edit.getDestination());
            if (edit.isWidthModified())
                snap.setSize(edit.getThickness());
            if (edit.isLabelModified())
                snap.setLabel(edit.getLabel());
            if (edit.isColorModified())
                snap.setColor(edit.getColor());
            if (edit.isDestinationModified())
                snap.setDestination(edit.getDestination());
            if (edit.isOriginModified())
                snap.setSource(edit.getOrigin());
        }

        return snap;
    }

    /**
     * Méthode pour récupérer le {@link data.GraphElement} ({@link data.Edge} ici) associé à l'élément de la vue {@link EdgeView}.
     * @return Le graphElement associé
     */
	@Override
	public GraphElement getGraphElement() {
		return this.edge;
	}

    /**
     * Override de la méthode permettant de savoir si c'est un VertexView ou non
     * @return faux on n'a pas affaire à un VertexView
     */
    @Override
    public boolean isVertexView() {
        return false;
    }
}