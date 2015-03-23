package files.dot;

import data.Graph;

import java.io.File;
import java.io.IOException;

/**
 * Classe principale gérant le fait d'écrire ou de lire un fichier .dot
 */
public class DotFileManager {

    private Graph graph;
    private File fileAssociated;

    /**
     * Constructeur de la classe
     * @param graph Le {@link data.Graph} associé
     * @param fileAssociated Le {@link java.io.File} associé
     */
    public DotFileManager(Graph graph, File fileAssociated) {
        this.graph = graph;
        this.fileAssociated = fileAssociated;
    }

    /**
     * Méthode princiapel de sauvegarde du {@link data.Graph} dans un fichier .dot (utilise {@link files.dot.DotFileWriter}
     */
    public void saveToDotFile(){

        GraphDot graphDot = new GraphDot();
        graphDot.createFromGraph(graph);

        DotFileWriter dotFileWriter = new DotFileWriter(graphDot, fileAssociated);
        try {
            dotFileWriter.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale d'ouverture d'un {@link data.Graph} depuis un fichier .dot (utilise {@link files.dot.DotFileReader}
     */
    public void openDotFile(){

        DotFileReader reader = new DotFileReader(fileAssociated);


        try {
            reader.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        graph = reader.getGraphDot().exportToGraph();

    }

    /**
     * Getter du {@link data.Graph}
     * @return Le Graphe
     */
    public Graph getGraph() {
        return graph;
    }
}
