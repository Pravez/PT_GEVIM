package data;

/**
 * Created by cordavidenko on 26/01/15.
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

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Vertex getOrigin() {
        return this.origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getDestination() {
        return this.destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
