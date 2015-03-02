package files;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLTokens;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;
import data.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paubreton on 02/03/15.
 */
public class GmlGraphWriter {

    private Graph graph;
    private TinkerGraph gmlGraph;
    private GraphMLWriter gmlWriter;
    private File file;


    public GmlGraphWriter(Graph graph, File file){
        this.graph = graph;
        this.gmlGraph = new TinkerGraph();
        this.gmlWriter = new GraphMLWriter(gmlGraph);
        this.file = file;
    }

    public void createGmlGraph(){

        addGraphElementsToGml();

        Map<String, String> vertexKeyTypes = new HashMap<String, String>();
        vertexKeyTypes.put(GraphMLTokens.ATTR_NAME, GraphMLTokens.STRING);
        vertexKeyTypes.put("weight", GraphMLTokens.INT);
        vertexKeyTypes.put("color", "color");

        Map<String, String> edgeKeyTypes = new HashMap<String, String>();
        edgeKeyTypes.put(GraphMLTokens.ATTR_NAME, GraphMLTokens.STRING);
        gmlWriter.setVertexKeyTypes(vertexKeyTypes);
        gmlWriter.setEdgeKeyTypes(edgeKeyTypes);

    }

    private void addGraphElementsToGml(){

        HashMap<data.Vertex, Vertex> vertexMapping = new HashMap<>();

        for(data.Edge e : this.graph.getEdges()){

            Vertex GMLdest = null;
            Vertex GMLorg = null;

            data.Vertex destination = e.getDestination();
            data.Vertex origin = e.getOrigin();

            if(!vertexMapping.containsKey(destination)){
                GMLdest = gmlGraph.addVertex(null);
                GMLdest.setProperty(GraphMLTokens.ATTR_NAME, destination.getLabel());
                GMLdest.setProperty("weight", destination.getSize());
                GMLdest.setProperty("color", destination.getColor().getRGB());
                vertexMapping.put(destination, GMLdest);
            }
            if(!vertexMapping.containsKey(origin)){
                GMLorg = gmlGraph.addVertex(null);
                GMLorg.setProperty(GraphMLTokens.ATTR_NAME, origin.getLabel());
                GMLorg.setProperty("weight", origin.getSize());
                GMLorg.setProperty("color", origin.getColor().getRGB());
                vertexMapping.put(origin, GMLorg);
            }

            Edge GMLedge = gmlGraph.addEdge(null, vertexMapping.get(origin), vertexMapping.get(destination), e.getLabel());

        }
    }

    public void writeGraphToFile(){

        FileOutputStream out = null;

        try {

            out = new FileOutputStream(this.file);
            gmlWriter.outputGraph(gmlGraph, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
