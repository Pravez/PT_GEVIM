package undoRedo;

import data.Vertex;

/**
 * Created by bendossantos on 18/03/15.
 */
public class SnapVertex extends SnapProperties {

    private Vertex.Shape shape;

    public Vertex.Shape getShape() {
        return shape;
    }

    public void setShape(Vertex.Shape shape) {
        this.shape = shape;
    }

    public SnapVertex()
    {
        super();
        shape=null;
    }

}
