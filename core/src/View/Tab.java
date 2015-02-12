package View;

import data.Graph;
import data.Vertex;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Corentin Davidenko on 04/02/15.
 */


public class Tab extends JPanel {

	private static final long serialVersionUID = 1L;
	private Graph             graph;

    /**
     * Constructeur du Tab, l'onglet. Un onglet est associé à un {@link data.Graph}
     * @param graph Graphe devant être associé
     */
    public Tab(Graph graph) {
        super();
        this.graph = graph;
    }

    /**
     * Méthode de dessin des éléments dans un onglet, à partir des données d'un {@link data.Graph}
     * @param g {@link java.awt.Graphics} à partir de quoi dessiner
     */
    public void paintComponent(Graphics g){
        for(Vertex v : graph.getVertexes()){
            g.setColor(Color.BLACK);
            g.drawRect(v.getPositionX(), v.getPositionY(), 10, 10);

        }
    }
}
