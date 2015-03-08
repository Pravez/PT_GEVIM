package files.gml;

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
 * Classe de lecture de fichier .gml et d'interprétation des données du fichier. Elle utilise la classe {@link com.tinkerpop.blueprints.Graph},
 * associée à la bibliothèque tinkerpop.blueprints.
 */
public class GmlGraphReader {

    private Graph graph;
    private TinkerGraph gmlGraph;
    private File file;

    /**
     * Constructeur de la classe.
     * @param file Le fichier dans lequel récupérer les données
     */
    public GmlGraphReader(File file){
        this.file = file;
        this.graph = null;
        this.gmlGraph = new TinkerGraph();
        new GraphMLReader(gmlGraph);
    }

    /**
     * Méthode de lecture du fichier. Elle range les données dans un {@link com.tinkerpop.blueprints.Graph}. (plus précisément
     * dans un {@link com.tinkerpop.blueprints.impls.tg.TinkerGraph})
     */
    public void readFile(){

        try {
            GraphMLReader.inputGraph(gmlGraph, String.valueOf(this.file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode de création du {@link data.Graph} associé aux données qui ont pu être récupérées du fichier.
     */
    public void createGraph() {
        this.graph = new Graph();
        ArrayList<GraphElement> graphElements = new ArrayList<>();
        HashMap<Vertex, data.Vertex> vertices = new HashMap<>();

        for (Vertex v : this.gmlGraph.getVertices()) {
            if (!vertices.containsKey(v)) {
                data.Vertex sourceVertex = new data.Vertex((String) v.getProperty("name"), Color.decode((String) v.getProperty("color")), new Point((int) v.getProperty("x"), (int) v.getProperty("y")), (int) v.getProperty("weight"), data.Vertex.Shape.SQUARE);
                vertices.put(v, sourceVertex);
                graphElements.add(sourceVertex);
            }
        }


        for (Edge e : gmlGraph.getEdges()) {

            Vertex source = e.getVertex(Direction.OUT);
            Vertex target = e.getVertex(Direction.IN);

            data.Edge createdEdge = new data.Edge((String) e.getProperty("name"), Color.BLACK, vertices.get(source), vertices.get(target), 1);
            graphElements.add(createdEdge);

        }

        this.graph.addGraphElements(graphElements);
    }

    /**
     * Getter du {@link data.Graph} crée
     * @return Le {@link data.Graph} instancié depuis le fichier
     */
    public Graph getCreatedGraph(){
        return this.graph;
    }
}
