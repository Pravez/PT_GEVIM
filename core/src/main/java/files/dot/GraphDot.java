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
 */
public class GraphDot {

    private ArrayList<EdgeDot> edges;
    private ArrayList<VertexDot> vertices;
    private String name;

    public GraphDot() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

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

    public Graph exportToGraph(){
        ArrayList<GraphElement> elements = new ArrayList<>();
        HashMap<VertexDot, Vertex> verticesMap = new HashMap<>();

        for(VertexDot vd : vertices){
            String position = (String) vd.getAttributes().get("pos");
            position = position.replace("(","");
            position = position.replace(")", "");
            int x = Integer.parseInt(position.split("%")[0]);
            int y = Integer.parseInt(position.split("%")[1]);
            Vertex v = new Vertex((String)vd.getAttributes().get("label"), Color.BLACK, new Point(x,y),Integer.parseInt((String)vd.getAttributes().get("size")), Vertex.Shape.SQUARE);
            elements.add(v);
            verticesMap.put(vd, v);
        }

        for(EdgeDot ed : edges){

            VertexDot origin = ed.getOrigin();
            VertexDot destination = ed.getDestination();

            Edge e = new Edge((String)ed.getAttributes().get("label"), Color.BLACK, verticesMap.get(origin), verticesMap.get(destination), Integer.parseInt((String) ed.getAttributes().get("size")));
            elements.add(e);
        }

        Graph g = new Graph();
        g.addGraphElements(elements);
        g.setName(this.name);

        return g;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VertexDot getVertexById(int id){
        for(VertexDot de : vertices){
            if(de.getId() == id){
                return de;
            }
        }

        return null;
    }

    public ArrayList<EdgeDot> getEdges() {
        return edges;
    }

    public ArrayList<VertexDot> getVertices() {
        return vertices;
    }
}
