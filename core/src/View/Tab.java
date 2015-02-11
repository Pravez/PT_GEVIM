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
    }

    public void paintComponent(Graphics g){
        for(Vertex v : graph.getVertexes()){
            g.setColor(Color.BLACK);
            g.drawRect(v.getPositionX(), v.getPositionY(), 10, 10);

        }

        g.drawRect(0,0,100,100);
    }
    /*
    A partir du graphe dessiner tout ce qu'il contient.
     */

}
