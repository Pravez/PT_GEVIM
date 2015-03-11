package files.dot;

import data.Edge;
import data.Graph;
import data.GraphElement;
import data.Vertex;
import files.dot.elements.EdgeDot;
import files.dot.elements.VertexDot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by paubreton on 08/03/15.
 * Classe permettant de créer un Graphe prêt à être exporté en fichier .dot.
 */
public class GraphDot {

    private ArrayList<EdgeDot> edges;
    private ArrayList<VertexDot> vertices;
    private String name;

    public GraphDot() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Méthode créant le GraphDot à partir d'un {@link data.Graph}
     * @param graph Le {@link data.Graph} à convertir
     */
    public void createFromGraph(Graph graph){

        this.name = graph.getName();

        HashMap<Vertex, VertexDot> vertexMapping = new HashMap<>();

        for(Vertex v : graph.getVertexes()){

            VertexDot vd = new VertexDot();
            vd.setId(v.getValue());
            vd.addAttribute("label", v.getLabel());
            vd.addAttribute("pos", "(" + v.getPosition().x + "%" + v.getPosition().y + ")");
            vd.addAttribute("size", v.getSize());
            vd.addAttribute("shape", v.getShape().toString());
            vd.addAttribute("color", "#"+Integer.toHexString(v.getColor().getRGB()).substring(2));

            vertices.add(vd);
            vertexMapping.put(v, vd);
        }

        for(Edge e : graph.getEdges()){

            EdgeDot ed = new EdgeDot();
            ed.setId(e.getValue());
            ed.addAttribute("label", e.getLabel());
            ed.addAttribute("size", e.getThickness());
            ed.addAttribute("color", "#" + Integer.toHexString(e.getColor().getRGB()).substring(2));

            ed.setOrigin(vertexMapping.get(e.getOrigin()));
            ed.setDestination(vertexMapping.get(e.getDestination()));

            this.edges.add(ed);
        }
    }

    /**
     * Méthode exportant le GraphDot vers un {@link data.Graph}
     * @return Le {@link data.Graph} créee
     */
    public Graph exportToGraph(){
        ArrayList<GraphElement> elements = new ArrayList<>();
        HashMap<VertexDot, Vertex> verticesMap = new HashMap<>();

        for(VertexDot vd : vertices){
            String position = (String) vd.getAttributes().get("pos");
            position = position.replace("(","");
            position = position.replace(")", "");
            int x = Integer.parseInt(position.split("%")[0]);
            int y = Integer.parseInt(position.split("%")[1]);
            Color color = Color.decode((String)vd.getAttributes().get("color"));
            Vertex v = new Vertex((String)vd.getAttributes().get("label"), color, new Point(x,y),Integer.parseInt((String)vd.getAttributes().get("size")), Vertex.Shape.decode((String)vd.getAttributes().get("shape")));
            elements.add(v);
            verticesMap.put(vd, v);
        }

        for(EdgeDot ed : edges){

            VertexDot origin = ed.getOrigin();
            VertexDot destination = ed.getDestination();
            Color color = Color.decode((String)ed.getAttributes().get("color"));


            Edge e = new Edge((String)ed.getAttributes().get("label"), color, verticesMap.get(origin), verticesMap.get(destination), Integer.parseInt((String) ed.getAttributes().get("size")));
            elements.add(e);
        }

        Graph g = new Graph();
        g.addGraphElements(elements);
        g.setName(this.name);

        return g;
    }

    /**
     * Getter du nom du graphe
     * @return Le nom du graphe (string)
     */
    public String getName() {
        return name;
    }

    /**
     * Setter du nom du graphe
     * @param name Le nom du graphe (string)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Méthode de récupération d'un {@link files.dot.elements.VertexDot} grâce à son ID.
     * @param id
     * @return
     */
    public VertexDot getVertexById(int id){
        for(VertexDot de : vertices){
            if(de.getId() == id){
                return de;
            }
        }

        return null;
    }

    /**
     * Getter des {@link files.dot.elements.EdgeDot} d'un {@link files.dot.GraphDot}
     * @return Un {@link java.util.ArrayList} de {@link files.dot.elements.EdgeDot}
     */
    public ArrayList<EdgeDot> getEdges() {
        return edges;
    }

    /**
     * Getter des {@link files.dot.elements.VertexDot} d'un {@link files.dot.GraphDot}
     * @return Un {@link java.util.ArrayList} de {@link files.dot.elements.VertexDot}
     */
    public ArrayList<VertexDot> getVertices() {
        return vertices;
    }
}
