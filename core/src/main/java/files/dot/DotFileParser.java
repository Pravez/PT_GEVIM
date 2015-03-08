package files.dot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotFileParser extends FileWriter {

    public DotFileParser(GraphDot graphDot, File file) throws IOException {
        super(file);
    }

    public void write(DotElement dotElement, int indent) throws IOException {

        StringBuilder elementWriter = new StringBuilder();

        for(int i=0;i<indent;i++){
            elementWriter.append("    ");
        }
        elementWriter.append(dotElement.getId());
        elementWriter.append(" ");
        elementWriter.append(convertAttributes(dotElement));
        elementWriter.append(";\n");

        this.write(elementWriter.toString());

    }

    public void write(EdgeDot edgeElement, int indent) throws IOException{

        StringBuilder elementWriter = new StringBuilder();

        for(int i=0;i<indent;i++){
            elementWriter.append("    ");
        }

        elementWriter.append(edgeElement.getOrigin().getId());
        elementWriter.append(" -- ");
        elementWriter.append(edgeElement.getDestination().getId());
        elementWriter.append(" ");
        elementWriter.append(convertAttributes(edgeElement));
        elementWriter.append(";\n");

        this.write(elementWriter.toString());
    }

    public String convertAttributes(DotElement dotElement){
        StringBuilder dotAttributes = new StringBuilder();

        dotAttributes.append("[");

        for(DotAttribute da : dotElement.getAttributes()){
            dotAttributes.append(da.getAttributeName());
            dotAttributes.append("=");
            dotAttributes.append(String.valueOf(da.getAttribute()));
            dotAttributes.append(" ");
        }

        dotAttributes.append("]");

        return dotAttributes.toString();
    }
}
