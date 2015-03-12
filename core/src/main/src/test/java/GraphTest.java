import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;
import junit.framework.TestCase;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Admin on 12/03/2015.
 */
public class GraphTest extends TestCase {

    private Graph graph;

    /**
     * Test du constructeur de la classe Graph
     */
    public void testConstructor() {
        this.graph = new Graph();

        assertTrue(this.graph.getGraphElements().isEmpty());
        assertTrue(this.graph.getVertexes().isEmpty());
        assertTrue(this.graph.getEdges().isEmpty());
    }

    /**
     * Test du getter et setter du label de la classe Graph
     */
    public void testName() {
        this.graph = new Graph();

        this.graph.setName("name");
        assertEquals(this.graph.getName(), "name");
        this.graph.setName("");
        assertEquals(this.graph.getName(), "");
    }

    /**
     * Test des méthodes pour ajouter et supprimer des GraphElement au Graph
     */
    public void testAddRemoveGraphElements() {
        this.graph = new Graph();

        /* Ajouter un Vertex au Graph */
        ArrayList<GraphElement> elements = new ArrayList<>();
        Vertex v = new Vertex(Color.BLACK, null, 2, Vertex.Shape.SQUARE);
        elements.add(v);

        this.graph.addGraphElements(elements);
        assertEquals(this.graph.getGraphElements(), elements);
        assertEquals(this.graph.getVertexes(), elements);
        assertTrue(this.graph.getEdges().isEmpty());

        this.graph.removeGraphElement(v);
        assertTrue(this.graph.getGraphElements().isEmpty());
        assertTrue(this.graph.getVertexes().isEmpty());
        assertTrue(this.graph.getEdges().isEmpty());

        this.graph.removeGraphElement(v);
        assertTrue(this.graph.getGraphElements().isEmpty());
        assertTrue(this.graph.getVertexes().isEmpty());
        assertTrue(this.graph.getEdges().isEmpty());

        /* Ajouter un Edge au Graph */
        elements.clear();
        Vertex v1 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CIRCLE);
        Vertex v2 = new Vertex(Color.BLUE, null, 0, Vertex.Shape.CROSS);
        Edge e = new Edge("test", Color.GREEN, v1, v2, 0);
        elements.add(e);

        this.graph.addGraphElements(elements);
        assertEquals(this.graph.getGraphElements(), elements);
        assertTrue(this.graph.getVertexes().isEmpty());
        assertEquals(this.graph.getEdges(), elements);

        this.graph.removeGraphElement(e);
        assertTrue(this.graph.getGraphElements().isEmpty());
        assertTrue(this.graph.getVertexes().isEmpty());
        assertTrue(this.graph.getEdges().isEmpty());

        this.graph.removeGraphElement(e);
        assertTrue(this.graph.getGraphElements().isEmpty());
        assertTrue(this.graph.getVertexes().isEmpty());
        assertTrue(this.graph.getEdges().isEmpty());
    }

    /**
     * Test de la méthode pour copier des GraphElements
     */
    public void testCopyGraphElements() {
        ArrayList<GraphElement> elements = new ArrayList<>();
        elements.add(new Vertex(Color.BLACK, new Point(0, 0), 2, Vertex.Shape.SQUARE));
        Vertex v1 = new Vertex(Color.BLUE, new Point(0, 0), 0, Vertex.Shape.CIRCLE);
        elements.add(v1);
        Vertex v2 = new Vertex(Color.BLUE, new Point(0, 0), 0, Vertex.Shape.CROSS);
        elements.add(v2);
        elements.add(new Edge("test", Color.GREEN, v1, v2, 0));

        ArrayList<GraphElement> copied_elements = Graph.copyGraphElements(elements);
        assertEquals(copied_elements.size(), elements.size());

        for (int i = 0 ; i < copied_elements.size() ; i++ ) {
            assertEquals(elements.get(i).getLabel(), copied_elements.get(i).getLabel());
            assertEquals(elements.get(i).getColor(), copied_elements.get(i).getColor());
            assertEquals(elements.get(i).isVertex(), copied_elements.get(i).isVertex());
        }
    }
}
