package files.dot;

/**
 * Created by paubreton on 08/03/15.
 */
public class EdgeDot extends DotElement{

    private VertexDot origin;
    private VertexDot destination;

    public EdgeDot(){
        super();
    }

    public EdgeDot(VertexDot origin, VertexDot destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public VertexDot getOrigin() {
        return origin;
    }

    public void setOrigin(VertexDot origin) {
        this.origin = origin;
    }

    public VertexDot getDestination() {
        return destination;
    }

    public void setDestination(VertexDot destination) {
        this.destination = destination;
    }


}
