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

        Color color;
        String name;
        Point point;
        int size;

        for (Vertex v : this.gmlGraph.getVertices()) {
            if (!vertices.containsKey(v)) {

                if(v.getProperty("r")!=null) {
                    color = new Color((int) v.getProperty("r"), (int) v.getProperty("g"), (int) v.getProperty("b"));
                }else{
                    color = Color.BLACK;
                }

                if(v.getProperty("name")!=null){
                    name = v.getProperty("name");
                }else{
                    name = "element";
                }

                if(v.getProperty("x")!=null && v.getProperty("y")!=null){
                    point = new Point((int) v.getProperty("x"), (int) v.getProperty("y"));
                }else{
                    point = new Point(50, 50);
                }

                if(v.getProperty("size")!=null){
                    size = v.getProperty("size");
                }else{
                    size = 15;
                }

                data.Vertex sourceVertex = new data.Vertex(name, color, point, size, data.Vertex.Shape.SQUARE);
                vertices.put(v, sourceVertex);
                graphElements.add(sourceVertex);
            }
        }


        for (Edge e : gmlGraph.getEdges()) {

            Vertex source = e.getVertex(Direction.OUT);
            Vertex target = e.getVertex(Direction.IN);

            if(e.getProperty("r")!=null){
                color = new Color((int)e.getProperty("r"), (int) e.getProperty("g"), (int) e.getProperty("b"));
            }else{
                color = Color.BLACK;
            }

            if(e.getProperty("name")!=null){
                name = e.getProperty("name");
            }else{
                name = "element";
            }

            if(e.getProperty("size")!=null){
                size = e.getProperty("size");
            }else{
                size = 1;
            }

            data.Edge createdEdge = new data.Edge(name, color, vertices.get(source), vertices.get(target), size);
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
