package controller;

import data.Graph;
import data.Vertex;
import org.junit.Before;
import org.junit.Test;
import view.Window;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe de tests pour le controller
 */
public class ControllerTest {

    private Controller controller;
    private Window window;
    private Graph g;

    @Before
    public void setUp() throws Exception {

        controller = new Controller();
        ActionController.setController(controller);
        window = new Window(0, 0, controller);
        controller.setWindow(window);

        g = new Graph();
        window.addNewTab(g, "test");
        controller.addGraph(g);
        controller.changeState("CREATION");
    }

    @Test
    public void testAddVertex() throws Exception {
        controller.addVertex(g, Color.BLACK, new Point(0,0), 1, Vertex.Shape.SQUARE);
        assertNotNull(g.getVertexes().get(0));
    }

    @Test
    public void testAddEdge() throws Exception {

        /*controller.addVertex(g, Color.BLACK, new Point(0,0), 1, Vertex.Shape.SQUARE);
        controller.addVertex(g, Color.BLACK, new Point(0,2), 1, Vertex.Shape.SQUARE);
        controller.addEdge(g.getVertexes().get(0), g.getVertexes().get(1));
        assertNotNull(g.getGraphElements().get(2));*/
    }

    @Test
    public void testAddNewGraph() throws Exception {
        controller.addNewGraph();
        assertNotNull(controller.getGraph(1));
    }

    @Test
    public void testAddGraph() throws Exception {
        Graph g2 = new Graph();
        controller.addGraph(g2);
        assertEquals(controller.getGraph(1), g2);
    }

}