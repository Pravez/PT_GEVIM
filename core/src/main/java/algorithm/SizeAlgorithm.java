package algorithm;

import data.Graph;
import data.Vertex;

/**
 * Classe SizeAlgorithm, implémente l'interface {@link algorithm.IAlgorithm}. Rédifinit la taille des {@link data.Vertex} en fonction d'une {@link algorithm.Property}
 */
public class SizeAlgorithm implements IAlgorithm {

    private static final int MINIMAL_SIZE = 10;//taille minimale qui sera appliquée
    private Property property; // propriété selon laquelle seront redimensionnés les Vertices

    /**
     * Applique l'algorithme suivant la propriété choisie
     * @param graph le Graph sur lequel faire tourner l'algorithme
     */
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

    /**
     * Redimensionne les {@link data.Vertex} suivant leurs valeurs
     * @param graph Graph sur lequel est appliqué l'algorithme
     */
    private void valueSize(Graph graph) {
        for (Vertex v : graph.getVertexes()){
            int newSize = v.getValue() + MINIMAL_SIZE;
            v.setSize(newSize);
        }
        graph.setChanged();
    }

    /**
     * Redimensionne les {@link data.Vertex} suivant le nombre de {@link data.Edge} qu'ils possèdent
     * @param graph Graph sur lequel est appliqué l'algorithme
     */
    private void nbEdgesSize(Graph graph) {
        for (Vertex v : graph.getVertexes()){
            v.setSize(v.getEdges().size() + MINIMAL_SIZE);
        }
        graph.setChanged();
    }

    /**
     * Applique l'algorithme suivant la propriété choisie
     * @param graph Graph sur lequel est appliqué l'algorithme
     * @param p propriété paramétrant l'algorithme
     */
    public void run(Graph graph, Property p){
        property = p;
        run(graph);
    }
}
