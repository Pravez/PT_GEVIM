import data.Edge;
import data.Vertex;
import junit.framework.TestCase;

import java.awt.*;

/**
 * Created by Admin on 12/03/2015.
 */
public class EdgeTest extends TestCase {

    private Edge edge;

    /**
     * Test du Constructeur de la classe Edge sans le label en paramètre
     */
    public void testSimpleConstructor() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.edge = new Edge(Color.RED, v1, v2, 0);
        assertEquals(this.edge.getColor(), Color.RED);
        assertEquals(this.edge.getThickness(), 0);
        assertEquals(this.edge.getOrigin(), v1);
        assertEquals(this.edge.getDestination(), v2);
    }

    /**
     * Test du Constructeur de la classe Edge avec le label en paramètre
     */
    public void testComplexConstructor() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.edge = new Edge("label", Color.GRAY, v1, v2, 0);
        assertEquals(this.edge.getLabel(), "label");
        assertEquals(this.edge.getColor(), Color.GRAY);
        assertEquals(this.edge.getThickness(), 0);
        assertEquals(this.edge.getOrigin(), v1);
        assertEquals(this.edge.getDestination(), v2);
    }

    /**
     * Test du Constructeur de la classe Edge par copie
     */
    public void testCopyConstructor() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        Edge new_edge = new Edge("test", Color.GREEN, v1, v2, 0);
        this.edge = new Edge(new_edge);
        assertEquals(this.edge.getLabel(), new_edge.getLabel());
        assertEquals(this.edge.getColor(), new_edge.getColor());
        assertEquals(this.edge.getThickness(), new_edge.getThickness());
        assertEquals(this.edge.getOrigin(), new_edge.getOrigin());
        assertEquals(this.edge.getDestination(), new_edge.getDestination());
    }

    /**
     * Test du getter et setter du Vertex d'origine de la classe Edge
     */
    public void testOrigin() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        Vertex v3 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.SQUARE);
        this.edge = new Edge("test", Color.GREEN, v1, v2, 0);

        assertEquals(this.edge.getOrigin(), v1);
        this.edge.setOrigin(v3);
        assertEquals(this.edge.getOrigin(), v3);
    }

    /**
     * Test du getter et setter du Vertex de destination de la classe Edge
     */
    public void testDestination() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        Vertex v3 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.SQUARE);
        this.edge = new Edge("test", Color.GREEN, v1, v2, 0);

        assertEquals(this.edge.getDestination(), v2);
        this.edge.setDestination(v3);
        assertEquals(this.edge.getDestination(), v3);
    }

    /**
     * Test du getter et setter de l'épaisseur thickness de la classe Edge
     */
    public void testThickness() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.edge = new Edge("test", Color.GREEN, v1, v2, 12);

        assertEquals(this.edge.getThickness(), 12);
        this.edge.setThickness(34);
        assertEquals(this.edge.getThickness(), 34);
    }

    /**
     * Test de la méthode permettant de savoir si la classe est un Vertex ou non
     */
    public void testIsVertex() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        this.edge = new Edge("test", Color.GREEN, v1, v2, 12);

        assertFalse(this.edge.isVertex());
    }
}
