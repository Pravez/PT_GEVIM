package data;

/**
 * Created by cordavidenko on 26/01/15.
 * Classe Edge, arête entre deux Vertex, partie du modèle de l'application
 */
public class Edge {
    private String         label;
    private Vertex         origin;
    private Vertex         destination;
    
    /**
     * Edge Constructor
     * @param label
     * @param origin
     * @param destination
     */
    public Edge(String label, Vertex origin, Vertex destination) {
        this.label       = label;
        this.origin      = origin;
        this.destination = destination;
    }

    /**
     * Edge Constructor without label
     * @param origin
     * @param destination
     */
    public Edge(Vertex origin, Vertex destination) {
        this.origin      = origin;
        this.destination = destination;
    }

    /**
     * Getter du label de l'Edge
     * @return le label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Setter du label de l'Edge
     * @param label le nouveau label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter du Vertex d'origine de l'Edge
     * @return le Vertex d'origine
     */
    public Vertex getOrigin() {
        return this.origin;
    }

    /**
     * Setter du Vertex d'origine de l'Edge
     * @param origin le nouveau Vertex d'origine
     */
    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    /**
     * Getter du Vertex de destination de l'Edge
     * @return le Vertex de destination
     */
    public Vertex getDestination() {
        return this.destination;
    }

    /**
     * Setter du Vertex de destination de l'Edge
     * @param destination le nouveau Vertex de destination
     */
    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
