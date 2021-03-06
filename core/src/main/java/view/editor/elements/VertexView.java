package view.editor.elements;

import data.Graph;
import data.GraphElement;
import data.Vertex;
import undoRedo.snap.SnapProperties;
import undoRedo.snap.SnapVertex;
import view.UIElements.CustomUIManager;
import view.frames.VertexViewEditor;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Classe étant la "représentation graphique" d'un {@link data.Vertex}. Elle récupère ses données et les rend visibles.
 */
public class VertexView extends ElementView {

    /* Le Vertex qui est affiché */
	private Vertex         vertex;
	private Graphics2D     g2d;
    /* Le décalage entre le VertexView et son label */
	private int            labelGap;

    /**
     * Constructeur de la classe VertexView
     * @param vertex le modèle Vertex à afficher
     */
    public VertexView(Vertex vertex) {
		super(vertex.getColor());
    	this.vertex   = vertex;
		this.labelGap = 5;
    }

    /**
     * Constructeur par copie de la classe VertexView
     * @param vertexView le VertexView à copier
     */
	public VertexView(VertexView vertexView) {
		super(vertexView);
		this.vertex   = new Vertex(vertexView.vertex);
		this.labelGap = 5;
	}

    /**
     * Override de la méthode contains pour savoir si le point est dans le VertexView ou non
     * @param x l'abscisse du point
     * @param y l'absicce du point
     * @return le résultat sous la forme d'un booléen
     */
    @Override
    public boolean contains(int x, int y) {
        int size = (int) (this.vertex.getSize()*this.scale.x);
        int posx = (int) ((this.vertex.getPosition().x - this.vertex.getSize()/2)*this.scale.x);
        int posy = (int) ((this.vertex.getPosition().y - this.vertex.getSize()/2)*this.scale.y);
        return new Rectangle(posx, posy, size, size).contains(x, y);
    }

