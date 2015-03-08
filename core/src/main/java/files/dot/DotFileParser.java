package files.dot;

import files.dot.elements.DotElement;
import files.dot.elements.EdgeDot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by paubreton on 08/03/15.
 */
public class DotFileParser extends FileWriter {

    public DotFileParser(File file) throws IOException {
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

        HashMap<String, Object> attributes = dotElement.getAttributes();

        dotAttributes.append("[");

        for(String s : attributes.keySet()){
            dotAttributes.append(s);
            dotAttributes.append("=");
            dotAttributes.append(String.valueOf(attributes.get(s)));
            dotAttributes.append(",");
        }

        dotAttributes.append("]");
        dotAttributes.substring(dotAttributes.lastIndexOf(","), dotAttributes.lastIndexOf(","));

        return dotAttributes.toString();
    }
}
