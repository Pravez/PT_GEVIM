package files.dot;

import files.dot.elements.EdgeDot;
import files.dot.elements.VertexDot;

import java.io.*;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotFileWriter {

    private GraphDot graphDot;
    private File file;

    public DotFileWriter(GraphDot graphDot, File file) {
        this.graphDot = graphDot;
        this.file = file;
    }

    public void writeFile() throws IOException {

        DotFileParser filewriter = new DotFileParser(graphDot, file);

        String fileHeader = "graph " + graphDot.getName() + " { \n";
        String fileFooter = "}";

        filewriter.write(fileHeader);

        for(VertexDot vd : graphDot.getVertices()){
            filewriter.write(vd, 1);
        }

        for(EdgeDot ed : graphDot.getEdges()){
            filewriter.write(ed, 1);
        }

        filewriter.write(fileFooter);

        filewriter.close();
    }
}
