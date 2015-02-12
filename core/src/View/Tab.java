package View;

import data.Edge;
import data.Graph;
import data.Vertex;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Corentin Davidenko on 04/02/15.
 */


public class Tab extends JPanel {

	private static final long serialVersionUID = 1L;
    private Graph             graph;

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

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
    	/**/
    	Stroke oldStroke = ((Graphics2D) g).getStroke();
    	/**/
        for(Vertex v : graph.getVertexes()){
            g.setColor(v.getColor());
            ((Graphics2D) g).setStroke(new BasicStroke(v.getThickness()));
            g.drawRect(v.getPositionX(), v.getPositionY(), v.getWidth(), v.getWidth());
        }

        for(Edge e : graph.getEdges()){
            g.setColor(e.getColor());
            g.drawLine(e.getOrigin().getPositionX(), e.getOrigin().getPositionY(), e.getDestination().getPositionX(), e.getDestination().getPositionY());
        }
        ((Graphics2D) g).setStroke(oldStroke);
    }

    /**
     * Teste si à partir des données d'un événement souris ({@link java.awt.event.MouseEvent}) un vertex est situé au dessous.
     * @param mouseEvent Evénement souris
     * @return Le {@link data.Vertex} s'il existe, sinon null
     */
    public Vertex onVertex(MouseEvent mouseEvent){

        Rectangle rect = new Rectangle();

        for(Vertex v : graph.getVertexes()){

            rect.setBounds(v.getPositionX(), v.getPositionY(), v.getWidth(), v.getWidth());
            if(rect.contains(mouseEvent.getX(), mouseEvent.getY())){
                return v;
            }
        }
        return null;
    }

}
