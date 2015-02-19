package algorithm;

import data.Graph;
import data.Vertex;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cordavidenko on 19/02/15.
 */
public class CircularPositioning implements IAlgorithm {

    private int height;
    private int width;

    public CircularPositioning(Dimension size) {
        this.height = size.height;
        this.width = size.width;
    }

    @Override
    public void run(Graph graph) {
        ArrayList<Vertex> vertexes = graph.getVertexes();

        for (double i = 0; i< vertexes.size(); i++){
            double x = Math.cos((i /(double)vertexes.size()) * 2.0*Math.PI)*(Math.min(height,width)*0.5) +width/2;
            double y = Math.sin((i/(double)vertexes.size())*2.0*Math.PI)*( Math.min(height,width)*0.5)+height/2;
            vertexes.get((int)i).setPosition(new Point((int) x, (int)y ));
        }
        graph.setChanged();
    }
}
