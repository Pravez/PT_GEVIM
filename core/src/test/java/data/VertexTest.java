package data;

import data.Edge;
import data.Vertex;
import junit.framework.TestCase;

import java.awt.*;

/**
 * Created by Admin on 12/03/2015.
 */
public class VertexTest extends TestCase {

    private Vertex vertex;

    /**
     * Test du Constructeur de la classe Vertex sans le label en paramètre
     */
    public void testSimpleConstructor() {
        this.vertex = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        assertEquals(this.vertex.getColor(), Color.BLUE);
        assertEquals(this.vertex.getPosition(), null);
        assertEquals(this.vertex.getSize(), 0);
        assertEquals(this.vertex.getShape(), Vertex.Shape.CIRCLE);
    }

    /**
     * Test du Constructeur de la classe Vertex avec le label en paramètre
     */
    public void testComplexConstructor() {
        this.vertex = new Vertex("label", Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        assertEquals(this.vertex.getLabel(), "label");
        assertEquals(this.vertex.getColor(), Color.BLUE);
        assertEquals(this.vertex.getPosition(), null);
        assertEquals(this.vertex.getSize(), 0);
        assertEquals(this.vertex.getShape(), Vertex.Shape.CIRCLE);
    }

    /**
     * Test du Constructeur de la classe Vertex par copie
     */
    public void testCopyConstructor() {
        Vertex v = new Vertex("label", Color.BLUE, new Point(), 0, Vertex.Shape.CIRCLE);
        this.vertex = new Vertex(v);
        assertEquals(this.vertex.getLabel(), v.getLabel());
        assertEquals(this.vertex.getColor(), v.getColor());
        assertEquals(this.vertex.getPosition(), v.getPosition());
        assertEquals(this.vertex.getSize(), v.getSize());
        assertEquals(this.vertex.getShape(), v.getShape());
    }

    /**
     * Test de l'ajout et de la suppression d'une Edge d'un Vertex
     */
    public void testAddRemoveEdge() {
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        Edge new_edge = new Edge("test", Color.GREEN, v1, v2, 0);
        this.vertex = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);

        assertEquals(this.vertex.getEdges().size(), 0);
        this.vertex.addEdge(new_edge);
        assertEquals(this.vertex.getEdges().size(), 1);
        assertTrue(this.vertex.getEdges().contains(new_edge));

        this.vertex.removeEdge(new_edge);
        assertEquals(this.vertex.getEdges().size(), 0);
        assertFalse(this.vertex.getEdges().contains(new_edge));

        this.vertex.removeEdge(new_edge);
        assertEquals(this.vertex.getEdges().size(), 0);
        assertFalse(this.vertex.getEdges().contains(new_edge));
    }

    /**
     * Test du getter et setter de la position de la classe Vertex
     */
    public void testPosition() {
        this.vertex = new Vertex(Color.BLUE, new Point(0, 1), 0, Vertex.Shape.CIRCLE);

        assertEquals(this.vertex.getPosition(), new Point(0, 1));
        this.vertex.setPosition(new Point(-5, 15));
        assertEquals(this.vertex.getPosition(), new Point(-5, 15));
    }

    /**
     * Test du getter et setter de la taille de la classe Vertex
     */
    public void testSize() {
        this.vertex = new Vertex(Color.BLUE, null, 12, Vertex.Shape.CIRCLE);

        assertEquals(this.vertex.getSize(), 12);
        this.vertex.setSize(-4);
        assertEquals(this.vertex.getSize(), -4);
    }

    /**
     * Test du getter et setter de la forme de la classe Vertex
     */
    public void testShape() {
        this.vertex = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);

        assertEquals(this.vertex.getShape(), Vertex.Shape.CIRCLE);
        this.vertex.setShape(Vertex.Shape.CROSS);
        assertEquals(this.vertex.getShape(), Vertex.Shape.CROSS);
    }

    /**
     * Test de la méthode move de la classe Vertex
     */
    public void testMove() {
        this.vertex = new Vertex(Color.BLUE, new Point(0, 0), 0, Vertex.Shape.CIRCLE);

        assertEquals(this.vertex.getPosition(), new Point(0, 0));
        this.vertex.move(-5, 12);
        assertEquals(this.vertex.getPosition(), new Point(-5, 12));
        this.vertex.move(0, 21);
        assertEquals(this.vertex.getPosition(), new Point(-5, 33));
    }

    /**
     * Test de la méthode permettant de savoir si la classe est un Vertex ou non
     */
    public void testIsVertex() {
        this.vertex = new Vertex(Color.BLUE, new Point(0, 0), 0, Vertex.Shape.CIRCLE);

        assertTrue(this.vertex.isVertex());
    }
}