    /**
     * Méthode paintComponent permettant de dessiner le VertexView dans le Tab selon un zoom
     * @param g le Graphique
     * @param scaleX l'échelle du zoom en X
     * @param scaleY l'échelle du zoom en Y
     * @param paintLabel si on doit afficher ou non le label
     */
    public void paintComponent(Graphics g, double scaleX, double scaleY, boolean paintLabel) {
        this.scale = new Point2D.Double(scaleX, scaleY);
		this.g2d   = (Graphics2D)g;
    	g.setFont(super.getFont());
		
		Graphics2D     g2d         = ((Graphics2D) g);
		RenderingHints renderHints = new RenderingHints (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHints(renderHints);
		g.setColor(this.color);
		
		int size = (int) (this.vertex.getSize() * scaleX);
		int x    = (int) (this.vertex.getPosition().x* scaleX - size/2);
		int y    = (int) (this.vertex.getPosition().y* scaleY - size/2);

        /** Dessin de la forme en fonction de la forme du Vertex **/
        switch (this.vertex.getShape()) {
		case SQUARE :
			g.fillRect(x, y, size, size);
			break;
		case CIRCLE :
			g.fillOval(x, y, size, size);
			break;
		case TRIANGLE :
			g.fillPolygon(new Polygon(new int[] {x + size/2, x + size, x}, new int[] {y, y + size, y + size}, 3));
			break;
		case CROSS :
			Stroke oldStroke = ((Graphics2D) g).getStroke();
			int thickness = size/3;
			((Graphics2D) g).setStroke(new BasicStroke(thickness));
			g.drawLine(x, y, x + size, y + size);
			g.drawLine(x, y + size, x + size, y);
			((Graphics2D) g).setStroke(oldStroke);
			break;
		default:
			break;
		}

		if (paintLabel) { /** Affichage du label du VertexView **/
			FontMetrics fontMetrics = g2d.getFontMetrics();
			int width = fontMetrics.stringWidth(this.vertex.getLabel());
			g.setColor(Color.BLACK);
			g.drawString(this.vertex.getLabel(), x + size/2 - width/2, y - this.labelGap);
		}
    }

	/**
	 * Méthode pour récupérer le rectangle de la zone du Vertex avec le label
	 * @return le rectangle de la zone du Vertex avec le label
	 */
	public Rectangle getVertexBounds() {
		FontMetrics fontMetrics = this.g2d.getFontMetrics();
		int size   = (int)(this.vertex.getSize() * this.scale.x);
        int x      = (int) (this.vertex.getPosition().x * this.scale.x - size/2);
        int y      = (int) (this.vertex.getPosition().y * this.scale.y);
		int width  = fontMetrics.stringWidth(this.vertex.getLabel());
		int height = fontMetrics.getHeight();
		return new Rectangle(x - width/2, y - this.labelGap - height, width, height + this.labelGap + size);
	}
    
    /**
     * Setter de la position du VertexView
     * @param position la nouvelle position
     */
    public void setPosition(Point position) {
    	this.vertex.setPosition(position);
    }
       
    /**
     * Getter de la position du VertexView
     * @return la position du VertexView
     */
    public Point getPosition() {
		return this.vertex.getPosition();
    }

	/**
	 * Getter de la position effective du VertexView en fonction du zoom
	 * @return la position effective à l'écran du Vertex
	 */
	public Point getScaledPosition() {
		return new Point((int)(this.vertex.getPosition().x*this.scale.getX()), (int)(this.vertex.getPosition().y*this.scale.getY()));
	}
    
    /**
     * Méthode appelée pour mettre à jour les paramètres d'affichage du VertexView s'il est sélectionné ou non
     * @param isHover boolean si le VertexView est sélectionné ou non
     */
    @Override
    public void updateHover(boolean isHover) {
        this.color = (isHover) ? CustomUIManager.getHoverColor() : this.vertex.getColor();
	}

    /**
     * Méthode appellée pour invoquer un JDialog permettant de modifier les informations d'un VertexView dans le détail.
     */
    @Override
    public SnapProperties modify(Graph graph){
		SnapVertex       snap = null;
        VertexViewEditor edit = new VertexViewEditor(this.vertex, this);

        if(!edit.isNotModified()) {
			snap=new SnapVertex();
            this.vertex.setSize(edit.getNewWidth());
            this.vertex.setPosition(edit.getNewPosition());
            this.vertex.setLabel(edit.getNewLabel());
            this.vertex.setShape(edit.getNewShape());
            this.vertex.setColor(edit.getNewColor());
            this.vertex.setValue(edit.getNewIndex());
            if (edit.isWidthModified())
                snap.setSize(edit.getNewWidth());
            if (edit.isPositionModified())
                snap.setPosition(edit.getNewPosition());
            if (edit.isLabelModified())
                snap.setLabel(edit.getNewLabel());
            if (edit.isShapeModified())
                snap.setShape(edit.getNewShape());
            if (edit.isColorModified())
                snap.setColor(edit.getNewColor());
			if(edit.isIndexModified())
				snap.setValue(edit.getNewIndex());
        }

        return snap;
    }

    /**
     * Getter permettant de récupérer le {@link data.Vertex} associé au VertexView
     * @return le Vertex associé au VertexView
     */
    public Vertex getVertex() {
        return vertex;
    }

    /**
     * Méthode renvoyant le {@link data.Vertex} mais au format d'un {@link data.GraphElement}
     * @return le GraphElement associé au VertexView
     */
	@Override
	public GraphElement getGraphElement() {
		return this.vertex;
	}

    /**
     * Méthode changeant la position ({@link java.awt.Point}) d'un {@link data.Vertex}.
     * @param vector Le nouveau {@link java.awt.Point} où se situera le {@link data.Vertex}
     */
	public void move(Point vector) {
		this.vertex.setPosition(new Point(this.vertex.getPosition().x + vector.x, this.vertex.getPosition().y + vector.y));		
	}

    /**
     * Override de la méthode isVertexView permettant de savoir si l'ElementView est un VertexView ou non
     * @return vrai, on a affaire à un VertexView
     */
	@Override
	public boolean isVertexView() {
		return true;
	}
}
