package main.java.algorithm;

import main.java.data.Graph;
import main.java.data.Vertex;

import java.awt.*;
import java.util.Random;

/**
 * Created by cordavidenko on 19/02/15.
 */
public class RandomPositioning implements IAlgorithm {

    private Rectangle viewRectangle;

    public RandomPositioning(Point position, Dimension size) {
        this.viewRectangle = new Rectangle(position,size);
    }

    @Override
    public void run(Graph graph) {
        Random r = new Random();
        for(Vertex v : graph.getVertexes()){
            v.setPosition(new Point(r.nextInt(this.viewRectangle.width)+this.viewRectangle.x, r.nextInt(this.viewRectangle.height)+this.viewRectangle.y));
        }
        graph.setChanged();
    }
}
