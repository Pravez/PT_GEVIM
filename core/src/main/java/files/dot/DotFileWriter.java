package files.dot;

import files.dot.elements.EdgeDot;
import files.dot.elements.VertexDot;

import java.io.*;

/**
 * Created by paubreton on 08/03/15.
 * Classe permettant de manager l'écriture d'un {@link files.dot.GraphDot} dans un fichier
 */
public class DotFileWriter {

    private GraphDot graphDot;
    private File file;

    /**
     * Constructeur de la classe
     * @param graphDot Le {@link files.dot.GraphDot} devant être écrit
     * @param file Le {@link java.io.File} dans lequel sera écrit le {@link files.dot.GraphDot}
     */
    public DotFileWriter(GraphDot graphDot, File file) {
        this.graphDot = graphDot;
        this.file = file;
    }

    /**
     * Méthode procédant à l'écriture du {@link files.dot.GraphDot} dans le {@link java.io.File}
     * @throws IOException Si jamais le fichier n'existe pas
     */
    public void writeFile() throws IOException {

        DotFileParser filewriter = new DotFileParser(file);

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

        filewriter.flush();
        filewriter.close();
    }
}
