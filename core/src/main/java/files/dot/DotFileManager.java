package files.dot;

import data.Graph;

import java.io.File;
import java.io.IOException;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotFileManager {

    private Graph graph;
    private File fileAssociated;

    public DotFileManager(Graph graph, File fileAssociated) {
        this.graph = graph;
        this.fileAssociated = fileAssociated;
    }

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
}
