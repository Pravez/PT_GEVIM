package main.java.algorithm;

import main.java.data.Graph;

import java.awt.*;

/**
 * Created by cordavidenko on 02/03/15.
 */


public class VertexColoring implements IAlgorithm {

    private Property p;

    @Override
    public void run(Graph graph) {
        switch (p){
            case SIZE:
                sizeColor(graph);
                break;
            case NBEDGES:
                nbEdgesColor(graph);
                break;
            default:
                break;
        }
    }

    private void nbEdgesColor(Graph graph) {
        int maxEdges = 0;
        for( main.java.data.Vertex v : graph.getVertexes()){
            if (v.getEdges().size() > maxEdges)
                maxEdges = v.getEdges().size();
        }
        for( main.java.data.Vertex v : graph.getVertexes()) {
            if ( v.getEdges().size() == maxEdges)
                v.setColor(Color.red);
            else v.setColor(new Color(v.getEdges().size()*255/maxEdges, 255-v.getEdges().size()*255/maxEdges ,0));
        }
        graph.setChanged();
    }

    private void sizeColor(Graph graph) {
        int sizeMax = 0;
        for( main.java.data.Vertex v : graph.getVertexes()){
            if (v.getSize() > sizeMax)
                sizeMax = v.getSize();
        }
        for( main.java.data.Vertex v : graph.getVertexes()) {
            if ( v.getSize() == sizeMax)
                v.setColor(Color.red);
            else v.setColor(new Color(v.getSize()*255/sizeMax, 0,0));
        }
        graph.setChanged();
    }

    public void run(Graph graph, Property p   ){
        this.p = p;
        run(graph);
    }
}

