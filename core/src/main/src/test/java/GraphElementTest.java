import data.Edge;
import data.GraphElement;
import data.Vertex;
import junit.framework.TestCase;

import java.awt.*;

/**
 * Created by Admin on 12/03/2015.
 */
public class GraphElementTest extends TestCase {

    private GraphElement element;

    /**
     * Test du constructeur de la classe GraphElement sans le paramètre label
     */
    public void testSimpleConstructor() {
        this.element = new Vertex(Color.GREEN, null, 0, Vertex.Shape.CROSS);

        assertEquals(this.element.getColor(), Color.GREEN);

        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.element = new Edge(Color.RED, v1, v2, 0);

        assertEquals(this.element.getColor(), Color.RED);
    }

    /**
     * Test du constructeur de la classe GraphElement avec le paramètre label
     */
    public void testComplexConstructor() {
        this.element = new Vertex("label", Color.GREEN, null, 0, Vertex.Shape.CROSS);

        assertEquals(this.element.getLabel(), "label");
        assertEquals(this.element.getColor(), Color.GREEN);

        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.element = new Edge("label2", Color.RED, v1, v2, 0);

        assertEquals(this.element.getLabel(), "label2");
        assertEquals(this.element.getColor(), Color.RED);
    }

    /**
     * Test du getter et setter du label d'un GraphElement
     */
    public void testLabel() {
        this.element = new Vertex("label", Color.GREEN, null, 0, Vertex.Shape.CROSS);

        assertEquals(this.element.getLabel(), "label");
        this.element.setLabel("new vertex");
        assertEquals(this.element.getLabel(), "new vertex");

        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.element = new Edge("label2", Color.RED, v1, v2, 0);

        assertEquals(this.element.getLabel(), "label2");
        this.element.setLabel("new edge");
        assertEquals(this.element.getLabel(), "new edge");
    }

    /**
     * Test du getter et setter de la couleur d'un GraphElement
     */
    public void testColor() {
        this.element = new Vertex("label", Color.GREEN, null, 0, Vertex.Shape.CROSS);

        assertEquals(this.element.getColor(), Color.GREEN);
        this.element.setColor(Color.BLUE);
        assertEquals(this.element.getColor(), Color.BLUE);

        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.element = new Edge("label2", Color.RED, v1, v2, 0);

        assertEquals(this.element.getColor(), Color.RED);
        this.element.setColor(Color.YELLOW);
        assertEquals(this.element.getColor(), Color.YELLOW);
    }
}
