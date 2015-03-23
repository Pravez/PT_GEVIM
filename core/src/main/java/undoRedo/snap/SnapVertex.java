package undoRedo.snap;

import data.Vertex;

import java.awt.*;

/**
 *Classe SnapVertex, enregistre en plus des propriétés communes aux {@link data.GraphElement} les propriétés propres aux {@link data.Vertex}
 */
public class SnapVertex extends SnapProperties {

    private Vertex.Shape shape;//Forme du Vertex associé au SnapVertex
    private Point position;//Position du Vertex associé au SnapVertex

    /**
     * Constructeur par défaut de la classe SnapVertex. Affecte des valeurs par défaut permettant par la suite de détecter les modifications à prendre en compte
     */
       public SnapVertex()
    {
        super();
        shape=null;
        position=null;

    }

    /**
     * Constructeur de la classe SnapVertex
     * @param v Vertex dont les propriétés seront sauvegardées dans le SnapVertex
     * @param index  position du Vertex au sein de la liste des Vertices du Graph concerné
     */
    public SnapVertex(Vertex v, int index) {
        setIndex(index);
        setShape(v.getShape());
        setSize(v.getSize());
        setColor(v.getColor());
        setPosition(v.getPosition());
        setLabel(v.getLabel());
        setValue(v.getValue());
    }

    /**
     * Getter de la forme du Vertex associé au SnapVertex
     * @return la forme du Vertex
     */
    public Vertex.Shape getShape() {
        return shape;
    }

    /**
     * Setter de la forme du Vertex associé au SnapVertex
     * @param shape la nouvelle forme du SnapVertex
     */
    public void setShape(Vertex.Shape shape) {
        this.shape = shape;
    }

    /**
     * Getter de la position du Vertex associé au SnapVertex
     * @return la position du Vertex
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Setter de la position du Vertex associé au SnapVertex
     * @param position la nouvelle position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Permet de se distinguer des {@link undoRedo.snap.SnapEdge}
     * @return true
     */
    @Override
    public boolean isSnapVertex() {
        return true;
    }
}
