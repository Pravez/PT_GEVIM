package files.dot;

import files.dot.elements.DotElement;
import files.dot.elements.EdgeDot;
import files.dot.elements.VertexDot;

import java.io.*;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotFileReader {

    private GraphDot graphDot;
    private File file;

    public DotFileReader(File file) {
        this.file = file;
        graphDot = new GraphDot();
    }

    public void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        for(String line; (line = reader.readLine()) != null; ) {
            processLine(line);
        }

        //A continuer
    }

    public void processLine(String line){

        if(line.contains("graph")){

            line = line.replace("{", "");
            line = line.replace("graph ", "");
            line = line.replace(" ", "");

            graphDot.setName(line);
        }else if(line.contains("--")){

            EdgeDot ed = new EdgeDot();

            line = line.replace(" ", "");

            String origin = line.substring(0, line.lastIndexOf("-"));
            line = line.replace(origin, "");
            origin = origin.replace(" ", "");
            origin = origin.replace("-", "");
            ed.setOrigin(graphDot.getVertexById(Integer.parseInt(origin)));

            String destination = line.substring(0, line.indexOf("["));
            line = line.replace(destination, "");
            destination = destination.replace("-", "");
            ed.setDestination(graphDot.getVertexById(Integer.parseInt(destination)));

            line = line.replace("[", "");
            line = line.replace("]", "");
            line = line.replace(";", "");
            line = line.substring(0, line.length()-1);
            createAttributes(line, ed);

            graphDot.getEdges().add(ed);
        }else if(line.contains("}")){

        }else{

            VertexDot vd = new VertexDot();

            line = line.replace(" ", "");

            vd.setId(Integer.parseInt(line.substring(0,1)));

            line = line.substring(1, line.length());
            line = line.replace("[", "");
            line = line.replace("]", "");
            line = line.replace(";", "");
            line = line.substring(0, line.length()-1);

            createAttributes(line, vd);

            graphDot.getVertices().add(vd);
        }
    }

    public void createAttributes(String attributes, DotElement elem){
        String[] attributesFile = attributes.split(",");

        for(String att : attributesFile){
            elem.addAttribute(att.split("=")[0], att.split("=")[1]);
        }
    }

    public GraphDot getGraphDot(){
        return graphDot;
    }
}
