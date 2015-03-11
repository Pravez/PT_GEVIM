package files.dot.elements;

/**
 * Created by paubreton on 08/03/15.
 * Classe plus spécialisée d'un {@link files.dot.elements.DotElement} contenant deux {@link files.dot.elements.VertexDot}
 */
public class EdgeDot extends DotElement{

    private VertexDot origin;
    private VertexDot destination;

    public EdgeDot(){
        super();
    }

    /**
     * Getter du {@link files.dot.elements.VertexDot} à l'origine
     * @return
     */
    public VertexDot getOrigin() {
        return origin;
    }

    /**
     * Setter du {@link files.dot.elements.VertexDot} à l'origine
     * @param origin
     */
    public void setOrigin(VertexDot origin) {
        this.origin = origin;
    }

    /**
     * Getter du {@link files.dot.elements.VertexDot} à destination
     * @return
     */
    public VertexDot getDestination() {
        return destination;
    }

    /**
     * Setter du {@link files.dot.elements.VertexDot} à destination
     * @param destination
     */
    public void setDestination(VertexDot destination) {
        this.destination = destination;
    }


}
