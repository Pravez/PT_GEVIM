package files.dot;

import files.dot.elements.DotElement;
import files.dot.elements.EdgeDot;
import files.dot.elements.VertexDot;

import java.io.*;

/**
 * Classe principale de mangement de la lecture dans un fichier .dot
 */
public class DotFileReader {

    private GraphDot graphDot;
    private File file;

    /**
     * Constructeur de la classe
     * @param file Le {@link java.io.File} depuis qui récupérer le {@link files.dot.GraphDot}
     */
    public DotFileReader(File file) {
        this.file = file;
        graphDot = new GraphDot();
    }

    /**
     * Méthode de lecture et d'interprétation des données du {@link java.io.File}
     * @throws IOException Si jamais le fichier n'est pas trouvé
     */
    public void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //Lecture de toutes les lignes du fichier
        for(String line; (line = reader.readLine()) != null; ) {
            processLine(line);
        }
    }

    /**
     * Méthode d'interprétation d'une seul ligne d'un fichier .dot
     * @param line Le {@link java.lang.String} contenant la ligne
     */
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
            createAttributes(line, ed);

            graphDot.getEdges().add(ed);
        }else if(line.contains("}")){
            //Fin de la lecture, ne fait rien
        }else{

            VertexDot vd = new VertexDot();

            line = line.replace(" ", "");

            vd.setId(Integer.parseInt(line.substring(0,line.indexOf("["))));

            line = line.substring(line.indexOf("["), line.length());
            line = line.replace("[", "");
            line = line.replace("]", "");
            line = line.replace(";", "");

            createAttributes(line, vd);

            graphDot.getVertices().add(vd);
        }
    }

    /**
     * Méthode de création des attributs d'un {@link files.dot.elements.DotElement} à partir d'un {@link java.lang.String}
     * @param attributes Le {@link java.lang.String} à interpréter
     * @param elem Le {@link files.dot.elements.DotElement} dans lequel ranger les attributs crées
     */
    public void createAttributes(String attributes, DotElement elem){
        String[] attributesFile = attributes.split(",");

        for(String att : attributesFile){
            elem.addAttribute(att.split("=")[0], att.split("=")[1]);
        }
    }

    /**
     * Getter du {@link files.dot.GraphDot} associé à l'instance
     * @return Un {@link files.dot.GraphDot}
     */
    public GraphDot getGraphDot(){
        return graphDot;
    }
}
