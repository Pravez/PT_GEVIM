package algorithm;

import data.Graph;
import data.Vertex;

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
            case VALUE:
                valueColor(graph);
                break;
            default:
                break;
        }
    }

    private void valueColor(Graph graph) {
        int minValue = 0;
        int maxValue = Integer.MAX_VALUE;
        for( Vertex v : graph.getVertexes()){
            if (v.getValue() > maxValue)
                maxValue = v.getValue();
            else if (v.getValue() < minValue)
                minValue = v.getValue();
        }
        int coef = 1;
        if (maxValue > minValue)
            coef = maxValue-minValue;
        for( Vertex v : graph.getVertexes()) {
            if ( v.getEdges().size() == maxValue)
                v.setColor(max);
            else if (v.getEdges().size() == minValue)
                v.setColor(min);
            else{
                //new Color(v.getEdges().size()*255/maxEdges, 255-v.getEdges().size()*255/maxEdges ,0)
                int r = max.getRed() - ((max.getRed() - min.getRed()) * v.getValue() )/coef;
                r = (r > 255)?  255 :  ( (r < 0 ) ? 0 : r );
                int g = max.getGreen() - ((max.getGreen() - min.getGreen()) * v.getValue() )/coef;
                g = (g > 255)?  255 :  ( (g < 0 ) ? 0 : g );
                int b = max.getBlue() - ((max.getBlue() - min.getBlue()) * v.getValue() )/coef;
                b = (b > 255)?  255 :  ( (b < 0 ) ? 0 : b );

                Color color = new Color( r ,
                        g,
                        b);
                v.setColor(color);

            }
        }
        graph.setChanged();
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
                int r = max.getRed() - ((max.getRed() - min.getRed()) * v.getEdges().size() )/coef;
                r = (r > 255)?  255 :  ( (r < 0 ) ? 0 : r );
                int g = max.getGreen() - ((max.getGreen() - min.getGreen()) * v.getEdges().size() )/coef;
                g = (g > 255)?  255 :  ( (g < 0 ) ? 0 : g );
                int b = max.getBlue() - ((max.getBlue() - min.getBlue()) * v.getEdges().size() )/coef;
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
        int minSize = 0;
        int maxSize = Integer.MAX_VALUE;
        for( Vertex v : graph.getVertexes()){
            if (v.getValue() > maxSize)
                maxSize = v.getValue();
            else if (v.getValue() < minSize)
                minSize = v.getValue();
        }
        int coef = 1;
        if (maxSize > minSize)
            coef = maxSize-minSize;
        for( Vertex v : graph.getVertexes()) {
            if ( v.getEdges().size() == maxSize)
                v.setColor(max);
            else if (v.getEdges().size() == minSize)
                v.setColor(min);
            else{
                //new Color(v.getEdges().size()*255/maxEdges, 255-v.getEdges().size()*255/maxEdges ,0)
                int r = max.getRed() - ((max.getRed() - min.getRed()) * v.getValue() )/coef;
                r = (r > 255)?  255 :  ( (r < 0 ) ? 0 : r );
                int g = max.getGreen() - ((max.getGreen() - min.getGreen()) * v.getValue() )/coef;
                g = (g > 255)?  255 :  ( (g < 0 ) ? 0 : g );
                int b = max.getBlue() - ((max.getBlue() - min.getBlue()) * v.getValue() )/coef;
                b = (b > 255)?  255 :  ( (b < 0 ) ? 0 : b );

                Color color = new Color( r ,
                        g,
                        b);
                v.setColor(color);

            }
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

