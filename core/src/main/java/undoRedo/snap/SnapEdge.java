package undoRedo.snap;

import data.Edge;
import data.Vertex;

/**
 *Classe SnapEdge, enregistre en plus des propriétés communes aux {@link data.GraphElement} les propriétés propres aux {@link data.Edge}
 */
public class SnapEdge extends SnapProperties {

  private Vertex source; //Vertex d'origine du Edge associé au Snap
  private Vertex destination; //Vertex de fin du Edge associé au Snap

    /**
     * Constructeur par défaut de la classe SnapEdge. Affecte des valeurs par défaut permettant par la suite de détecter les modifications à prendre en compte
     */
    public SnapEdge()
    {
        super();
        source =null;
        destination =null;
    }

    /**
     * Constructeur de la classe SnapEdge
     * @param e Edge dont les propriétés seront sauvegardées dans le SnapEdge
     * @param index position du Edge au sein de la liste des Edges du Graph concerné
     */
    public SnapEdge(Edge e, int index) {

        setIndex(index);
        setSize(e.getThickness());
        setColor(e.getColor());
        setLabel(e.getLabel());
        setDestination(e.getDestination());
        setSource(e.getOrigin());

    }

    /**
     * Getter du Vertex d'origine du Edge
     * @return le Vertex source
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Setter du Vertex d'origine du Edge
     * @param source le nouveau Vertex d'origine
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * Getter du Vertex de destination du Edge
     * @return le Vertex de destination
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * Setter du Vertex de destination du Edge
     * @param destination le nouveau vertex de destination
     */
    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

}
