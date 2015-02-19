package algorithm;

import data.Graph;
import data.Vertex;

import java.awt.*;
import java.util.Random;

/**
 * Created by cordavidenko on 19/02/15.
 */
public class RandomPositioning implements IAlgorithm {

    private int height;
    private int width;

    public RandomPositioning(Dimension size) {
        this.height = size.height;
        this.width = size.width;
    }

    @Override
    public void run(Graph graph) {
        Random r = new Random();
        for(Vertex v : graph.getVertexes()){
            v.setPosition(new Point(r.nextInt(width), r.nextInt(height)));
        }
        graph.setChanged();
    }
}
