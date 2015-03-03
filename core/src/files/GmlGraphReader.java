package files;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import data.Graph;
import data.GraphElement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by paubreton on 02/03/15.
 */
public class GmlGraphReader {

    private Graph graph;
    private TinkerGraph gmlGraph;
    private File file;
    private GraphMLReader gmlReader;

    public GmlGraphReader(File file){
        this.file = file;
        this.graph = null;
        this.gmlGraph = new TinkerGraph();
        this.gmlReader = new GraphMLReader(gmlGraph);
    }

    public void readFile(){

        try {
            gmlReader.inputGraph(gmlGraph, String.valueOf(this.file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createGraph(){

        this.graph = new Graph();
        ArrayList<GraphElement> graphElements = new ArrayList<>();
        HashMap<Vertex, data.Vertex> vertices = new HashMap<>();

        //TEMPORAIRE
        int x = 10;
        int y = 10;

        for(Vertex v : this.gmlGraph.getVertices()){
            if(!vertices.containsKey(v)){
                data.Vertex sourceVertex = new data.Vertex((String)v.getProperty("name"), Color.BLACK, new Point(x,y), (int)v.getProperty("weight"), data.Vertex.Shape.SQUARE);
                vertices.put(v, sourceVertex);
                graphElements.add(sourceVertex);
            }
        }


        for(Edge e : gmlGraph.getEdges()){

            Vertex source = e.getVertex(Direction.OUT);
            Vertex target = e.getVertex(Direction.IN);

            data.Edge createdEdge = new data.Edge((String)e.getProperty("name"), Color.BLACK, vertices.get(source), vertices.get(target),1);
            graphElements.add(createdEdge);

            //TEMPORAIRE
            if (x >= 500 ){
                y+=50; x=10;}
            else{
                x += 50;}
        }



        this.graph.addGraphElements(graphElements);

    }

    public Graph getCreatedGraph(){
        return this.graph;
    }
}
