package undoRedo;

import data.Edge;
import data.Vertex;

/**
 * Created by bendossantos on 18/03/15.
 */
public class SnapEdge extends SnapProperties {

  private Vertex source;
  private Vertex destination;

    public SnapEdge()
    {
        super();
        source =null;
        destination =null;
    }

    public SnapEdge(Edge e, int index) {

        setIndex(index);
        setSize(e.getThickness());
        setColor(e.getColor());
        setLabel(e.getLabel());
        setDestination(e.getDestination());
        setSource(e.getOrigin());

    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

}
