package undoRedo;

import data.Vertex;
import view.editor.elements.ElementView;

import java.awt.*;

/**
 * Created by bendossantos on 18/03/15.
 */
public class SnapVertex extends SnapProperties {

    private Vertex.Shape shape;
    private Point position;

       public SnapVertex()
    {
        super();
        shape=null;
        position=null;

    }

    public SnapVertex(Vertex v, int index) {
        setIndex(index);
        setShape(v.getShape());
        setSize(v.getSize());
        setColor(v.getColor());
        setPosition(v.getPosition());
        setLabel(v.getLabel());
    }

    public Vertex.Shape getShape() {
        return shape;
    }

    public void setShape(Vertex.Shape shape) {
        this.shape = shape;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public boolean isSnapVertex() {
        return true;
    }
}
