package files;

import data.Graph;

import java.io.File;

/**
 * Created by paubreton on 23/02/15.
 * Classe permettant de gérer la lecture et l'écriture de fichiers .gml, les fichiers GraphML. Elle utilise
 * la classe {@link files.GmlGraphReader} pour lire les fichiers et la classe {@link files.GmlGraphWriter} pour
 * en écrire. Elle possède un {@link data.Graph} qui sera lu ou écrit, et un {@link java.io.File}, fichier associé.
 */
public class GmlFileManager{

    private Graph graph;
    private File fileAssociated;

    /**
     * Constructeur de base
     * @param graph {@link data.Graph} associé au document
     * @param fileAssociated {@link java.io.File} associé au document
     */
    public GmlFileManager(Graph graph, File fileAssociated) {

        this.graph = graph;
        this.fileAssociated = fileAssociated;
    }

    /**
     * Méthode principale permettant la lecture d'un graphe. Elle appelle et utilise les méthodes de {@link files.GmlGraphReader},
     * et en récupère les données.
     */
    public void openGraph(){

        GmlGraphReader gmlGraphReader = new GmlGraphReader(this.fileAssociated);
        gmlGraphReader.readFile();
        gmlGraphReader.createGraph();

        this.graph = gmlGraphReader.getCreatedGraph();

    }

    /**
     * Méthode principale d'écriture d'un graphe dans un fichier GML. Elle passe les données à la classe {@link files.GmlGraphWriter}
     * et cette dernière se charge de les écrire.
     */
    public void saveGraph(){

        GmlGraphWriter gmlGraphWriter = new GmlGraphWriter(this.graph, this.fileAssociated);
        gmlGraphWriter.createGmlGraph();
        gmlGraphWriter.writeGraphToFile();

    }

    /**
     * Getter du graphe de la classe
     * @return Le graphe associé à la classe
     */
    public Graph getGraph(){
        return this.graph;
    }
}
