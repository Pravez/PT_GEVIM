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
            HashMap<String, Object> properties = vd.getAttributes();

            Point position = new Point(10,10);
            Color color = Color.BLACK;
            String label = "vertex";
            int size = 15;
            Vertex.Shape shape = Vertex.Shape.SQUARE;

            if(properties.get("pos") != null) {
                String pos = (String) vd.getAttributes().get("pos");
                pos = pos.replace("(","");
                pos = pos.replace(")", "");
                position.setLocation(Integer.parseInt(pos.split("%")[0]),Integer.parseInt(pos.split("%")[1]));
            }

            if(properties.get("color") != null) {
                color = Color.decode((String) properties.get("color"));
            }

            if(properties.get("label") != null){
                label = (String)properties.get("label");
            }

            if(properties.get("size") != null){
                size = Integer.parseInt((String)properties.get("size"));
            }

            if(properties.get("shape") != null){
                shape = Vertex.Shape.decode((String) properties.get("shape"));
            }

            Vertex v = new Vertex(label, color, position,size, shape);
            elements.add(v);
            verticesMap.put(vd, v);
        }

        for(EdgeDot ed : edges){

            VertexDot origin = ed.getOrigin();
            VertexDot destination = ed.getDestination();

            HashMap<String, Object> properties = ed.getAttributes();
            Color color = Color.BLACK;
            String label = "edge";
            int size = 1;

            if(properties.get("label")!=null){
                label = (String)properties.get("label");
            }

            if(properties.get("color")!=null){
                color = Color.decode((String)ed.getAttributes().get("color"));
            }

            if(properties.get("size")!=null){
                size = Integer.parseInt((String)properties.get("size"));
            }

            Edge e = new Edge(label, color, verticesMap.get(origin), verticesMap.get(destination), size);
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
