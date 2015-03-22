package algorithm;

import data.Graph;
import data.Vertex;

import java.util.Random;

/**
 * Created by Corentin Davidenko on 22/03/15.
 */
public class ValueAlgorithm  implements IAlgorithm {

    Property property;

    @Override
    public void run(Graph graph) {
        switch (property) {
            case RANDOM:
                randomValue(graph);
                break;
            case NBEDGES:
                nbOfEdgeValue(graph);
                break;
            case SIZE:
                sizeValue(graph);
                break;
            default:
                break;
        }
    }

    private void sizeValue(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            v.setValue(v.getSize());
        }
    }

    private void nbOfEdgeValue(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            v.setValue(v.getEdges().size());
        }
    }

    private void randomValue(Graph graph) {
        Random r = new Random();
        for (Vertex v : graph.getVertexes()) {
            v.setValue(r.nextInt());
        }
    }

    public void run(Graph graph, Property p) {
        property = p;
        run(graph);
    }
}