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

    public Tab(Graph graph) {
        super();
        this.graph = graph;
        this.graph.setDefaultWidth(10);
    }

    public void paintComponent(Graphics g){
        for(Vertex v : graph.getVertexes()){
            g.setColor(Color.BLACK);
            g.drawRect(v.getPositionX(), v.getPositionY(), v.getWidth(), v.getWidth());
        }
    }
    /*
    A partir du graphe dessiner tout ce qu'il contient.
     */

}
