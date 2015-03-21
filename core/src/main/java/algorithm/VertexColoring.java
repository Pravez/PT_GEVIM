package algorithm;

import data.Graph;

import java.awt.*;

/**
 * Created by cordavidenko on 02/03/15.
 */


public class VertexColoring implements IAlgorithm {

    private Property p;
    private Color min;
    private Color max;

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
        int minEdges = Integer.MAX_VALUE;
        for( data.Vertex v : graph.getVertexes()){
            if (v.getEdges().size() > maxEdges)
                maxEdges = v.getEdges().size();
            else if (v.getEdges().size() < minEdges)
                minEdges = v.getEdges().size();
        }
        int coef = 1;
        if (maxEdges > minEdges)
            coef = maxEdges-minEdges;
        for( data.Vertex v : graph.getVertexes()) {
            if ( v.getEdges().size() == maxEdges)
                v.setColor(max);
            else if (v.getEdges().size() == minEdges)
                v.setColor(min);
            else{
                //new Color(v.getEdges().size()*255/maxEdges, 255-v.getEdges().size()*255/maxEdges ,0)
                int r = ((max.getRed() - (min.getRed() * v.getEdges().size()*coef ))/maxEdges);
                r = (r > 255)?  255 :  ( (r < 0 ) ? 0 : r );
                int g = (max.getGreen() - (min.getGreen() * v.getEdges().size()*coef ))/ maxEdges;
                g = (g > 255)?  255 :  ( (g < 0 ) ? 0 : g );
                int b = ((max.getBlue() - (min.getBlue() * v.getEdges().size()*coef ))/maxEdges);
                b = (b > 255)?  255 :  ( (b < 0 ) ? 0 : b );

                Color color = new Color( r ,
                        g,
                        b);
                v.setColor(color);

            }
        }
        graph.setChanged();
    }

    private void sizeColor(Graph graph) {
        int sizeMax = 0;
        for( data.Vertex v : graph.getVertexes()){
            if (v.getSize() > sizeMax)
                sizeMax = v.getSize();
        }
        for( data.Vertex v : graph.getVertexes()) {
            if ( v.getSize() == sizeMax)
                v.setColor(Color.red);
            else v.setColor(new Color(v.getSize()*255/sizeMax, 0,0));
        }
        graph.setChanged();
    }

    public void run(Graph graph, Property p, Color min, Color max){
        this.p = p;
        this.min = min;
        this.max = max;
        run(graph);
    }
}

