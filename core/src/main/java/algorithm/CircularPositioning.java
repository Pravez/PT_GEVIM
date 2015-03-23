package algorithm;

import data.Graph;
import data.Vertex;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe CircularPositioning, implémente l'interface {@link algorithm.IAlgorithm}. Permet un positionnement circulaire de l'ensemble des {@link data.Vertex}
 */
public class CircularPositioning implements IAlgorithm {

    private Rectangle viewRectangle;//Rectangle délimitant la zone dans laquelle se tracera le cercle

    /**
     * Constructeur de la classe CircularPositioning
     * @param position origine de viewRectangle
     * @param size taille des côtés de viewRectangle
     */
    public CircularPositioning(Point position, Dimension size) {
        this.viewRectangle = new Rectangle(position, size);
    }

    /**
     * Méthode qui positionnera l'ensemble des Vertices du Graph passé en paramètre de manière circulaire
     * @param graph le Graph sur lequel faire tourner l'algorithme
     */
    @Override
    public void run(Graph graph) {
        ArrayList<Vertex> vertexes = graph.getVertexes();

        for (double i = 0; i< vertexes.size(); i++){
            double x = Math.cos((i /(double)vertexes.size()) * 2.0*Math.PI)*(Math.min(this.viewRectangle.height,this.viewRectangle.width)*0.5) +this.viewRectangle.width/2;
            double y = Math.sin((i/(double)vertexes.size())*2.0*Math.PI)*( Math.min(this.viewRectangle.height,this.viewRectangle.width)*0.5)+this.viewRectangle.height/2;
            vertexes.get((int)i).setPosition(new Point((int) x+this.viewRectangle.x, (int)y+this.viewRectangle.y ));
        }
        graph.setChanged();
    }
}
