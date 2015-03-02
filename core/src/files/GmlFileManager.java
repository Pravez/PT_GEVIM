package files;

import com.sun.istack.internal.Nullable;
import data.Graph;

import java.io.File;

/**
 * Created by paubreton on 23/02/15.
 * Classe permettant de gérer l'utilisation de l'enregistrement de graphes au format GML.
 * Elle est héritée de . Elle possède des méthodes créant le document GML,
 * puis l'enregistrant dans un fichier.
 * Le document n'est pas un GML officiel, mais un réalisé à la main aussi proche que possible du réel de manière à
 * ce qu'il soit lu partout.
 */
public class GmlFileManager{

    @Nullable
    private Graph graph;
    private File fileAssociated;

    /**
     * Constructeur de base
     * @param graph {@link data.Graph} associé au document
     * @param fileAssociated {@link java.io.File} associé au document
     */
    public GmlFileManager(Graph graph, File fileAssociated) {

        super();

        this.graph = graph;
        this.fileAssociated = fileAssociated;
    }

    public void openGraph(){

        GmlGraphReader gmlGraphReader = new GmlGraphReader(this.fileAssociated);
        gmlGraphReader.readFile();
        gmlGraphReader.createGraph();

        this.graph = gmlGraphReader.getCreatedGraph();

    }

    public void saveGraph(){

        GmlGraphWriter gmlGraphWriter = new GmlGraphWriter(this.graph, this.fileAssociated);
        gmlGraphWriter.createGmlGraph();
        gmlGraphWriter.writeGraphToFile();

    }

    public Graph getGraph(){
        return this.graph;
    }
}
