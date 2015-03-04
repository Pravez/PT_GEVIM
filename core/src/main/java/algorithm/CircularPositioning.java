package main.java.algorithm;

import main.java.data.Graph;
import main.java.data.Vertex;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cordavidenko on 19/02/15.
 */
public class CircularPositioning implements IAlgorithm {

    private Rectangle viewRectangle;

    public CircularPositioning(Point position, Dimension size) {
        this.viewRectangle = new Rectangle(position, size);
    }

    @Override
    public void run(Graph graph) {
        ArrayList<Vertex> vertexes = graph.getVertexes();

        for (double i = 0; i< vertexes.size(); i++){
            double x = Math.cos((i /(double)vertexes.size()) * 2.0*Math.PI)*(Math.min(this.viewRectangle.height,this.viewRectangle.width)*0.5) +this.viewRectangle.width/2;
            double y = Math.sin((i/(double)vertexes.size())*2.0*Math.PI)*( Math.min(this.viewRectangle.height,this.viewRectangle.width)*0.5)+this.viewRectangle.height/2;
            vertexes.get((int)i).setPosition(new Point((int) x+this.viewRectangle.x, (int)y+this.viewRectangle.y ));
        }
        graph.setChanged();
    }
}
