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
 * Classe principale utilisant les {@link com.tinkerpop.blueprints.Graph} pour écrire des {@link data.Graph} dans des
 * fichiers.
 */
public class GmlGraphWriter {

    private Graph graph;
    private TinkerGraph gmlGraph;
    private GraphMLWriter gmlWriter;
    private File file;


    /**
     * Constructeur de la classe
     * @param graph Le {@link data.Graph} à retranscrire en GraphML.
     * @param file Le {@link java.io.File} dans lequel sera écrite la retranscription.
     */
    public GmlGraphWriter(Graph graph, File file){
        this.graph = graph;
        this.gmlGraph = new TinkerGraph();
        this.gmlWriter = new GraphMLWriter(gmlGraph);
        this.file = file;
    }

    /**
     * Méthode de création du {@link com.tinkerpop.blueprints.Graph} grâce à la retranscription de tous les
     * éléments du {@link data.Graph} en {@link com.tinkerpop.blueprints.Element} (les éléments du GraphML).
     * Elle applique des propriétés au graphe.
     */
    public void createGmlGraph(){

        addGraphElementsToGml();

        Map<String, String> vertexKeyTypes = new HashMap<String, String>();
        vertexKeyTypes.put("name", GraphMLTokens.STRING);
        vertexKeyTypes.put("weight", GraphMLTokens.INT);
        vertexKeyTypes.put("color", "color");
        vertexKeyTypes.put("x", GraphMLTokens.DOUBLE);
        vertexKeyTypes.put("y", GraphMLTokens.DOUBLE);

        Map<String, String> edgeKeyTypes = new HashMap<String, String>();

        gmlWriter.setVertexKeyTypes(vertexKeyTypes);
        gmlWriter.setEdgeKeyTypes(edgeKeyTypes);

    }

    /**
     * Méthode de retranscription des {@link data.GraphElement} en éléments du {@link com.tinkerpop.blueprints.Graph}. Ils seront
     * ensuite utilisés pour être écrits dans le fichier associé.
     */
    private void addGraphElementsToGml(){

        HashMap<data.Vertex, Vertex> vertexMapping = new HashMap<>();

        for(data.Vertex v : this.graph.getVertexes()){
            if(!vertexMapping.containsKey(v)){
                Vertex GMLvertex = gmlGraph.addVertex(null);
                GMLvertex.setProperty("name", v.getLabel());
                GMLvertex.setProperty("weight", v.getSize());
                GMLvertex.setProperty("color", "#"+Integer.toHexString(v.getColor().getRGB()).substring(2));
                GMLvertex.setProperty("x", v.getPosition().x);
                GMLvertex.setProperty("y", v.getPosition().y
                );
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

    /**
     * Méthode d'écriture du {@link com.tinkerpop.blueprints.Graph} dans un fichier.
     */
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
