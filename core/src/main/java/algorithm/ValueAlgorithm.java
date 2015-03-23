package algorithm;

import data.Graph;
import data.Vertex;

import java.util.Random;

/**
 * Classe ValueAlgorithm, implémente l'interface {@link algorithm.IAlgorithm}. Rédifinit la valeur des {@link data.Vertex} en fonction d'une {@link algorithm.Property}
 */
public class ValueAlgorithm  implements IAlgorithm {

    private static final int MAX_RANDOM_INT = 100;//Valeur maximale qu'il sera possible d'associer à un Vertex
    Property property;// propriété selon laquelle seront attribuées les valeurs aux Vertices

    /**
     * Applique l'algorithme suivant la propriété choisie
     * @param graph le Graph sur lequel faire tourner l'algorithme
     */
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

    /**
     * Attribue à chaque {@link data.Vertex} des valeurs égales à leurs tailles
     * @param graph Graph sur lequel est appliqué l'algorithme
     */
    private void sizeValue(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            v.setValue(v.getSize());
        }
        graph.setChanged();
    }

    /**
     * Attribue à chaque {@link data.Vertex} des valeurs égales aux nombre de {@link data.Edge} possédés
     * @param graph Graph sur lequel est appliqué l'algorithme
     */
    private void nbOfEdgeValue(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            v.setValue(v.getEdges().size());
        }
        graph.setChanged();
    }

    /**
     * Attribue à chaque {@link data.Vertex} des valeurs aléatoires
     * @param graph Graph sur lequel est appliqué l'algorithme
     */
    private void randomValue(Graph graph) {
        Random r = new Random();
        for (Vertex v : graph.getVertexes()) {
            v.setValue(r.nextInt(MAX_RANDOM_INT));
        }
        graph.setChanged();
    }


    public void run(Graph graph, Property p) {
        property = p;
        run(graph);
    }
}