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
        vertexKeyTypes.put("name", GraphMLTokens.STRING);
        vertexKeyTypes.put("weight", GraphMLTokens.INT);
        vertexKeyTypes.put("color", "color");

        Map<String, String> edgeKeyTypes = new HashMap<String, String>();

        gmlWriter.setVertexKeyTypes(vertexKeyTypes);
        gmlWriter.setEdgeKeyTypes(edgeKeyTypes);

    }

    private void addGraphElementsToGml(){

        HashMap<data.Vertex, Vertex> vertexMapping = new HashMap<>();

        for(data.Vertex v : this.graph.getVertexes()){
            if(!vertexMapping.containsKey(v)){
                Vertex GMLvertex = gmlGraph.addVertex(null);
                GMLvertex.setProperty("name", v.getLabel());
                GMLvertex.setProperty("weight", v.getSize());
                GMLvertex.setProperty("color", v.getColor().toString());
                vertexMapping.put(v, GMLvertex);
            }
        }

        for(data.Edge e : this.graph.getEdges()){

            data.Vertex destination = e.getDestination();
            data.Vertex origin = e.getOrigin();

            if(e.getLabel()==null){
                e.setLabel("edge"+e.getValue());
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
