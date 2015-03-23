package algorithm;

import data.Graph;
import data.Vertex;

/**
 * Created by Corentin Davidenko on 22/03/15.
 */
public class SizeAlgorithm implements IAlgorithm {

    private static final int MINIMAL_SIZE = 10;
    private Property property;

    @Override
    public void run(Graph graph) {
        switch (property){
            case NBEDGES:
                nbEdgesSize(graph);
                break;
            case VALUE:
                valueSize(graph);
            default:
                break;
        }
    }

    private void valueSize(Graph graph) {
        for (Vertex v : graph.getVertexes()){
            int newSize = v.getValue() + MINIMAL_SIZE;
            v.setSize(newSize);
        }
        graph.setChanged();
    }

    private void nbEdgesSize(Graph graph) {
        for (Vertex v : graph.getVertexes()){
            v.setSize(v.getEdges().size() + MINIMAL_SIZE);
        }
        graph.setChanged();
    }

    public void run(Graph graph, Property p){
        property = p;
        run(graph);
    }
}
