package algorithm;

import data.Graph;
import data.Vertex;

import java.awt.*;
import java.util.Random;

/**
 * Classe RandomPositioning, implémente l'interface {@link algorithm.IAlgorithm}. Permet un positionnement aléatoire de l'ensemble des {@link data.Vertex}
 * */
public class RandomPositioning implements IAlgorithm {

    private Rectangle viewRectangle;//Rectangle délimitant la zone dans laquelle se tracera le cercle

    /**
     * Constructeur de la classe RandomPositioning
     * @param position origine de viewRectangle
     * @param size taille des côtés de viewRectangle
     */
    public RandomPositioning(Point position, Dimension size) {
        this.viewRectangle = new Rectangle(position,size);
    }

    /**
     *Méthode qui positionnera l'ensemble des {@link data.Vertex} du {@link data.Graph} passé en paramètre de manière circulaire
     * @param graph le Graph sur lequel faire tourner l'algorithme
     */
    @Override
    public void run(Graph graph) {
        Random r = new Random();
        for(Vertex v : graph.getVertexes()){
            v.setPosition(new Point(r.nextInt(this.viewRectangle.width) + this.viewRectangle.x, r.nextInt(this.viewRectangle.height) + this.viewRectangle.y));
        }
        graph.setChanged();
    }
}
